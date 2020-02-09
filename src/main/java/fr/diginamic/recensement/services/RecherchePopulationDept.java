package fr.diginamic.recensement.services;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import fr.diginamic.recensement.dao.DepartementDaoJdbc;

public class RecherchePopulationDept {

	public void traiter(DepartementDaoJdbc dao, Scanner scanner) {
		System.out.println("Veuillez choisir un departement.");
		String dept = scanner.nextLine();
		Map<String, Integer> populationDept = dao.recherchePopulation(dept);
		if (!populationDept.isEmpty()) {
			for (Entry<String, Integer> entry : populationDept.entrySet()) {
				System.out.println(
						"La population du departement " + entry.getKey() + " est " + entry.getValue() + " habitants.");
			}
		}else{
			System.out.println("Le departement " + dept + "  n'a pas été trouvé.");
		}
	}
}
