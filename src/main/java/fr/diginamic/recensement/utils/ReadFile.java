package fr.diginamic.recensement.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.IntegrationRecensement;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/**
 * Lire le fichier .csv dans un objet type Recensement - une Liste de Villes
 * avec des paramètres codeRegion, nomRegion, codeDepartement, nomCommune,
 * populationTotale
 *
 */
public class ReadFile {
	private static final Logger LOG = LoggerFactory.getLogger(ReadFile.class);

	public static Recensement read(String fileIn) {
		Recensement recensement = new Recensement();
		try {
			ClassLoader classLoader = new IntegrationRecensement().getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileIn).getFile());

			Reader fr = new FileReader(URLDecoder.decode(file.toString(), "UTF-8"));
			BufferedReader lineReader = new BufferedReader(fr);
			String line = null;

			lineReader.readLine();

			while ((line = lineReader.readLine()) != null) {
				String[] columns = line.split(";");
				String codeRegion = columns[0];
				String nomRegion = columns[1];
				String codeDept = columns[2];
				String nomCommune = columns[3];
				String population = columns[4];
				int pop = Integer.parseInt(population.trim().replaceAll(" ", ""));

				Ville ville = new Ville(codeRegion, nomRegion, codeDept, nomCommune, pop);
				recensement.getVille().add(ville);
			}

			lineReader.close();
			fr.close();
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return recensement;
	}

}

