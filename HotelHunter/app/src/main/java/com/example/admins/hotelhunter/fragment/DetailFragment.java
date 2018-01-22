package com.example.admins.hotelhunter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admins.hotelhunter.FeedbackAdapter;
import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.activities.LoginActivity;
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
    private static final String TAG = "DetailFragment";
    CheckBox cbWifi, cbDieuHoa, cbNongLanh, cbThangMay;
    TextView tvDienThoai, tvDiaChi, tvFeedback;
    HotelModel hotelModel;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<ReviewModel> reviewModelList = new ArrayList<>();
    FeedbackAdapter feedbackAdapter;
    RecyclerView rvFeedback;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        EventBus.getDefault().register(this);
        setupUI(view);
        loadData();
        return view;
    }

    private void loadData() {
        tvDiaChi.setText(hotelModel.address);
        tvDienThoai.setText(hotelModel.phone);
        cbWifi.setChecked(hotelModel.wifi);
        cbDieuHoa.setChecked(hotelModel.dieuHoa);
        cbNongLanh.setChecked(hotelModel.nongLanh);
        cbThangMay.setChecked(hotelModel.thangMay);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        tvFeedback.setOnClickListener(this);
    }


    private void setupUI(View view) {
        cbWifi = view.findViewById(R.id.cb_1);
        cbDieuHoa = view.findViewById(R.id.cb_dieu_hoa);
        cbNongLanh = view.findViewById(R.id.cb_nong_lanh);
        cbThangMay = view.findViewById(R.id.cb_thang_may);
        tvDienThoai = view.findViewById(R.id.tv_so_dien_thoai);
        tvDiaChi = view.findViewById(R.id.tv_dia_chi_2);
        tvFeedback = view.findViewById(R.id.tv_feedback11);
        rvFeedback = view.findViewById(R.id.rv_feedback);


    }

    @Subscribe(sticky = true)
    public void getHotelModel(final OnClickWindowinfo onClickWindowinfo) {
        hotelModel = onClickWindowinfo.hotelModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback11: {
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
                            // child phai la reviewModels, k phai reviewModelList.
                            // de nhu cu sai ten so vs database firebase nen code k chay vao
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
                                            // nhu nay ms lay dc tgian hien tai. de nhu cu no null
                                            Date date = Calendar.getInstance().getTime();

                                            // sua lai constructor cua thg review model, constructor cu bi sai
                                            // rating phai la float chu k phai int
                                            ReviewModel review = new ReviewModel(
                                                    firebaseAuth.getCurrentUser().getDisplayName(),
                                                    dateFormat.format(date),
                                                    etComment.getText().toString(),
                                                    //getRating ms la lay rate, getNumStar no luc nao cung return 5
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
                            databaseReference.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    hotelModel = dataSnapshot.getValue(HotelModel.class);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            databaseReference.child(FirebaseAuth.getInstance().getUid()).child("reviewModels")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getChildrenCount() > 0) {
                                                List<ReviewModel> reviewModelList = new ArrayList<>();
                                                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                                    ReviewModel reviewModel = reviewSnapshot.getValue(ReviewModel.class);
                                                    reviewModelList.add(reviewModel);
                                                }


                                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                                Date date = Calendar.getInstance().getTime();


                                                ReviewModel review = new ReviewModel(
                                                        firebaseAuth.getCurrentUser().getDisplayName(),
                                                        dateFormat.format(date),
                                                        etComment.getText().toString(),

                                                        rbRate.getRating()
                                                );
                                                Log.d(TAG, "onDataChange: " + review);
                                                reviewModelList.add(review);


                                                hotelModel.reviewModels = reviewModelList;

                                                databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                                                        .setValue(hotelModel);


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

