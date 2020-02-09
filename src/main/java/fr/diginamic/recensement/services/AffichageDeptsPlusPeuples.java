package fr.diginamic.recensement.services;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.DepartementDaoJdbc;
import fr.diginamic.recensement.utils.Sort;

/**
 * Affichage des 10 départements les plus peuplés
 */
public class AffichageDeptsPlusPeuples {
	
	public void traiter(DepartementDaoJdbc dao, Scanner scanner) {
		HashMap<String, Integer> populationRegion = dao.afficher();
		TreeMap<String, Integer> populationRegionSorted = Sort.sortMap(populationRegion);
		
		for (Entry<String, Integer> entry : populationRegionSorted.entrySet()) {
			if(entry.getKey()!=null){
				System.out.println(entry.getKey() + " - population : " + entry.getValue() + " habitants.");
			}
		}
	}
}