package com.example.learnquest.Utils.database.Serializers;
import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.QuizBank.Tag;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuizEntryDeserializer implements JsonDeserializer<QuizEntry> {

    @Override
    public QuizEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();


        QuizEntry quizEntry = new QuizEntry();
        quizEntry.setQuizEntryID(jsonObject.get("quizEntryID").isJsonNull() ? null : jsonObject.get("quizEntryID").getAsInt());
        quizEntry.setQuestion(jsonObject.get("question").isJsonNull() ? null : jsonObject.get("question").getAsString());
        quizEntry.setAnswer(jsonObject.get("answer").isJsonNull() ? null : jsonObject.get("answer").getAsString());
        quizEntry.setDescription(jsonObject.get("description").isJsonNull() ? null : jsonObject.get("description").getAsString());


        JsonArray tagsArray = jsonObject.getAsJsonArray("tags");

        JsonObject test = tagsArray.get(0).getAsJsonObject();
        if (!test.get("tagID").isJsonNull()) {


            List<Tag> tags = new ArrayList<>();
            for (JsonElement tagElement : tagsArray) {
                JsonObject tagObject = tagElement.getAsJsonObject();
                Tag tag = new Tag();
                tag.setTagID(tagObject.get("tagID").isJsonNull() ? null : tagObject.get("tagID").getAsInt());
                tag.setTagName(tagObject.get("tagName").isJsonNull() ? null : tagObject.get("tagName").getAsString());
                tag.setTagColour(tagObject.get("tagColour").isJsonNull() ? null : tagObject.get("tagColour").getAsString());
                tags.add(tag);
            }
            quizEntry.setTags(tags);
        }

        return quizEntry;
    }
}
