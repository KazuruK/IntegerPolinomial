import org.junit.Assert;
import org.junit.Test;

public class PolynomialTest {

    private Polynomial p1;
    private Polynomial p2;

    private void initialization() {
        int[] array1 = new int[]{3, 2, 4, 0};
        p1 = new Polynomial(array1); // p1 = 4x^2+2x+3
        int[] array2 = new int[]{0, -1};
        p2 = new Polynomial(array2); // p2 = -x
    }

    @Test
    public void sumTest() {
        initialization();
        Polynomial result = p1.summarize(p2);
        Assert.assertEquals("4x^2+x+3", result.toString());
    }

    @Test
    public void subTest() {
        initialization();
        Polynomial result = p1.subtract(p2);
        Assert.assertEquals("4x^2+3x+3", result.toString());
    }

    @Test
    public void multTest() {
        initialization();
        Polynomial result = p1.multiply(p2);
        Assert.assertEquals("-4x^3-2x^2-3x", result.toString());
    }

    @Test
    public void divTest() {
        initialization();
        Polynomial result = p1.divide(p2);
        Assert.assertEquals("-4x-2", result.toString());
    }

    @Test
    public void remainderTest() {
        initialization();
        Polynomial result = p1.getRemainder(p2);
        Assert.assertEquals("3", result.toString());
    }

    @Test
    public void equalsTest(){
        initialization();
        Polynomial p3 = p1;
        Assert.assertFalse(p1.equals(p2));
        Assert.assertTrue(p1.equals(p3));
    }
}
