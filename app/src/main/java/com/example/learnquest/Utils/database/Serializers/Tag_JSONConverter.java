package com.example.learnquest.Utils.database.Serializers;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.Tag;
import com.google.gson.*;


import java.lang.reflect.Type;

public class Tag_JSONConverter implements JsonDeserializer<Tag>, JsonSerializer<Tag> {
    @Override
    public Tag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract fields from the JSON
        Integer tagID = jsonObject.get("tagID").isJsonNull() ? null : jsonObject.get("tagID").getAsInt();
        Integer groupID = jsonObject.get("groupID").isJsonNull() ? null : jsonObject.get("groupID").getAsInt();
        String tagName = jsonObject.get("tagName").isJsonNull() ? null : jsonObject.get("tagName").getAsString();
        String tagColour = jsonObject.get("tagColour").isJsonNull() ? null : jsonObject.get("tagColour").getAsString();

        // Create and return the Tag object
        Tag tag = new Tag();
        tag.setTagID(tagID);
        tag.setGroupID(groupID);
        tag.setTagName(tagName);
        tag.setTagColour(tagColour);

        return tag;
    }

    @Override
    public JsonElement serialize(Tag tag, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Convert fields to JSON
        jsonObject.addProperty("tagID", tag.getTagID());
        jsonObject.addProperty("groupID", tag.getGroupID());
        jsonObject.addProperty("tagName", tag.getTagName());
        jsonObject.addProperty("tagColour", tag.getTagColour());

        return jsonObject;
    }
}
