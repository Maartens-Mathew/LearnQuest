package com.example.learnquest.model.studyResource;

import com.google.gson.annotations.SerializedName;

public class StudyResource {

    @SerializedName("resourceID")
    Short resourceID;

    @SerializedName("fileName")
    String fileName;

    @SerializedName("fileType")
    String fileType;

    @SerializedName("filePathURL")
    String filePathURL;

    @SerializedName("groupID")
    Short groupID;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePathURL() {
        return filePathURL;
    }

    public void setFilePathURL(String filePathURL) {
        this.filePathURL = filePathURL;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Short getGroupID() {
        return groupID;
    }

    public void setGroupID(Short groupID) {
        this.groupID = groupID;
    }

    public Short getResourceID() {
        return resourceID;
    }

    public void setResourceID(Short resourceID) {
        this.resourceID = resourceID;
    }
}
