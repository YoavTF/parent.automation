/**
 *
 */
package com.cedex.fortests;


import com.cedex.GlobalParameters.TryValidationEnum;
import com.cedex.jsystem.ReporterLight;
import com.cedex.text.textformat.TextBlockFormatter;
import org.junit.Assert;

/**
 * test utils function
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$ <br>
 * Author: $Author: romang $<br>
 * Svn Version: $Revision: 11766 $ <br>
 *
 * @author $Author: romang $
 * @version $Revision: 11766 $
 */

/**
 * @author Romang
 */
public class TestUtils implements ReporterLight {


    public static boolean assertNotNullParameter(int mandatoryInt) {
        String mandatoryStr = Integer.toString(mandatoryInt);
        return assertNotNullParameter(mandatoryStr);
    }

    public static boolean assertNotNullParameter(String mandatoryString) {
        TryValidationEnum fromBlock = TryValidationEnum.NOT_SET;
        boolean assertResult;
        try {
            Assert.assertNotNull(mandatoryString);
            fromBlock = TryValidationEnum.FROM_TRY;

        } catch (Exception e) {
            fromBlock = TryValidationEnum.FROM_CATCH;
        } finally {
            assertResult = fromBlock == TryValidationEnum.FROM_TRY ? true : false;
        }
        return assertResult;
    }

    /**
     * prepare test result string
     *
     * @param testResult - test result boolean
     * @return
     */
    public static String getTestResultString(boolean testResult) {
        String testResultString = testResult ? "PASSED" : "FAILED";
        return "============= Test: " + testResultString + " ============= ";
    }

    /**
     * print to reported result of test
     *
     * @param testResult
     */
    public static void printTestResult(String testName, boolean testResult) {
        String testResultMessage = getTestResultString(testResult);
//        report.report("_______________________________________________");
//        report.report("Test: '" + testName + "'");
//        report.report(testResultMessage, testResult);
//        report.report("_______________________________________________");
        TextBlockFormatter.printHeader("## Test: '" + testName + "'", '#',false);
        report.report(testResultMessage, testResult);
    }

}
