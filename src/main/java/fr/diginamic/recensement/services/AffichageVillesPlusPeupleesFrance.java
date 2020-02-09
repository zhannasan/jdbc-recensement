package fr.diginamic.recensement.services;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.utils.Sort;

/**
 * Afficher les 10 villes les plus peuplées de France
 *
 */
public class AffichageVillesPlusPeupleesFrance {

	public void traiter(VilleDaoJdbc dao, Scanner scanner) {
		HashMap<String, Integer> populationVille = dao.afficher();
		TreeMap<String, Integer> populationVilleSorted = Sort.sortMap(populationVille);

		System.out.println("Les 10 villes les plus peuplées en France:");
		for (Entry<String, Integer> entry : populationVilleSorted.entrySet()) {
			if(entry.getKey()!=null){
				System.out.println(entry.getKey() + " - " +entry.getValue()+" d'habitants.");
			}
		}
	}
}
