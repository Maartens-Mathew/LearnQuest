package com.example.learnquest.Utils.database;


import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.Assessment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.StudentAssessment;
import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.model.user.User;
import com.example.learnquest.model.wrappers.TaggedQuiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * SupabaseApi interface to define the API endpoints
 * This interface defines the API endpoints for the Supabase API
 * It uses Retrofit annotations to define the HTTP methods and parameters
 * The methods defined in this interface will be used to make API calls
 * Typically,  GET annotations are used to fetch data from the API
 * The PUT annotation is used to insert data into the database
 * I would advise you to put all your api calls here, so login, add user, etc.
 *
 */
public interface SupabaseApi {
    @GET("/rest/v1/notes") // Your table name is "Example"
  //  Call<List<User>> getItems(@Query("select") String select); //first part of your query (select, update, insert, etc.)

    <T> Call<List<T>> getItems(@Query("select") String select);

    @POST("User")
    Call<User> addUser(@Body User user);

    @GET("StudentAssessment")
    Call<List<StudentAssessment>> getStudentAssessments(@Query("userID") String userID);

    @GET("Assessment")
    Call<List<Assessment>> getAssessments();


            //adding an assessment to the DB
    @POST("Assessment")
    Call<com.example.learnquest.SetWeightings.Assessment> addAssessment(@Body com.example.learnquest.SetWeightings.Assessment assessment);




    @DELETE("Assessment")
    Call<Void> deleteAssessmentsByGroupID(@Query("groupID") String groupID);



//this gets all the quiz entries,
    //SQL functions added by Mathew. Start...
    @GET("rpc/hasPermission")
    Call<Boolean> hasPermission(@Query("userID_input") Integer userID);

    @GET("rpc/getWaitingQuizQuestions")
    Call<List<TaggedQuiz>> getWaitingQuizQuestions(@Query("groupID_input") Integer groupID);

    @GET("rpc/getValidQuizQuestions")
    Call<List<TaggedQuiz>> getValidQuizQuestions(@Query("groupID_input") Integer groupID);
    //...end

    @GET("rpc/getQuizEntries")
    Call<List<TaggedQuiz>> getQuizEntries(@Query("groupID_input") Integer groupID_input);


    @GET("rpc/getQuizEntries4")
    Call<List<QuizEntry>> getQuizEntries4(@Query("groupID_input") Integer groupID_input);

    @DELETE("QuizEntry")
    Call<Void> deleteQuizEntry(@Query("quizEntryID") String quizEntryID);//narsi delete eq for a given id (working)









//    @GET("/rest/v1/TaggedQuizEntry")
//    Call<List<TaggedQuizEntry>> getAllTaggedQuizEntries();
    //StudentAssessment class does not have all the fields like the table in the database so I don't know if api will work or how it will work
}
