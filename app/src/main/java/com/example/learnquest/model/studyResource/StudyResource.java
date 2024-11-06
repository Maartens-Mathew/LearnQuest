package com.example.learnquest.model.studyResource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class StudyResource implements Serializable {

    @SerializedName("resourceID")
    Integer resourceID;

    @SerializedName("fileName")
    String fileName;

    @SerializedName("fileType")
    String fileType;

    @SerializedName("dateAdded")
    String dateAdded;

    @SerializedName("URL")
    String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Expose
    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @SerializedName("person")
    String person;

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isPDF(){
        return fileType.equals("pdf");
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @SerializedName("groupID")
    Integer groupID;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public StudyResource() {
    }

    public StudyResource(String dateAdded, String fileName, String filePath, String fileType, Integer groupID, Integer resourceID) {
        this.dateAdded = dateAdded;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.groupID = groupID;
        this.resourceID = resourceID;
        this.URL = URL;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public Integer getResourceID() {
        return resourceID;
    }

    public void setResourceID(Integer resourceID) {
        this.resourceID = resourceID;
    }
}
