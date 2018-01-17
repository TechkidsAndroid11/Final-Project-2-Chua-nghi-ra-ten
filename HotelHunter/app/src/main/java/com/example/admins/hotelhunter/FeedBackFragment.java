package com.example.admins.hotelhunter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admins.hotelhunter.activities.LoginActivity;
import com.example.admins.hotelhunter.model.ReviewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends Fragment {
    ReviewModel reviewModel;
    List<ReviewModel> reviewModelList= new ArrayList<>();
    FeedbackAdapter feedbackAdapter;
    RecyclerView rvFeedback;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_feed_back,container,false);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        rvFeedback= view.findViewById(R.id.rv_feedback);

        setupUI();
        loadData();
        return view;
    }

    private void loadData() {
        databaseReference.child(LoginActivity.userModel.name).child("danhGiaModels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ReviewModel reviewModel= d.getValue(ReviewModel.class);
                    reviewModelList.add(reviewModel);
                    feedbackAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupUI() {
        feedbackAdapter = new FeedbackAdapter(getContext(),reviewModelList);
        rvFeedback.setAdapter(feedbackAdapter);
        rvFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
