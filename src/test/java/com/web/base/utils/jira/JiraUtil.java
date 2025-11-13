package com.web.base.utils.jira;


import com.web.base.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.base.model.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class JiraUtil {
    private static final String BASE_URL = "";
    private static final String JIRA_PATH = "";
    private static final String BASIC_AUTH = "";

    public static boolean createIssue(final String projectKey, String title, final String description, final String attachmentPath, final String assigneeName){
        URL url = null;
        String issueKey = "";
        try {
            url = new URL(BASE_URL + JIRA_PATH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTH);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        JiraIssue jiraIssue = new JiraIssue();
        JiraFields jiraFields = new JiraFields();
        JiraProject jiraProject = new JiraProject();
        JiraSeverity jiraSeverity = new JiraSeverity();
        JiraIssueType jiraIssueType = new JiraIssueType();

        jiraSeverity.setValue("Normal");
        jiraIssueType.setName("System Alert");
        jiraFields.setIssuetype(jiraIssueType);
        jiraFields.setDescription(description);
        jiraFields.setSummary(title);
        jiraProject.setKey(projectKey);
        jiraFields.setProject(jiraProject);
        jiraIssue.setFields(jiraFields);
        ObjectMapper mapper = new ObjectMapper();
        String jiraIssueJson = null;
        try {
            jiraIssueJson = mapper.writeValueAsString(jiraIssue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Writer writer = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
            writer.write(jiraIssueJson);
            writer.flush();
            writer.close();
            if(connection.getResponseCode() == HttpsURLConnection.HTTP_CREATED){
                BufferedReader br = new BufferedReader(new InputStreamReader( connection.getInputStream(),"utf-8"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                final String responseContent = sb.toString();
                JiraResponse jiraResponse = (JiraResponse)mapper.readValue(responseContent, JiraResponse.class);
                issueKey = jiraResponse.getKey();
                System.out.println("JIRA ISSUE OPENED WITH KEY: " + issueKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addAttachment(issueKey, attachmentPath);
        return true;
    }

    private static boolean addAttachment(String issueKey, String fileName){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(BASE_URL + "/api/latest/issue/" + issueKey + "/attachments");
        httppost.setHeader("X-Atlassian-Token", "nocheck");
        httppost.setHeader("Authorization", "Basic " + BASIC_AUTH);

        File fileToUpload = new File(fileName);
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("file", new FileBody(fileToUpload));
        httppost.setEntity(entity);
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httppost);
            if(response.getStatusLine().getStatusCode() == HttpsURLConnection.HTTP_OK)
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
