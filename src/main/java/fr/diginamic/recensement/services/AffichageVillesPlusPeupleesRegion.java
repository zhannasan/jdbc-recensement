package fr.diginamic.recensement.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.utils.ValueComparator;

public class AffichageVillesPlusPeupleesRegion {
	public void traiter(VilleDaoJdbc dao, Scanner scanner) {
		System.out.println("Veuillez choisir une région.");
		String region = scanner.nextLine();
		HashMap<String, Integer> populationRegion = dao.afficherVilleRegion(region);
		
		Comparator<String> comparator = new ValueComparator<String, Integer>(populationRegion);
		TreeMap<String, Integer> populationRegionSorted = new TreeMap<>(comparator);
		populationRegionSorted.putAll(populationRegion);

		if (!populationRegionSorted.isEmpty()) {
			System.out.println("Les 10 villes les plus peuplées de la région " + region);
			for (Entry<String, Integer> entry : populationRegionSorted.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue() + " d'habitants.");
			}
		} else {
			System.out.println("La région " + region + "  n'a pas été trouvée.");
		}
		}
}
