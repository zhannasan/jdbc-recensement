package fr.diginamic.recensement.services;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.RegionDaoJdbc;
import fr.diginamic.recensement.utils.Sort;

/**
 * Affichqge de 10 régions les plus peuplées
 *
 */
public class AffichageRegionsPlusPeuplees {

	public void traiter(RegionDaoJdbc dao, Scanner scanner) {
		HashMap<String, Integer> populationRegion = dao.afficher();
		
		TreeMap<String, Integer> populationRegionSorted = Sort.sortMap(populationRegion);

		for (Entry<String, Integer> entry : populationRegionSorted.entrySet()) {
			if(entry.getKey()!=null){
				System.out.println(entry.getKey() + " - population: " + entry.getValue() + " habitants.");
			}	
		}
	}

}
