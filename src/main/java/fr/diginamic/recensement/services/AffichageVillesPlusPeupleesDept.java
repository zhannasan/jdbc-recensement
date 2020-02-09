package fr.diginamic.recensement.services;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.utils.Sort;

/**
 * Afficher les 10 villes les plus peuplées d’un département saisi par
 * utilisateur
 *
 */
public class AffichageVillesPlusPeupleesDept {
	public void traiter(VilleDaoJdbc dao, Scanner scanner) {
		System.out.println("Veuillez choisir un departement.");
		String dept = scanner.nextLine();

		HashMap<String, Integer> populationDept = dao.afficherVilleDept(dept);
		TreeMap<String, Integer> populationDeptSorted = Sort.sortMap(populationDept);

		if (!populationDept.isEmpty()) {
			System.out.println("Les 10 villes les plus peuplées du département " + dept);
			for (Entry<String, Integer> entry : populationDeptSorted.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue() + " d'habitants.");
			}
		}
	}
}
