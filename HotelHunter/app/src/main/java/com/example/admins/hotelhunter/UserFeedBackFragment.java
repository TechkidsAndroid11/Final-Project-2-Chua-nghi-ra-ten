package com.example.admins.hotelhunter;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admins.hotelhunter.model.ReviewModel;
import com.example.admins.hotelhunter.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFeedBackFragment extends Fragment {

    private TextView tvPhone, tvName;
    private RatingBar rbRate;
    private EditText edComment;
    private Button btPost;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private UserModel userModel;

    private String qrcode;


    public UserFeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_feed_back, container, false);
        EventBus.getDefault().register(this);
//        String strtext = getArguments().getString("edttext");
//        Log.d("tag", "onCreateView: " + strtext);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        tvPhone = view.findViewById(R.id.tv_phone);
        tvName = view.findViewById(R.id.tv_name);
        rbRate = view.findViewById(R.id.rb_rating);
        edComment = view.findViewById(R.id.et_comment);
        btPost = view.findViewById(R.id.bt_post);

        databaseReference.child("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userModel = dataSnapshot.getValue(UserModel.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("2").child("reviewModelList")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<ReviewModel> reviewModelList = new ArrayList<>();
                                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                    ReviewModel reviewModel = reviewSnapshot.getValue(ReviewModel.class);
                                    reviewModelList.add(reviewModel);
                                }


                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();


                                ReviewModel review3 = new ReviewModel(
                                        dateFormat.format(date),
                                        edComment.getText().toString(),
                                        rbRate.getNumStars()
                                );
                                reviewModelList.add(review3);

                                userModel.reviewModels = reviewModelList;
                                databaseReference.child("2")
                                        .setValue(userModel);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        return view;
    }


}
