package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class IMetierImpl implements IMetier {

    @Autowired
    public IMetierImpl(@Qualifier("dao2")dao.IDao IDao) {
        this.IDao = IDao;
    }

    private IDao IDao;

    @Override
    public double calcul() {

        double tmp = IDao.getData();

        return (tmp * 540)/Math.cos(tmp*Math.PI);
    }

    public IMetierImpl() {
    }

    /**
     * injection dun objet d'une classe implémentant l'interface IDao
     * @param IDao classe implémentant l'interface IDao
     */
    public void setIDao(dao.IDao IDao) {
        this.IDao = IDao;
    }
}
