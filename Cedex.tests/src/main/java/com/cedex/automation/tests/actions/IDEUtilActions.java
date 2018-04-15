package com.cedex.automation.tests.actions;

import com.cedex.automation.tests.base.AbstractBaseTest;
import com.cedex.numbers.Randomalizator;
import jsystem.framework.RunProperties;
import jsystem.framework.TestProperties;
import org.junit.Assert;
import org.junit.Test;

public class IDEUtilActions extends AbstractBaseTest {


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private String storeParameterName = "username";
    private String storeParameterValue = "romang";


    private String storeParameterPreffix = "testuserforautotests";
    private String storeParameterPostfix = "@send22u.info";
    private String storeParameterValueRandom = storeParameterPreffix;
    private int maxRamdom = 10000;


//    public void handleUIEvent(HashMap<String,Parameter> map, String methodName) throws Exception {
//        if (!"storeParameterRandom".equals(methodName)) {
//            isRandomTest=true;
//        }
//    }

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------

    /**
     * save any parameter to future use
     * for example :
     * i set storeParameterName=username
     * and set storeParameterValue=romang
     * <p>
     * this action saves this pair like: username=romang and
     * if you want user this parameter in on of you future tests just put #{username} and test will get its value (romang)
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: store parameter: ${storeParameterName} with value: ${storeParameterValue} ", paramsInclude = {"storeParameterName", "storeParameterValue"}, returnParam = {"storeParameterName"})
    public void storeParameter() throws Exception {
        Assert.assertNotNull(storeParameterName);
        Assert.assertNotNull(storeParameterValue);
        RunProperties.getInstance().setRunProperty(storeParameterName, storeParameterValue);

    }

    /**
     * save any randon parameter to future use
     * stored parameter will be looks like: [storeParameterPreffix]some random number[storeParameterPostfix]
     * for example if i want to provide random email:
     * i can use preffix like testmail
     * and postfix: @gmail.com
     * so complete mail may looks like: storeParameterValue=testmail67892@gmail.com
     * and to future use this parameter just define in other test parameters field #{storeParameterValue} and you will get its value (the random email)
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: store random parameter: ${storeParameterName} with value: ${storeParameterValueRandom} ", paramsInclude = {"storeParameterPreffix", "maxRamdom", "storeParameterPostfix","storeParameterName"}, returnParam = {"storeParameterName"})
    public void storeParameterRandom() throws Exception {
        Assert.assertNotNull(storeParameterName);
        Assert.assertNotNull(storeParameterValue);
        RunProperties.getInstance().setRunProperty(storeParameterName, storeParameterValueRandom);
    }

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public String getStoreParameterName() {
        return storeParameterName;
    }

    public void setStoreParameterName(String storeParameterName) {
        this.storeParameterName = storeParameterName;
    }

    public String getStoreParameterValue() {
        return storeParameterValue;
    }

    public void setStoreParameterValue(String storeParameterValue) {
        this.storeParameterValue = storeParameterValue;
    }

    public String getStoreParameterPreffix() {
        return storeParameterPreffix;
    }

    /**
     * parameter prefix before random number (like: testuserforautotests.2766386 before the dot)
     *
     * @param storeParameterPreffix
     */
    public void setStoreParameterPreffix(String storeParameterPreffix) {
        this.storeParameterPreffix = storeParameterPreffix;
    }

    public int getMaxRamdom() {
        return maxRamdom;

    }

    public void setMaxRamdom(int maxRamdom) {
        this.maxRamdom = maxRamdom;
    }

    public String getStoreParameterValueRandom() {
        return storeParameterValueRandom;
    }

    public void setStoreParameterValueRandom(String storeParameterValueRandom) {
        int random = Randomalizator.getRandomNumber(maxRamdom);
        this.storeParameterValueRandom = this.storeParameterPreffix + random+storeParameterPostfix;
    }

    public String getStoreParameterPostfix() {
        return storeParameterPostfix;
    }

    /**
     * parameter postfix after random number (like: testuser.2766386@gmail.com after the random number)
     * @param storeParameterPostfix
     */
    public void setStoreParameterPostfix(String storeParameterPostfix) {
        this.storeParameterPostfix = storeParameterPostfix;
    }
}

