package com.example.learnquest.Utils.database.Serializers;
import com.google.gson.*;
//import org.javengers.learnquest.Settings.App;
//import org.javengers.learnquest.model.quizEntry.QuizEntry;
//import org.javengers.learnquest.model.quizEntry.Tag;
import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.QuizBank.QuizEntry;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuizEntry_JSONConverter implements JsonDeserializer<QuizEntry> , JsonSerializer<QuizEntry> {


    @Override
    public QuizEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();


        QuizEntry quizEntry = new QuizEntry();
        quizEntry.setQuizEntryID(jsonObject.get("quizEntryID").isJsonNull() ? null : jsonObject.get("quizEntryID").getAsInt());
        quizEntry.setQuestion(jsonObject.get("question").isJsonNull() ? null : jsonObject.get("question").getAsString());
        quizEntry.setAnswer(jsonObject.get("answer").isJsonNull() ? null : jsonObject.get("answer").getAsString());
        quizEntry.setDescription(jsonObject.get("description").isJsonNull() ? null : jsonObject.get("description").getAsString());
        quizEntry.setGroupID(jsonObject.get("groupID").isJsonNull() ? null : jsonObject.get("groupID").getAsInt());
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

    @Override
    public JsonElement serialize(QuizEntry quizEntry, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("quizEntryID_input", quizEntry.getQuizEntryID());
        jsonObject.addProperty("question_input", quizEntry.getQuestion());
        jsonObject.addProperty("answer_input", quizEntry.getAnswer());
        jsonObject.addProperty("description_input", quizEntry.getDescription());
        jsonObject.addProperty("groupID_input", quizEntry.getGroupID());//this is a problem
        jsonObject.addProperty("isValidated_input",quizEntry.getIsValid());
        jsonObject.addProperty("inContention_input", quizEntry.getInContention());

        JsonArray tagsArray = new JsonArray();

        for (Integer tagID : quizEntry.getTagsAsList()) {
            tagsArray.add(tagID);
        }
        jsonObject.add("tags_input", tagsArray);


        return jsonObject;
    }
}