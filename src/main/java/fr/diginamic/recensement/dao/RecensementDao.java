package fr.diginamic.recensement.dao;

import java.util.HashMap;
import java.util.List;

import fr.diginamic.recensement.entites.Ville;

/**
 * Un seul commun Dao pour les VilleDaoJdbc, DepartementDaoJdbc et RegionDaoJdbc
 *
 */
public interface RecensementDao {

	/**
	 * insert ignore dans le tableau ville a partir d'un fichier et des tableaux
	 * departement et région a partir de tableau ville pour rapidité
	 * 
	 * @param ville
	 *            liste des villes du fichier recensement
	 */
	void insert(List<Ville> ville);
	
	/**
	 * convergence des arrondissements dans une seule ville dans la table ville
	 */
	void update();

	/**
	 * recherchePopulation par
	 * 
	 * @param searchParam
	 *            - nom_commune/code_departement/nom_region/code_region
	 *            recherché (String)
	 * @return HashMap de nom_commune/code_departement/nom_region/code_region
	 *         (String) et population_totale (Integer)
	 */
	HashMap<String, Integer> recherchePopulation(String searchParam);

	/**
	 * afficher les villes/departements/regions plus peuplés
	 * 
	 * @return HashMap de nom_commune/code_departement/nom_region/code_region
	 *         (String) et population_totale (Integer)
	 */
	HashMap<String, Integer> afficher();

}
