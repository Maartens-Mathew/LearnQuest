package com.example.learnquest.Utils.database;


import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageAssessments.Assessment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.StudentAssessment;
import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.QuizBank.TaggedQuizEntry;
import com.example.learnquest.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<List<User>> getItems(@Query("select") String select); //first part of your query (select, update, insert, etc.)

    <T> Call<List<T>> getItems(@Query("select") String select);

    @POST("/rest/v1/User")
    Call<User> addUser(@Body User user);

    @GET("/rest/v1/StudentAssessment")
    Call<List<StudentAssessment>> getStudentAssessments(@Query("userID") String userID);

    @GET("/rest/v1/Assessment")
    Call<List<Assessment>> getAssessments();


            //adding an assessment to the DB
    @POST("/rest/v1/Assessment")
    Call<com.example.learnquest.SetWeightings.Assessment> addAssessment(@Body com.example.learnquest.SetWeightings.Assessment assessment);




    @DELETE("/rest/v1/Assessment")
    Call<Void> deleteAssessmentsByGroupID(@Query("groupID") String groupID);


    @GET("/rest/v1/QuizEntry")
    Call<List<QuizEntry>> getAllQuizEntries();

    @GET("/rest/v1/Tag")
    Call<List<Tag>> getAllTags();


    @GET("/rest/v1/TaggedQuizEntry")
    Call<List<TaggedQuizEntry>> getAllTaggedQuizEntries();
    //StudentAssessment class does not have all the fields like the table in the database so I don't know if api will work or how it will work
}
