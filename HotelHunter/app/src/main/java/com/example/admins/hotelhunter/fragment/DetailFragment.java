package com.example.admins.hotelhunter.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.activities.LoginActivity;
import com.example.admins.hotelhunter.adapter.FeedbackAdapter;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.model.HotelModel;
import com.example.admins.hotelhunter.model.ReviewModel;
import com.example.admins.hotelhunter.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.admins.hotelhunter.activities.LoginActivity.userModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = DetailFragment.class.toString();
    TextView tvGia;
    RelativeLayout rlWifi, rlNongLanh, rlDieuHoa, rlThangMay;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvRate;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<ReviewModel> reviewModelList = new ArrayList<>();
    FeedbackAdapter feedbackAdapter;
    RecyclerView rvFeedback;
    public HotelModel hotelModel;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        EventBus.getDefault().register(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("hotels");
//        databaseReference.child("reviewModels").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
//                {
//                    ReviewModel reviewModel = postSnapshot.getValue(ReviewModel.class);
//                    reviewModelList.add(reviewModel);
//                    Log.d(TAG, "onDataChange: "+reviewModelList);
//
//                    feedbackAdapter = new FeedbackAdapter(getContext(),reviewModelList);
//                    rvFeedback.setAdapter(feedbackAdapter);
//                    rvFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        setupUI(view);
        loadData();
        return view;
    }

    @SuppressLint("Range")
    private void loadData() {
        AlphaAnimation alpha = new AlphaAnimation(0.1F, 0.1F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        if (!hotelModel.dieuHoa) {
            rlDieuHoa.startAnimation(alpha);
        }

        if (!hotelModel.wifi) {
            rlWifi.startAnimation(alpha);
        }

        if (!hotelModel.nongLanh) {
            rlNongLanh.startAnimation(alpha);
        }

        if (!hotelModel.thangMay) {
            rlThangMay.startAnimation(alpha);
        }

        tvAddress.setText(hotelModel.address);

        tvPhone.setText(hotelModel.phone);
        tvGia.setText(hotelModel.gia + "VNĐ");
        if (hotelModel.danhGiaTB >= 0 && hotelModel.danhGiaTB < 1) {
            tvRate.setText("Rất kém");
        }


        if (hotelModel.danhGiaTB >= 1 && hotelModel.danhGiaTB < 2) {
            tvRate.setText("Kém");
        }


        if (hotelModel.danhGiaTB >= 2 && hotelModel.danhGiaTB < 3) {
            tvRate.setText("Bình thường");
        }


        if (hotelModel.danhGiaTB >= 3 && hotelModel.danhGiaTB < 4) {
            tvRate.setText("Tốt");
        }


        if (hotelModel.danhGiaTB >= 4 && hotelModel.danhGiaTB <= 5) {
            tvRate.setText("Rất tốt");
        }
    }

    private void setupUI(View view) {
        rlDieuHoa = view.findViewById(R.id.rl_dieu_hoa);
        rlNongLanh = view.findViewById(R.id.rl_nong_lanh);
        rlThangMay = view.findViewById(R.id.rl_thang_may);
        rlWifi = view.findViewById(R.id.rl_wifi);
        tvAddress = view.findViewById(R.id.tv_address);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvRate = view.findViewById(R.id.tv_rating);
        tvGia = view.findViewById(R.id.tv_gia);
        tvRate.setOnClickListener(this);
        rvFeedback = view.findViewById(R.id.rv_feedback);
        feedbackAdapter = new FeedbackAdapter(getContext(),hotelModel.reviewModels);
        rvFeedback.setAdapter(feedbackAdapter);
        rvFeedback.setLayoutManager(new LinearLayoutManager(getContext()));




    }

    @Subscribe(sticky = true)
    public void getHotelModel(final OnClickWindowinfo onClickWindowinfo) {
        hotelModel = onClickWindowinfo.hotelModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rating: {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    LayoutInflater layoutInflater = this.getLayoutInflater();
                    View dialogView = layoutInflater.inflate(R.layout.fragment_user_feed_back, null);
                    dialogBuilder.setView(dialogView);
                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                    final RatingBar rbRate = dialogView.findViewById(R.id.rb_rating);
                    final EditText etComment = dialogView.findViewById(R.id.et_comment);
                    Log.d(TAG, "onClick: " + etComment.getText().toString());
                    Button btPost = dialogView.findViewById(R.id.bt_post);
                    databaseReference = firebaseDatabase.getReference("users");
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                        public void onClick(View v) {


                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("reviewModels")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            List<ReviewModel> reviewModelList = new ArrayList<>();

                                            if (dataSnapshot.getChildrenCount() > 0) {
                                                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                                    ReviewModel reviewModel = reviewSnapshot.getValue(ReviewModel.class);
                                                    reviewModelList.add(reviewModel);
                                                }
                                            }

                                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                                            Date date = Calendar.getInstance().getTime();


                                            ReviewModel review = new ReviewModel(
                                                    firebaseAuth.getCurrentUser().getDisplayName(),
                                                    dateFormat.format(date),
                                                    etComment.getText().toString(),

                                                    rbRate.getRating()
                                            );
                                            reviewModelList.add(review);


                                            userModel.reviewModels = reviewModelList;

                                            databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                                                    .setValue(userModel);


                                        }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                                        }
                                    });
                            databaseReference = firebaseDatabase.getReference("hotels");
                            databaseReference.orderByChild("phone").equalTo(hotelModel.phone).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        hotelModel = data.getValue(HotelModel.class);
                                        Log.d(TAG, "onDataChange.: " + hotelModel.phone);
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                        Date date = Calendar.getInstance().getTime();


                                        ReviewModel review = new ReviewModel(
                                                firebaseAuth.getCurrentUser().getDisplayName(),
                                                dateFormat.format(date),
                                                etComment.getText().toString(),

                                                rbRate.getRating()
                                        );
                                        if (hotelModel.reviewModels == null) {
                                            List<ReviewModel>reviewModels= new ArrayList<>();
                                            reviewModels.add(review);
                                            hotelModel.reviewModels=reviewModels;
                                        } else {
                                            hotelModel.reviewModels.add(review);
                                        }
                                        databaseReference.child(data.getKey()).setValue(hotelModel);



                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            alertDialog.dismiss();

                        }


                    });


                }

            }
        }
    }
}