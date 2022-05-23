package metier;


import org.junit.Assert;
import org.junit.Test;

public class CalculTest {

    private Calcul calcul;

    @Test
    public void testSomme() {
        calcul = new Calcul();
        double a = 5;
        double b = 9;
        double expected = 14;
        double res = calcul.somme(a,b);
        Assert.assertEquals(expected, res, 0.0);
    }

    @Test
    public void testSommeDeux() {
        calcul = new Calcul();
        double a = 5;
        double b = 9;
        double expected = 13;
        double res = calcul.somme(a,b);
        Assert.assertNotEquals(expected, res, 0.0);
    }

}
