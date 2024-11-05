package com.example.learnquest.Utils.database.JsonConverters;

import com.example.learnquest.model.assessment.Assessment;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Assessment_JSONConverter implements JsonSerializer<Assessment>, JsonDeserializer<Assessment> {
    @Override
    public Assessment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the JsonObject from the JsonElement
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract values from the JsonObject
        Integer assessmentID = jsonObject.get("assessmentID").isJsonNull() ? null : jsonObject.get("assessmentID").getAsInt();
        String name = jsonObject.get("name").isJsonNull() ? null : jsonObject.get("name").getAsString();

        // Parse the dueDate using a date formatter (assuming date is stored as a string in JSON)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Adjust format as needed
        Date dueDate = null;
        try {
            dueDate = dateFormat.parse(jsonObject.get("dueDate").isJsonNull() ? null : jsonObject.get("dueDate").getAsString());
        } catch (ParseException e) {
            // Handle parsing exception (e.g., log an error)
        }

        Float weighting = jsonObject.get("weighting").getAsFloat();
        Integer groupID = jsonObject.get("groupID").getAsInt();

        // Create a new Assessment object and return it
        return new Assessment(assessmentID, dueDate, groupID, name, weighting);
    }

    @Override
    public JsonElement serialize(Assessment src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("assessmentID_input", src.getAssessmentID());
        jsonObject.addProperty("name_input", src.getName());

        // Format the dueDate using the same date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        jsonObject.addProperty("dueDate_input", dateFormat.format(src.getDueDate()));

        jsonObject.addProperty("weighting_input", src.getWeighting());
        jsonObject.addProperty("groupID_input", src.getGroupID());

        return jsonObject;
    }
}
