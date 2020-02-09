package fr.diginamic.recensement.entites;

/**
 * Représent une ville avec codeRegion (String), nomRegion (String), codeDept
 * (String), nomCommune (String), populationTotale (int)
 *
 */
public class Ville {
	private String codeRegion;
	private String nomRegion;
	private String codeDept;
	private String nomCommune;
	private int populationTotale;
	
	/**
	 * Constructeur avec des paramètres:
	 * 
	 * @param codeRegion
	 *            - code d'une région
	 * @param nomRegion
	 *            - nom d'une région
	 * @param codeDept
	 *            - code d'un departement
	 * @param nomCommune
	 *            - nom d'une commune
	 * @param populationTotale
	 *            - population totale d'une commune
	 */
	public Ville(String codeRegion, String nomRegion, String codeDept, String nomCommune,
			int populationTotale) {
		super();
		this.codeRegion = codeRegion;
		this.nomRegion = nomRegion;
		this.codeDept = codeDept;
		this.nomCommune = nomCommune;
		this.populationTotale = populationTotale;
	};
	
	@Override
	public String toString(){
		return codeRegion+" "+nomRegion+" "+codeDept+" "+ nomCommune+" "+ populationTotale;
	}

	/**
	 * @return the codeRegion
	 */
	public String getCodeRegion() {
		return codeRegion;
	}

	/**
	 * @param codeRegion the codeRegion to set
	 */
	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}

	/**
	 * @return the nomRegion
	 */
	public String getNomRegion() {
		return nomRegion;
	}

	/**
	 * @param nomRegion the nomRegion to set
	 */
	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	/**
	 * @return the codeDept
	 */
	public String getCodeDept() {
		return codeDept;
	}

	/**
	 * @param codeDept the codeDept to set
	 */
	public void setCodeDept(String codeDept) {
		this.codeDept = codeDept;
	}

		/**
	 * @return the nomCommune
	 */
	public String getNomCommune() {
		return nomCommune;
	}

	/**
	 * @param nomCommune the nomCommune to set
	 */
	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	/**
	 * @return the populationTotale
	 */
	public int getPopulationTotale() {
		return populationTotale;
	}

	/**
	 * @param populationTotale the populationTotale to set
	 */
	public void setPopulationTotale(int populationTotale) {
		this.populationTotale = populationTotale;
	}
	
	
}
