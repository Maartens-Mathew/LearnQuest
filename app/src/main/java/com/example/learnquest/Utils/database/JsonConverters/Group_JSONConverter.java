package com.example.learnquest.Utils.database.JsonConverters;

import com.example.learnquest.model.group.Group;
import com.google.gson.*;
import java.lang.reflect.Type;

public class Group_JSONConverter implements JsonSerializer<Group>, JsonDeserializer<Group> {
    @Override
    public Group deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Integer groupID = jsonObject.has("groupID") ? jsonObject.get("groupID").getAsInt() : null;
        String topic = jsonObject.has("topic") ? jsonObject.get("topic").getAsString() : null;
        String description = jsonObject.has("description") ? jsonObject.get("description").getAsString() : null;
        Integer typeID = jsonObject.has("typeID") ? jsonObject.get("typeID").getAsInt() : null;
        String groupType = jsonObject.has("groupType") ? jsonObject.get("groupType").getAsString() : null;
        String groupColour = jsonObject.has("groupColour") ? jsonObject.get("groupColour").getAsString() : null;

        Group group = new Group();
        group.setGroupID(groupID);
        group.setTopic(topic);
        group.setDescription(description);
        group.setTypeID(typeID);
        group.setGroupType(groupType);
        group.setGroupColour(groupColour);


        return group;
    }

    @Override
    public JsonElement serialize(Group group, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("groupID_input", group.getGroupID());
        jsonObject.addProperty("topic_input", group.getTopic());
        jsonObject.addProperty("description_input", group.getDescription());
        jsonObject.addProperty("groupType_input", group.getGroupType());
        jsonObject.addProperty("groupColour_input", group.getGroupColour());
        jsonObject.addProperty("typeID_input", group.getTopic());

        return jsonObject;
    }
}
