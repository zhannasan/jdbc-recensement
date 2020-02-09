package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.IntegrationRecensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.utils.DbManager;

public class RecensementDaoJdbc implements RecensementDao {
	private static final Logger LOG = LoggerFactory.getLogger(IntegrationRecensement.class);
	private static Statement statement;
	private static Connection connection;

	/**
	 * 
	 */
	public RecensementDaoJdbc() {
		try {
			connection = DbManager.getConnection();
			statement = connection.createStatement();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		} 
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

	@Override
	public void insert(List<Ville> ville) {

	}

	@Override
	public HashMap<String, Integer> recherchePopulation(String searchParam) {
		return null;
	}

	@Override
	public HashMap<String, Integer> afficher() {
		return null;
	}
}
