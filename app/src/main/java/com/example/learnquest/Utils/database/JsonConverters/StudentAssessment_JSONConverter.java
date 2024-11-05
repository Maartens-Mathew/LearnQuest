package com.example.learnquest.Utils.database.JsonConverters;

import com.example.learnquest.model.assessment.StudentAssessment;
import com.google.gson.*;
import java.lang.reflect.Type;

public class StudentAssessment_JSONConverter implements JsonDeserializer<StudentAssessment> {

    @Override
    public StudentAssessment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Integer userID = jsonObject.has("userID") ? jsonObject.get("userID").getAsInt() : null;
        Integer assessmentID = jsonObject.has("assessmentID") ? jsonObject.get("assessmentID").getAsInt() : null;
        Float markObtained = jsonObject.has("markObtained") ? jsonObject.get("markObtained").getAsFloat() : null;
        Float idealMark = jsonObject.has("idealMark") ? jsonObject.get("idealMark").getAsFloat() : null;
        String feedBack = jsonObject.has("feedBack") ? jsonObject.get("feedBack").getAsString() : null;

        return new StudentAssessment(assessmentID, feedBack, idealMark, markObtained, userID);
    }


}