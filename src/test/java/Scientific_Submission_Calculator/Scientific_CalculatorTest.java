package Scientific_Submission_Calculator;

import junit.framework.TestCase;
import org.testng.Assert;

/**
 * Created by yasindu on 12/13/2016.
 */
public class Scientific_CalculatorTest extends TestCase {
    public void testCalc() throws Exception {

    }

    public void testAdd() throws Exception {
       double add= Scientific_Calculator.add(10,5);

        Assert.assertEquals(15.0, add);
    }

}