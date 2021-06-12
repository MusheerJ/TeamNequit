package com.teamnequit.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamnequit.Activities.FeedBack.FeedBackActivity;
import com.teamnequit.Activities.MemberList.ViewMemberListActivity;
import com.teamnequit.Activities.Rules.TeamsRulesActivity;
import com.teamnequit.Activities.TeamGoals.TeamGoalsActivity;
import com.teamnequit.Activities.TeamReferenceDoc.TeamRefDocActivity;
import com.teamnequit.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //MemberList
        ImageView memberList = root.findViewById(R.id.teamMemberList);
        memberList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getContext(), ViewMemberListActivity.class));
            }
        });

        //FeedBack Activity
        ImageView feedBack = root.findViewById(R.id.teamFeedBack);
        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FeedBackActivity.class));
            }
        });

        //teamGoals
        ImageView teamGoals = root.findViewById(R.id.teamGoals);
        teamGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TeamGoalsActivity.class);
                startActivity(i);
            }
        });

        //teamRules
        ImageView teamRules = root.findViewById(R.id.teamRules);
        teamRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TeamsRulesActivity.class));
            }
        });


        //teamReferenceDoc
        ImageView teamRefDoc = root.findViewById(R.id.teamReferenceDoc);
        teamRefDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TeamRefDocActivity.class));
            }
        });



        return root;
    }
}