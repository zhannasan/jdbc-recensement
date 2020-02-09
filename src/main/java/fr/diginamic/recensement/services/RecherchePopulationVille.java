package fr.diginamic.recensement.services;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import fr.diginamic.recensement.dao.VilleDaoJdbc;

/**
 * Recherche de la Population d'une Ville choisie par utilisateur
 *
 */
public class RecherchePopulationVille {

	public void traiter(VilleDaoJdbc dao, Scanner scanner) {
		System.out.println("Veuillez choisir une ville.");
		String commune = scanner.nextLine();
		Map<String, Integer> populationVille = dao.recherchePopulation(commune);
		if (!populationVille.isEmpty()) {
			for (Entry<String, Integer> entry : populationVille.entrySet()) {
				System.out.println("La population de " + entry.getKey() + " est " + entry.getValue());
			}
		} else {
			System.out.println("La ville " + commune + "  n'a pas été trouvée.");
		}
	}
}
