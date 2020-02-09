package fr.diginamic.recensement.dao;

import java.sql.Connection;
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

/**
 * RegionDaoJdbc implément les methodes de RecensementDao et crée une connection
 * par injection dans le constructeur
 *
 */
public class RegionDaoJdbc implements RecensementDao {
	private static final Logger LOG = LoggerFactory.getLogger(RegionDaoJdbc.class);
	private Statement statement;
	private Connection connection;
	private static ResultSet cursor;

	/**
	 * constructeur sans paramètres avec l'overture de connection
	 */
	public RegionDaoJdbc() {
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
	public HashMap<String, Integer> afficher() {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String sql = "SELECT population_totale, nom_region FROM region ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("nom_region");
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
	public void insert(List<Ville> ville) {
		try {
			long startR = System.currentTimeMillis();
			String insertRegion = "INSERT IGNORE INTO region SELECT code_region, nom_region, SUM(population_totale) FROM ville GROUP BY code_region";
			PreparedStatement insertStatementRegion = connection.prepareStatement(insertRegion);
			insertStatementRegion.executeUpdate();
			connection.commit();
			long endR = System.currentTimeMillis();
			LOG.info("Temps de création table region : " + (endR - startR) + "ms");
		} catch (SQLException e) {
			LOG.error("SQL exception ", e.getMessage());
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

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

	@Override
	public HashMap<String, Integer> recherchePopulation(String region) {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String searchParam = "";
			if (StringUtils.isNumeric(region)) {
				searchParam = "code_region";
			} else {
				searchParam = "nom_region";
			}
			String sql = "SELECT population_totale, " + searchParam + " FROM region WHERE lower(" + searchParam
					+ ") like '%" + region.toLowerCase() + "%' ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString(searchParam);
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
