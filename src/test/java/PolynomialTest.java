import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PolynomialTest {

    private Polynomial p1;
    private Polynomial p2;

    @Before
    public void initialization() {
        int[] array1 = new int[]{3, 2, 4, 0};
        p1 = new Polynomial(array1); // p1 = 4x^2+2x+3
        int[] array2 = new int[]{0, -1};
        p2 = new Polynomial(array2); // p2 = -x
    }

    @Test
    public void sumTest() {
        Polynomial result = p1.summarize(p2);
        Assert.assertEquals("4x^2+x+3", result.toString());
    }

    @Test
    public void subTest() {
        Polynomial result = p1.subtract(p2);
        Assert.assertEquals("4x^2+3x+3", result.toString());
    }

    @Test
    public void multTest() {
        Polynomial result = p1.multiply(p2);
        Assert.assertEquals("-4x^3-2x^2-3x", result.toString());
        Polynomial p3 = new Polynomial(new int[]{1});
        result = p1.multiply(p3);
        Assert.assertEquals("4x^2+2x+3",result.toString());
    }

    @Test
    public void divTest() {
        Polynomial result;
        result = p1.divide(p2);
        Assert.assertEquals("-4x-2", result.toString());
        Polynomial p3 = new Polynomial(new int[]{0});

        assertThrows(IllegalArgumentException.class , () -> p1.divide(p3));
    }

    @Test
    public void remainderTest() {
        Polynomial result = p1.getRemainder(p2);
        Assert.assertEquals("3", result.toString());
    }

    @Test
    public void equalsTest(){
        Polynomial p3 = p1;
        Assert.assertFalse(p1.equals(p2));
        Assert.assertTrue(p1.equals(p3));
    }
}
