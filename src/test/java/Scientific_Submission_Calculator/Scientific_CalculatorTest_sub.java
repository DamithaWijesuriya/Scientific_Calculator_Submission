package Scientific_Submission_Calculator;

import junit.framework.TestCase;
import org.testng.Assert;

/**
 * Created by yasindu on 12/13/2016.
 */
public class Scientific_CalculatorTest_sub extends TestCase {
    public void testSub() throws Exception {
        double add= Scientific_Calculator.sub(10,5);

        Assert.assertEquals(5.0, add);
    }

}