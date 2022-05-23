package metier;

import dao.IDao;

public class IMetierImpl implements IMetier {

    private IDao IDao;


    @Override
    public double calcul() {

        double tmp = IDao.getData();

        return (tmp * 540)/Math.cos(tmp*Math.PI);
    }

    /**
     * injection dun objet d'une classe implémentant l'interface IDao
     * @param IDao
     */
    public void setIDao(dao.IDao IDao) {
        this.IDao = IDao;
    }
}
