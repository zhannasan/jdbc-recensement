package fr.diginamic.recensement.services;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import fr.diginamic.recensement.dao.RegionDaoJdbc;

/**
 * Recherche de la Population d'une Région choisie par utilisateur
 */
public class RecherchePopulationRegion {
	public void traiter(RegionDaoJdbc dao, Scanner scanner) {
		System.out.println("Veuillez choisir une région.");
		String region = scanner.nextLine();
		Map<String, Integer> populationRegion = dao.recherchePopulation(region);
		if (!populationRegion.isEmpty()) {
			for (Entry<String, Integer> entry : populationRegion.entrySet()) {
				System.out.println(
						"La population de la région " + entry.getKey() + " est " + entry.getValue() + " habitants.");
			}
		}else{
			System.out.println("La région " + region + "  n'a pas été trouvée.");
		}
	}
}
