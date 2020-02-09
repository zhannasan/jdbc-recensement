package fr.diginamic.recensement.dao;

import java.util.HashMap;
import java.util.List;

import fr.diginamic.recensement.entites.Ville;

public interface RecensementDao {
	
	HashMap<String, Integer> recherchePopulation(String searchParam);

	void insert(List<Ville> ville);
	
	void update();

	HashMap<String, Integer> afficher();

	// void delete();
}
