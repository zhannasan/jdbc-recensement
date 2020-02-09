package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.utils.DbManager;

/**
 * DepartementDaoJdbc implément les methodes de RecensementDao et crée une
 * connection par injection dans le constructeur
 *
 */
public class DepartementDaoJdbc implements RecensementDao {
	private static final Logger LOG = LoggerFactory.getLogger(DepartementDaoJdbc.class);
	private static Statement statement;
	private static Connection connection;
	private static ResultSet cursor;

	/**
	 * constructeur sans paramètres avec l'overture de connection
	 */
	public DepartementDaoJdbc() {
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
	public void insert(List<Ville> villes) {
		try {
			long startD = System.currentTimeMillis();

			String insertDept = "INSERT IGNORE INTO departement SELECT code_departement, SUM(population_totale) FROM ville GROUP BY code_departement ASC";
			PreparedStatement insertStatementDept = connection.prepareStatement(insertDept);
			insertStatementDept.executeUpdate();
			connection.commit();

			long endD = System.currentTimeMillis();
			LOG.info("Temps de création table departement : " + (endD - startD) + "ms");
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
	public HashMap<String, Integer> recherchePopulation(String dept) {
		HashMap<String, Integer> extractPop = new HashMap<>();
		try {
			String sql = "SELECT population_totale, code_departement FROM departement WHERE code_departement like "
					+ dept + " ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("code_departement");
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
			String sql = "SELECT population_totale, code_departement FROM departement ORDER BY population_totale DESC LIMIT 10;";
			cursor = statement.executeQuery(sql);
			while (cursor.next()) {
				String searchResult = cursor.getString("code_departement");
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
	public void update() {
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
}
