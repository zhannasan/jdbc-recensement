package fr.diginamic.recensement.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.utils.ValueComparator;

public class AffichageVillesPlusPeupleesDept {
	public void traiter(VilleDaoJdbc dao, Scanner scanner) {
		System.out.println("Veuillez choisir un departement.");
		String dept = scanner.nextLine();

		HashMap<String, Integer> populationDept = dao.afficherVilleDept(dept);
		Comparator<String> comparator = new ValueComparator<String, Integer>(populationDept);
		TreeMap<String, Integer> populationDeptSorted = new TreeMap<>(comparator);
		populationDeptSorted.putAll(populationDept);

		if (!populationDept.isEmpty()) {
			System.out.println("Les 10 villes les plus peuplées du département " + dept);
			for (Entry<String, Integer> entry : populationDept.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue() + " d'habitants.");
			}
		}
	}
}
