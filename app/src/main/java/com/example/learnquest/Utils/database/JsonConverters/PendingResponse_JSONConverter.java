package com.example.learnquest.Utils.database.JsonConverters;

import com.example.learnquest.model.group.PendingResponse;
import com.google.gson.*;
import java.lang.reflect.Type;

public class PendingResponse_JSONConverter implements JsonSerializer<PendingResponse> {

    @Override
    public JsonElement serialize(PendingResponse src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("userID_input", src.getUserID());
        jsonObject.addProperty("groupID_input", src.getGroupID());
        jsonObject.addProperty("roleID_input", src.getRoleID());

        return jsonObject;
    }
}