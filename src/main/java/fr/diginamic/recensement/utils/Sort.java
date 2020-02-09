package fr.diginamic.recensement.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Convertion d'une HashMap<String, Integer> dans une TreeMap<String, Integer>
 * qui est triée à l'aide d'un comparateur par valeur ValueComparator
 *
 */
public class Sort {

	public static TreeMap<String, Integer> sortMap(HashMap<String, Integer> map) {
		Comparator<String> comparator = new ValueComparator<String, Integer>(map);
		TreeMap<String, Integer> populationDeptSorted = new TreeMap<>(comparator);
		populationDeptSorted.putAll(map);
		return populationDeptSorted;
	}
}
