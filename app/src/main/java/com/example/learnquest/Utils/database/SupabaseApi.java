package com.example.learnquest.Utils.database;


import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.Assessment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.StudentAssessment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupMembership;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Role;
import com.example.learnquest.model.studyResource.StudyResource;
import com.example.learnquest.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    @GET("notes") // Your table name is "Example"
    Call<List<User>> getItems(@Query("select") String select); //first part of your query (select, update, insert, etc.)

    @POST("User")
    Call<User> addUser(@Body User user);

    @GET("StudentAssessment")
    Call<List<StudentAssessment>> getStudentAssessments(@Query("userID") String userID);

    @GET("Assessment")
    Call<List<Assessment>> getAssessments();

    @GET("Role")
    Call<List<Role>> getRoles();

    @GET("GroupMembership")
    Call<List<GroupMembership>> getGroupMembership(@Query("groupID") String groupID);


    @POST("rpc/getStudyResources")
    Call<List<StudyResource>> getStudyResources();

    //StudentAssessment class does not have all the fields like the table in the database so I don't know if api will work or how it will work
}
