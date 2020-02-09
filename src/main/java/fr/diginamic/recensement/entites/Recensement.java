package fr.diginamic.recensement.entites;

import java.util.ArrayList;
import java.util.List;

public class Recensement {
	List<Ville> ville = new ArrayList<Ville>();

	/**
	 * 
	 */
	public Recensement() {
		super();
	}

	public List<Ville> getVille() {
		return ville;
	}

	public void setVille(List<Ville> ville) {
		this.ville = ville;
	}
}
