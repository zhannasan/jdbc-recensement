package fr.diginamic.recensement.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.RegionDaoJdbc;
import fr.diginamic.recensement.utils.ValueComparator;

public class AffichageRegionsPlusPeuplees {

	public void traiter(RegionDaoJdbc dao, Scanner scanner) {
		HashMap<String, Integer> populationRegion = dao.afficher();
		Comparator<String> comparator = new ValueComparator<String, Integer>(populationRegion);
		TreeMap<String, Integer> populationRegionSorted = new TreeMap<>(comparator);
		populationRegionSorted.putAll(populationRegion);
		
		for (Entry<String, Integer> entry : populationRegionSorted.entrySet()) {
			if(entry.getKey()!=null){
				System.out.println(entry.getKey() + " - population: " + entry.getValue() + " habitants.");
			}	
		}
	}

}
