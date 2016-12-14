package Scientific_Submission_Calculator;

import junit.framework.TestCase;
import org.testng.Assert;

/**
 * Created by yasindu on 12/13/2016.
 */
public class Scientific_CalculatorTest_mul extends TestCase {
    public void testMul() throws Exception {
        double add= Scientific_Calculator.mul(10,5);

        Assert.assertEquals(50.0, add);

    }

}