package fr.diginamic.recensement;

import fr.diginamic.recensement.dao.DepartementDaoJdbc;
import fr.diginamic.recensement.dao.RegionDaoJdbc;
import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.utils.ReadFile;

public class IntegrationRecensement {
	
	public static void main(String[] args) {

		Recensement recencement = ReadFile.read("recensement population 2016.csv");
		VilleDaoJdbc villeDao = new VilleDaoJdbc();
		DepartementDaoJdbc deptDao = new DepartementDaoJdbc();
		RegionDaoJdbc regionDao = new RegionDaoJdbc();
		villeDao.exists();
		villeDao.insert(recencement.getVille());
		deptDao.insert(recencement.getVille());
		regionDao.insert(recencement.getVille());
		villeDao.close();
		deptDao.close();
		regionDao.close();

	}

}
