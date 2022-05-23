package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Pres2 {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        /*
        couplage faible grâve aux interfaces
         */

        Scanner scanner = new Scanner(new File("src/config.txt"));


        String daoClassName = scanner.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.newInstance(); // classcastexcepton : instanciation ici d'une classe qui n'implémente pas IDAO

        String metierClassName = scanner.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.newInstance();


        // Method permet de retrouver une méthode depuis une chaine de caractère et l'interface correspondante (ici)
        Method method = cMetier.getMethod("setIDao",IDao.class);
        // invoke ici permet d'invoquer la méthgode appelée au dessus , param 1 = l'interface contenant la méthode
        // param 2 = le paramètre du setter invoqué, ici le dao instancié dynamiquement via le nom dans le txt
        method.invoke(metier,dao);

        System.out.println("resultat=" + metier.calcul());

    }

}
