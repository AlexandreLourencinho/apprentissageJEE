package presentation;

import dao.Daoimpl;
import metier.IMetierImpl;

public class presentation {

    public static void main(String[] args) {

        /**
         * injection dépendances par instanciation statique (=> new Etc)
         * couplage fort: code source à modifier !!  => mauvaise pratique
         */

        Daoimpl daoimpl = new Daoimpl();
        IMetierImpl metier = new IMetierImpl(daoimpl);
        System.out.println("resultats=" + metier.calcul());
    }



}
