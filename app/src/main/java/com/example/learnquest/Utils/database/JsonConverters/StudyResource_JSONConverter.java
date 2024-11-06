package com.example.learnquest.Utils.database.JsonConverters;

import com.example.learnquest.model.studyResource.StudyResource;
import com.google.gson.*;
import java.lang.reflect.Type;

public class StudyResource_JSONConverter implements JsonSerializer<StudyResource>, JsonDeserializer<StudyResource> {
    @Override
    public StudyResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        StudyResource resource = new StudyResource();

        JsonObject jsonObject = json.getAsJsonObject();


        resource.setResourceID(jsonObject.get("resourceID").isJsonNull() ? null : jsonObject.get("resourceID").getAsInt());
        resource.setFileName(jsonObject.get("fileName").isJsonNull() ? null : jsonObject.get("fileName").getAsString());
        resource.setFileType(jsonObject.get("fileType").isJsonNull() ? null : jsonObject.get("fileType").getAsString());
        resource.setGroupID(jsonObject.get("groupID").isJsonNull() ? null : jsonObject.get("groupID").getAsInt());
        resource.setURL(jsonObject.get("URL").isJsonNull() ? null : jsonObject.get("URL").getAsString());
        resource.setFilePath(jsonObject.get("filePath").isJsonNull() ? null : jsonObject.get("filePath").getAsString());
        resource.setDateAdded(jsonObject.get("dateAdded").isJsonNull() ? null : jsonObject.get("dateAdded").getAsString());

        return resource;

    }

    @Override
    public JsonElement serialize(StudyResource resource, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("resourceID_input", resource.getResourceID());
        object.addProperty("fileName_input", resource.getFileName());
        object.addProperty("fileType_input", resource.getFileType());
        object.addProperty("groupID_input", resource.getGroupID());
        object.addProperty("URL_input", resource.getURL());
        object.addProperty("filePath_input", resource.getFilePath());
        object.addProperty("dateAdded_input", resource.getDateAdded());
        return object;
    }
}
