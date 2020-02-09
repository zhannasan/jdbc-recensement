package fr.diginamic.recensement.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.utils.ValueComparator;

public class AffichageVillesPlusPeupleesFrance {

	public void traiter(VilleDaoJdbc dao, Scanner scanner) {
		HashMap<String, Integer> populationVille = dao.afficher();
		
		Comparator<String> comparator = new ValueComparator<String, Integer>(populationVille);
		TreeMap<String, Integer> populationVilleSorted = new TreeMap<>(comparator);
		populationVilleSorted.putAll(populationVille);

		System.out.println("Les 10 villes les plus peuplées en France:");
		for (Entry<String, Integer> entry : populationVilleSorted.entrySet()) {
			if(entry.getKey()!=null){
				System.out.println(entry.getKey() + " - " +entry.getValue()+" d'habitants.");
			}
		}
	}
}
