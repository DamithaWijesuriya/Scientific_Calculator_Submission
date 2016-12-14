package Scientific_Submission_Calculator;

import junit.framework.TestCase;
import org.testng.Assert;

/**
 * Created by yasindu on 12/13/2016.
 */
public class Scientific_CalculatorTest_div extends TestCase {
    public void testDiv() throws Exception {
        double add= Scientific_Calculator.div(10,5);

        Assert.assertEquals(2.0, add);
    }

}