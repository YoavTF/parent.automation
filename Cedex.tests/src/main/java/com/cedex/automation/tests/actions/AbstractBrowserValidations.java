package com.cedex.automation.tests.actions;

import com.cedex.automation.tests.base.AbstractBrowserBaseTest;
import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.text.StringUtils;
import com.cedex.text.StringUtils.StringOperator;
import jsystem.framework.TestProperties;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class AbstractBrowserValidations extends AbstractBrowserBaseTest {


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private String titleToValidate = "CEDEX - Certified BlockChain Based Diamond Exchange";
    private RecognizeBy recognizeBy;
    private String identifier;
    private boolean takeSnapOnFail = true;
    private String attributeToLookFor;
    private String expectedAttributeValue;

    private String expectedText;
    private int rowIndex = 0;
    private int collumnIndex = 0;

    private boolean titleResult;
    private boolean cellResult;
    private boolean textResult;
    private StringOperator stringOperator = StringOperator.EQUALS;
    private RecognizeBy childRecognizeBy;
    private String childIdentifier;

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    @Test
    @TestProperties(name = "BB: Validating current Title equals to: ${titleToValidate}", paramsExclude = {"textResult", "childRecognizeBy", "childIdentifier",
            "expectedText", "cellResult", "rowIndex", "collumnIndex", "identifier", "recognizeBy", "attributeToLookFor", "expectedAttributeValue"}, returnParam = {"titleResult"})
    public void titleValidation() throws Exception {

        report.report("Validating that current Window " + stringOperator + " title: " + titleToValidate);
        String currentFullTitle = lab.browser[browserIndex].getDriver().getTitle();
        titleResult = lab.browser[browserIndex].getValidator().isTitleExists(currentFullTitle, stringOperator);
        super.addSubScenarioResult("Is Title found: ", titleResult);
        if (!titleResult) windowsActionsWrapper.takeSnapshot();


    }

    @Test
    @TestProperties(name = "BB: Validating if element ${identifier} by: ${recognizeBy} located on current page", paramsExclude = {"stringOperator", "textResult", "childRecognizeBy", "childIdentifier",
            "cellResult", "titleResult", "expectedText", "rowIndex", "collumnIndex", "titleToValidate", "attributeToLookFor", "expectedAttributeValue"}, returnParam = {})
    public void elementExistenceValidation() throws Exception {
        String pageUrl = lab.browser[browserIndex].getDriver().getCurrentUrl();
        report.report("Validating if element exists on current page: " + pageUrl);
        boolean isElementFound = lab.browser[browserIndex].getValidator().isElementPresent(recognizeBy, identifier);
        super.addSubScenarioResult("Is Element found: ", isElementFound);
        if (!isElementFound) windowsActionsWrapper.takeSnapshot();
    }

    /**
     * get Text from Table's Cell
     * <p>
     * rowIndex  - index of the Row (started from 0)
     * collIndex - index of the Column (started from 0)
     * <p>
     * return value: isExpectedEquals
     * you can catch it by using ${cellResult}
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Validating if Table Cell Row: ${rowIndex} on Coll: ${collumnIndex} ${stringOperator} to text: ${expectedText}", paramsExclude = {"textResult", "cellResult",
            "childRecognizeBy", "childIdentifier", "titleResult", "identifier", "recognizeBy", "titleToValidate", "attributeToLookFor", "expectedAttributeValue"},
            returnParam = {"cellResult"})
    public void IfTablesCellHaveValueValidation() throws Exception {
        String foundCellValue = lab.browser[browserIndex].getTableCellValue(rowIndex, collumnIndex);

        cellResult = StringUtils.stringCompare(foundCellValue, expectedText, stringOperator);
//        cellResult = foundCellValue.equalsIgnoreCase(expectedText);
        super.addSubScenarioResult("Cell's Text " + stringOperator + " to expected", cellResult);
        if (!cellResult) windowsActionsWrapper.takeSnapshot();
        report.report("cellResult=" + cellResult);
    }

    /**
     * Validating that child's element have texk like expected
     * parameters: recognizeBy, identifier referenced to the parent element
     * parameters: childRecognizeBy,childIdentifier  to the child element
     * result stored in parameter: textResult
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Validating Child Element ${stringOperator} to text: ${expectedText}", paramsExclude = {"cellResult", "rowIndex", "collumnIndex", "textResult", "titleResult", "titleToValidate", "attributeToLookFor",
            "expectedAttributeValue"}, returnParam = {"textResult"})
    public void ifChildElementHaveText() throws Exception {
        WebElement childElement = lab.browser[browserIndex].getChildElement(recognizeBy, identifier, childRecognizeBy, childIdentifier);
        String childText = childElement.getText();
        textResult = StringUtils.stringCompare(childText, expectedText, stringOperator);
//        textResult = childText.equalsIgnoreCase(expectedText);
        super.addSubScenarioResult("Cell's Text " + stringOperator + " to expected", textResult);
        if (!textResult) windowsActionsWrapper.takeSnapshot();
        report.report("textResult=" + textResult);
    }

    /**
     * looking for an element: ${identifier} by: ${recognizeBy}
     * get its Attrribute's ${attributeToLookFor} value and compare it to: expectedAttributeValue
     * <p>
     * it its Equals return PASS else FAIL
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Validating if attribute: ${attributeToLookFor} of element ${identifier} by: ${recognizeBy} equals to", paramsExclude = {"textResult", "childRecognizeBy",
            "childIdentifier", "cellResult", "titleResult", "expectedText", "rowIndex", "collumnIndex", "titleToValidate"}, returnParam = {})
    public void isElementAttributeEqualsToValidation() throws Exception {
        String pageUrl = lab.browser[browserIndex].getDriver().getCurrentUrl();
        report.report("Validating if element exists on current page: " + pageUrl);
        boolean isElementAttributeValueLikeExpected = lab.browser[browserIndex].getValidator().isElementAttributeEqualsTo(recognizeBy, identifier, attributeToLookFor, expectedAttributeValue, stringOperator);
//        textResult=StringUtils.stringCompare(childText, expectedText, stringOperator);
        super.addSubScenarioResult("Attribute's value equals to expected: ", isElementAttributeValueLikeExpected);
        if (!isElementAttributeValueLikeExpected) windowsActionsWrapper.takeSnapshot();
    }

    /**
     * looking for an element: ${identifier} by: ${recognizeBy}
     * get its Attrribute's ${attributeToLookFor} value and compare it to: expectedAttributeValue
     * <p>
     * it its Equals return PASS else FAIL
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Validating if text of element '${identifier}' by: '${recognizeBy}' equals to: '${expectedText}'", paramsExclude = {"expectedAttributeValue", "attributeToLookFor", "textResult", "childRecognizeBy",
            "childIdentifier", "cellResult", "titleResult", "rowIndex", "collumnIndex", "titleToValidate"}, returnParam = {})
    public void isElementTextEqualsToExpectedValidation() throws Exception {

        textResult = lab.browser[browserIndex].getValidator().isElementTextEqualsToExpected(recognizeBy, identifier, expectedText, stringOperator);


        super.addSubScenarioResult("Found text " + stringOperator + " vs expected text", textResult);
        if (!textResult) windowsActionsWrapper.takeSnapshot();
    }


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public boolean isTextResult() {
        return textResult;
    }

    public void setTextResult(boolean textResult) {
        this.textResult = textResult;
    }

    public RecognizeBy getChildRecognizeBy() {
        return childRecognizeBy;
    }

    public void setChildRecognizeBy(RecognizeBy childRecognizeBy) {
        this.childRecognizeBy = childRecognizeBy;
    }

    public String getChildIdentifier() {
        return childIdentifier;
    }

    public void setChildIdentifier(String childIdentifier) {
        this.childIdentifier = childIdentifier;
    }

    public boolean isCellResult() {
        return cellResult;
    }

    public void setCellResult(boolean cellResult) {
        this.cellResult = cellResult;
    }

    public boolean isTitleResult() {
        return titleResult;
    }

    public void setTitleResult(boolean titleResult) {
        this.titleResult = titleResult;
    }

    public String getTitleToValidate() {
        return titleToValidate;
    }

    public void setTitleToValidate(String titleToValidate) {
        this.titleToValidate = titleToValidate;
    }

    public RecognizeBy getRecognizeBy() {
        return recognizeBy;
    }

    public void setRecognizeBy(RecognizeBy recognizeBy) {
        this.recognizeBy = recognizeBy;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isTakeSnapOnFail() {
        return takeSnapOnFail;
    }

    /**
     * if validation failed take the snapshot and attache it to log
     *
     * @param takeSnapOnFail
     * @section Snapshot
     */
    public void setTakeSnapOnFail(boolean takeSnapOnFail) {
        this.takeSnapOnFail = takeSnapOnFail;
    }

    public String getAttributeToLookFor() {
        return attributeToLookFor;
    }

    /**
     * attribute of the element to get its value
     *
     * @param attributeToLookFor
     */
    public void setAttributeToLookFor(String attributeToLookFor) {
        this.attributeToLookFor = attributeToLookFor;
    }

    public String getExpectedAttributeValue() {
        return expectedAttributeValue;
    }

    /**
     * expected value of the attribute
     *
     * @param expectedAttributeValue
     */
    public void setExpectedAttributeValue(String expectedAttributeValue) {
        this.expectedAttributeValue = expectedAttributeValue;
    }

    public StringOperator getStringOperator() {
        return stringOperator;
    }

    public void setStringOperator(StringOperator stringOperator) {
        this.stringOperator = stringOperator;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Table's row index (started from 0)
     *
     * @param rowIndex
     */
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCollumnIndex() {
        return collumnIndex;
    }

    /**
     * Table's collumn index (started from 0)
     *
     * @param collumnIndex
     */
    public void setCollumnIndex(int collumnIndex) {
        this.collumnIndex = collumnIndex;
    }

    public String getExpectedText() {
        return expectedText;
    }

    public void setExpectedText(String expectedText) {
        this.expectedText = expectedText;
    }


}
