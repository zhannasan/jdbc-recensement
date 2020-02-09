package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.utils.DbManager;

public class VilleDaoJdbc implements RecensementDao {
	private static final Logger LOG = LoggerFactory.getLogger(VilleDaoJdbc.class);

	private Connection connection;
	private Statement statement;
	private static ResultSet cursor;

	/**
	 * @param connection
	 * @param statement
	 */
	public VilleDaoJdbc() {
		try {
			connection = DbManager.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
		} catch (SQLException e) {
			LOG.error("SQL exception ", e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}
	}

	@Override
	public HashMap<String, Integer> recherchePopulation(String ville) {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String sql = "SELECT population_totale, nom_commune FROM ville WHERE LOWER(nom_commune) like '%"
					+ ville.toLowerCase() + "%' ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("nom_commune");
				int population = cursor.getInt("population_totale");
				extractPop.put(searchResult, population);
			}
			cursor.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		}
		return extractPop;
	}

	@Override
	public HashMap<String, Integer> afficher() {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String sql = "SELECT population_totale, nom_commune FROM ville ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("nom_commune");
				int population = cursor.getInt("population_totale");
				extractPop.put(searchResult, population);
			}
			cursor.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		}
		return extractPop;
	}

	@Override
	public void insert(List<Ville> villes) {
		int batchSize = 1280;
		long start = System.currentTimeMillis();

		LOG.info("Insertion des données dans la table ville");
		try {
			String sql = "INSERT IGNORE INTO ville (code_region, nom_region, code_departement, nom_commune, population_totale) VALUES (?,?,?,?,?)";
			PreparedStatement insertStatement = connection.prepareStatement(sql);
			int i = 0;
			for (Ville v : villes) {
				insertStatement.setString(1, v.getCodeRegion());
				insertStatement.setString(2, v.getNomRegion());
				insertStatement.setString(3, v.getCodeDept());
				insertStatement.setString(4, v.getNomCommune());
				insertStatement.setInt(5, v.getPopulationTotale());
				insertStatement.addBatch();

				if (i % batchSize == 0) {
					insertStatement.executeBatch();
				}
				i++;
			}
			insertStatement.executeBatch();
			connection.commit();

			long end = System.currentTimeMillis();
			LOG.info("Temps d'insertion dans la table ville : " + (end - start) + "ms");
		} catch (SQLException e) {
			LOG.error("SQL exception ", e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}
	}

	@Override
	public void update() {
		try { // -----fusion Lyon arrondissements-----
		String insertUpdate = "INSERT IGNORE INTO ville (SELECT SUM(id), code_region, nom_region, code_departement, 'Lyon', SUM(population_totale) FROM ville WHERE nom_commune LIKE '%Lyon%arrond%')";
		statement.executeUpdate(insertUpdate);
		LOG.info(insertUpdate);
		String deleteUpdate = "DELETE FROM ville WHERE nom_commune LIKE '%lyon%arrondiss%'";
		statement.executeUpdate(deleteUpdate);
		LOG.info(deleteUpdate);
		} catch (SQLException e) {
			LOG.error("SQL exception ", e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}

	}

	public void exists() {
		try {

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			boolean[] exists = new boolean[3];
			while (rs.next()) {
				if (rs.getString(3).equalsIgnoreCase("ville")) {
					exists[0] = true;
				}
				if (rs.getString(3).equalsIgnoreCase("departement")) {
					exists[1] = true;
				}
				if (rs.getString(3).equalsIgnoreCase("region")) {
					exists[2] = true;
				}
				LOG.info(rs.getString(3));

			}
			rs.close();
			if (exists[0] == false) {

				String createVille = "CREATE TABLE ville(" + "ID int(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
						+ "code_region int(10)," + "nom_region varchar(50)," + "code_departement varchar(50),"
						+ "nom_commune varchar(50)," + "population_totale int(10));";
				statement.executeUpdate(createVille);
				LOG.info("Créé la table ville.");
				String alterVille = "ALTER TABLE ville ADD CONSTRAINT uc UNIQUE(code_departement, nom_commune);";
				statement.execute(alterVille);
			}
			if (exists[2] == false) {
				String createRegion = "CREATE TABLE region(" + "code_region int(10)," + "nom_region varchar(50),"
						+ "population_totale int(10));";

				statement.executeUpdate(createRegion);

				LOG.info("Créé la table region.");
				String alterDept = "ALTER TABLE region ADD CONSTRAINT ur UNIQUE(code_region);";
				statement.execute(alterDept);
			}
			if (exists[1] == false) {
				String createDept = "CREATE TABLE departement(" + "code_departement varchar(50),"
						+ "population_totale int(10));";
				statement.executeUpdate(createDept);
				LOG.info("Créé la table departement.");
				String alterDept = "ALTER TABLE departement ADD CONSTRAINT ud UNIQUE(code_departement);";
				statement.execute(alterDept);
			}

		} catch (SQLException e) {
			LOG.error("SQL exception ", e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}
	}

	public void close() {
		try {
			if (statement != null) {
				statement.close();
			} else if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public HashMap<String, Integer> afficherVilleDept(String dept) {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String sql = "SELECT population_totale, nom_commune FROM ville where code_departement='" + dept
					+ "' ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("nom_commune");
				int population = cursor.getInt("population_totale");
				extractPop.put(searchResult, population);
			}
			cursor.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		}
		return extractPop;
	}

	public HashMap<String, Integer> afficherVilleRegion(String region) {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String searchParam = "";
			if (StringUtils.isNumeric(region)) {
				searchParam = "code_region";
			} else {
				searchParam = "nom_region";
			}
			String sql = "SELECT population_totale, nom_commune FROM ville WHERE " + searchParam + "='" + region
					+ "' ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("nom_commune");
				int population = cursor.getInt("population_totale");
				extractPop.put(searchResult, population);
			}
			cursor.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		}
		return extractPop;
	}
}
