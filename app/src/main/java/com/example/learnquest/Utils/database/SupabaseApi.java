package com.example.learnquest.Utils.database;


import static com.example.learnquest.AppState.App.groupID;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.Assessment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.StudentAssessment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.TrackProgressAssessmentData;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupMemberUserName;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupMembership;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.PendingUsersResult;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.QuizEntry;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.Tag;
import com.example.learnquest.model.studyResource.StudyResource;
import com.example.learnquest.model.user.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<Assessment>> getStudentAssessments(@Query("userID") String userID);

    @GET("Assessment")
    Call<List<com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings.Assessment>> getGroupAssessments(@Query("groupID") String groupID_input);

    @GET("Assessment")
    Call<List<Assessment>> getAssessments();


            //adding an assessment to the DB
    @POST("Assessment")
    Call<com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings.Assessment> addAssessment(@Body com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings.Assessment assessment);



    @GET("rpc/getUserGroups")
    Call<List<Group>> getUserGroups(@Query("userID_input") Integer userID_input);

    @DELETE("Assessment")
    Call<Void> deleteAssessmentsByGroupID(@Query("groupID") String groupID);

    @GET("rpc/getQuizEntries")
    Call<List<QuizEntry>> getQuizEntries(@Query("groupID_input") Integer groupID);

    @GET("rpc/getStudyResources")
    Call<List<StudyResource>> getStudyResources(@Query("groupID_input") Integer groupID_input);



//this gets all the quiz entries,
    //SQL functions added by Mathew. Start...
    @GET("rpc/hasPermission")
    Call<Boolean> hasPermission(@Query("userID_input") Integer userID);

    @GET("rpc/getWaitingQuizEntries")
    Call<List<QuizEntry>> getWaitingQuizEntries(@Query("groupID_input") Integer groupID_input);
//getWaitingQuizEntries
    @GET("rpc/getValidQuizEntries")
    Call<List<QuizEntry>> getValidQuizEntries(@Query("groupID_input") Integer groupID_input);
    //...endQuer
    @GET("rpc/getWaitingQuizQuestions")
    Call<List<QuizEntry>> getWaitingQuizQuestions(@Query("groupID_input") Integer groupID);



    @DELETE("QuizEntry")
    Call<Void> deleteQuizEntry(@Query("quizEntryID") String quizEntryID);//narsi delete eq for a given id (working)


    @POST("rpc/addQuizEntry")
    Call<Void> addQuizEntry(@Body QuizEntry quizEntry);



    @POST("rpc/updateQuizEntry")
    Call<Void> updateQuizEntry(@Body QuizEntry quizEntry);

//    @DELETE("QuizEntry")
//    Call<Void> deleteQuizEntry(@Query("quizEntryID") String QuizEntryID);


///////////////////////////////////////////////////////////////////////////////////A500
    @GET("Tag") // Replace with your Supabase endpoint for getting tags
    Call<List<Tag>> getTagsByGroup(@Query("groupID") String groupID);

    @POST("Tag")
    Call<Void> addTag(@Body Tag tag);

    @DELETE("Tag")
    Call<Void> deleteTag(@Query("tagID") String tagID);

    // Add the PUT method for updating a tag
//    @PUT("Tag")
//    Call<Void> updateTag(@Body Tag tag);

//    @PUT("Tag")
//    Call<Void> updateTagwID(@Body Tag tag);

    @PUT("Tag")
    Call<Void> updateTagwID(@Query("tagID") String idFilter, @Body Tag tag);


//    @PUT("Tag")
//    Call<Void> updateTag(@Path("id") String id, @Body Tag tag);

//    @PUT("Tag")
//    Call<Tag> updateTag(@Path("id") int id, @Body Tag tag);


    @GET("rpc/getAssessmentData")
    Call<List<TrackProgressAssessmentData>> getAssessmentData(@Query("UserID_input") int userID, @Query("GroupID_input") int groupID);


    @GET("rpc/getGroupMembers")
    Call<List<GroupMemberUserName>> getGroupMembers(@Query("GroupID_input") int groupID);

    @GET("Group")
    Call<Group> getGroup(@Query("groupID") int groupID);

    @GET("rpc/isUserValid")
    Call<Integer> isUserValid(@Query("username_input") String username_input, @Query("password_input") String password);

    @GET("rpc/getUser")
    Call<List<User>> getUser(@Query("username_input") String username_input);
    @POST("groupMembership")
    Call<Void> addGroupMembership(@Body GroupMembership newJoin);

    @DELETE("groupMembership")
    Call<Void> deleteGroupMembership(@Query("userID") String userID, @Query("groupID") String groupID);

    @GET("rpc/getUserWithID")
    Call<List<User>> getUserWithID(@Query("userID_input") String userID);
    @GET("rpc/getPendingUsers")
    Call<List<PendingUsersResult>> getPendingUsers(@Query("GroupID_input") Integer GroupID_input);



    @PUT("User")
    Call<Void> updateUser(@Query("userID") String userID, @Body User user);
    @PATCH("StudentAssessment")
    Call<Void> updateGoal(@Query("userID") String userID, @Query("assessmentID")String assessmentID, @Body Map<String, Object> body);

    @DELETE("StudentAssessment")
    Call<Void> deleteGoal(@Query("userID") String userID, @Query("assessmentID") String assessmentID);

    @GET("rpc/getUserGroups")
    Call<List<Group>> getUserGroups(@Query("userID_input") String userID_input);


    @PATCH("groupMembership")
    Call<Void> updateGroupMembership(@Query("userID") String userID, @Query("groupID") String groupID, @Body Map<String, Object> body);

    @DELETE("Group")
    Call<Void> deleteGroup(@Query("groupID") String groupID);
    @POST("Group")
    Call<Void> addGroup(@Body Group newGroup);

    @GET("Assessment")
    Call<List<Assessment>> getGroupAssessments(@Query("groupID") int groupID);

    @GET("StudentAssessment")
    Call<List<StudentAssessment>> getStudentAssessments(@Query("userID")int userID);

    @POST("StudentAssessment")
    Call<Void> addGoal(@Body StudentAssessment assessment);


//    @GET("/rest/v1/TaggedQuizEntry")
//    Call<List<TaggedQuizEntry>> getAllTaggedQuizEntries();
    //StudentAssessment class does not have all the fields like the table in the database so I don't know if api will work or how it will work
}
