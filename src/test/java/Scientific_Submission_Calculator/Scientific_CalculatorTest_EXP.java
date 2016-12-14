package Scientific_Submission_Calculator;

import junit.framework.TestCase;
import org.testng.Assert;

/**
 * Created by yasindu on 12/13/2016.
 */
public class Scientific_CalculatorTest_EXP extends TestCase {
    public void testCalc() throws Exception {

        String ex= Scientific_Calculator.calc("3+3*(5+3)");

        Assert.assertEquals("27.0", ex);

    }

}