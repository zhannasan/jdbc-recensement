package fr.diginamic.recensement;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.dao.DepartementDaoJdbc;
import fr.diginamic.recensement.dao.RegionDaoJdbc;
import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.services.AffichageDeptsPlusPeuples;
import fr.diginamic.recensement.services.AffichageRegionsPlusPeuplees;
import fr.diginamic.recensement.services.AffichageVillesPlusPeupleesDept;
import fr.diginamic.recensement.services.AffichageVillesPlusPeupleesFrance;
import fr.diginamic.recensement.services.AffichageVillesPlusPeupleesRegion;
import fr.diginamic.recensement.services.RecherchePopulationDept;
import fr.diginamic.recensement.services.RecherchePopulationRegion;
import fr.diginamic.recensement.services.RecherchePopulationVille;

public class Application {
	private static final Logger LOG = LoggerFactory.getLogger(IntegrationRecensement.class);
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		VilleDaoJdbc villeDao = new VilleDaoJdbc();
		DepartementDaoJdbc deptDao = new DepartementDaoJdbc();
		RegionDaoJdbc regionDao = new RegionDaoJdbc();
		
		if (villeDao == null) {
			LOG.error("Une erreur est survenue et l'application va se fermer.");
			System.exit(0);
		}
		int input = 0;
		do {
			afficherMenu();
			String inputS = scanner.nextLine();

			if (StringUtils.isNumeric(inputS)) {
				input = Integer.valueOf(inputS);
				switch (input) {
				case 1:
					// Population d’une ville donnée
					RecherchePopulationVille rechercheVille = new RecherchePopulationVille();
					rechercheVille.traiter(villeDao, scanner);
					break;
				case 2:
					// Population d’un département donné
					RecherchePopulationDept rechercheDept = new RecherchePopulationDept();
					rechercheDept.traiter(deptDao, scanner);
					break;
				case 3:
					// Population d’une région donnée
					RecherchePopulationRegion rechercheRegion = new RecherchePopulationRegion();
					rechercheRegion.traiter(regionDao, scanner);
					break;
				case 4:
					// Afficher les 10 régions les plus peuplées
					AffichageRegionsPlusPeuplees afficherRegion = new AffichageRegionsPlusPeuplees();
					afficherRegion.traiter(regionDao, scanner);
					break;
				case 5:
					// Afficher les 10 départements les plus peuplés
					AffichageDeptsPlusPeuples afficherDept = new AffichageDeptsPlusPeuples();
					afficherDept.traiter(deptDao, scanner);
					break;
				case 6:
					// Afficher les 10 villes les plus peuplées d’un département
					AffichageVillesPlusPeupleesDept afficherVilleDept = new AffichageVillesPlusPeupleesDept();
					afficherVilleDept.traiter(villeDao, scanner);
					break;
				case 7:
					// Afficher les 10 villes les plus peuplées d’une région
					AffichageVillesPlusPeupleesRegion afficherVilleRegion = new AffichageVillesPlusPeupleesRegion();
					afficherVilleRegion.traiter(villeDao, scanner);
					break;
				case 8:
					// Afficher les 10 villes les plus peuplées de France
					AffichageVillesPlusPeupleesFrance afficherVilleFr = new AffichageVillesPlusPeupleesFrance();
					afficherVilleFr.traiter(villeDao, scanner);
					break;
				case 9:
					System.out.println("Au revoir !");
					villeDao.close();
					deptDao.close();
					regionDao.close();
					break;
				default:
					System.out.println("Veuillez saisir un nombre de nouveau.");
					break;
				}
			} else {
				System.out.println("Veuillez saisir un nombre.\r");
			}
		} while (input != 9);
		{
			villeDao.close();
			deptDao.close();
			regionDao.close();
			scanner.close();
		}
	}

	public static void afficherMenu() {
		System.out.println("1. Population d’une ville donnée");
		System.out.println("2. Population d’un département donné (numéro)");
		System.out.println("3. Population d’une région donnée (nom ou numéro)");
		System.out.println("4. Afficher les 10 régions les plus peuplées");
		System.out.println("5. Afficher les 10 départements les plus peuplés");
		System.out.println("6. Afficher les 10 villes les plus peuplées d’un département (numéro)");
		System.out.println("7. Afficher les 10 villes les plus peuplées d’une région (nom ou numéro)");
		System.out.println("8. Afficher les 10 villes les plus peuplées de France");
		System.out.println("9. Sortir");
	}

}
