package com.web.base.model;

public class JiraFields {
    JiraProject project;
    String summary;
    String description;
    JiraIssueType issuetype;

    public JiraProject getProject() {
        return project;
    }

    public void setProject(JiraProject project) {
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JiraIssueType getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(JiraIssueType issuetype) {
        this.issuetype = issuetype;
    }
}
