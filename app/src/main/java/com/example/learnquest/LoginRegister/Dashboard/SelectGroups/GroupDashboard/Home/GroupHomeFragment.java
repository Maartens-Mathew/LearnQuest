package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups.PendingUsersActivity;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups.ViewGroupMembersActivity;
import com.example.learnquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String GROUP_HOME_FRAGMENT = "GroupHomeFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupHomeFragment newInstance(String param1, String param2) {
        GroupHomeFragment fragment = new GroupHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnJoinRequest = view.findViewById(R.id.join_requests_button_group_home_fragment);
        btnJoinRequest.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), PendingUsersActivity.class);
            startActivity(intent);
        });
        Button btnLeaveGroup = view.findViewById(R.id.btnLeaveGroup);
        Button btnViewMembers = view.findViewById(R.id.btnViewMembers);
        btnViewMembers.setOnClickListener(this::btnViewMembersClicked);
        btnLeaveGroup.setOnClickListener(this::btnLeaveGroupClicked);
    }

    public void btnLeaveGroupClicked(View v){
        Call<Void> leaveGroupCall = App.api.removeUser(App.user.getUserID(), App.group.getGroupID());
        leaveGroupCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(GROUP_HOME_FRAGMENT,"response is successful");
                }
                else{
                    Log.e(GROUP_HOME_FRAGMENT, response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(GROUP_HOME_FRAGMENT, throwable.getStackTrace().toString());
            }
        });
    }

    public void btnViewMembersClicked(View v){
        Intent intent = new Intent(getContext(), ViewGroupMembersActivity.class);
        intent.putExtra("isModerator",false);
        startActivity(intent);
    }

    public void btnRemoveMemberClicked(View v){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_dashboard, container, false);
    }
}