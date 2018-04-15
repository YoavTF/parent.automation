package com.cedex.automation.src.run;

public class TestUnit {
    private String name;
    private int result;
    private boolean isMandatory;
    private String[] jiraIssueList;

    public TestUnit(String name, int result, boolean isMandatory, String[] jiraIssueList) {
        this.name = name;
        this.result = result;
        this.isMandatory = isMandatory;
        this.jiraIssueList = jiraIssueList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String[] getJiraIssueList() {
        return jiraIssueList;
    }

    public void setJiraIssueList(String[] jiraIssueList) {
        this.jiraIssueList = jiraIssueList;
    }
}
