package fr.diginamic.recensement.entites;

import java.util.ArrayList;
import java.util.List;

/**
 * Représent des données du recensement - une Liste des Villes
 *
 */
public class Recensement {
	List<Ville> ville = new ArrayList<Ville>();

	/**
	 * constructeur sans paramètres
	 */
	public Recensement() {
		super();
	}

	/**
	 * @return liste de villes
	 */
	public List<Ville> getVille() {
		return ville;
	}

	/**
	 * déterminer une liste de villes
	 * 
	 * @param ville
	 */
	public void setVille(List<Ville> ville) {
		this.ville = ville;
	}
}
