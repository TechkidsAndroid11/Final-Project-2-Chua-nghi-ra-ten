package com.example.admins.hotelhunter.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.Utils.ImageUtils;
import com.example.admins.hotelhunter.database.onClickMyHotel;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Admins on 1/31/2018.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewholder> {
    private static final String TAG = HotelAdapter.class.toString();
    private Context context;
    private List<HotelModel> hotelModels;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public HotelAdapter(Context context, List<HotelModel> hotelModels) {
        this.context = context;
        this.hotelModels = hotelModels;
    }

    @Override
    public HotelAdapter.HotelViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_hotel, parent, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        return new HotelViewholder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewholder holder, int position) {
        holder.setData(hotelModels.get(position));
    }


    @Override
    public int getItemCount() {
        return hotelModels.size();
    }

    public class HotelViewholder extends RecyclerView.ViewHolder {
        ImageView ivMenu, ivImage;
        TextView tvName, tvPrice, tvAddress, tvEdit;
        RatingBar rbStar;

        public HotelViewholder(final View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            ivImage = itemView.findViewById(R.id.iv_image);
            rbStar = itemView.findViewById(R.id.rb_star);
            ivMenu.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                    menu.setHeaderTitle("Select The Action");
                    menu.add(0, R.id.main, 0, "Xóa").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_settings:
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                    dialogBuilder.setMessage("Bạn chắc chắn muốn xóa?")
                                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    firebaseAuth = FirebaseAuth.getInstance();
                                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                                    firebaseDatabase.getReference("hotels");


                                                }
                                            })
                                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                }
                                            })
                                            .show();


                            }
                            return true;
                        }
                    });//groupId, itemId, order, title

                }
            });

            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("users");
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Huid").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    String huid = d.getValue().toString();

                                    databaseReference = firebaseDatabase.getReference("hotels");
                                    databaseReference.orderByChild("key").equalTo(huid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Log.d(TAG, "onDataChange: ");
                                            if (dataSnapshot.getChildrenCount() > 0) {
                                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                    HotelModel hotelModel = d.getValue(HotelModel.class);
                                                    EventBus.getDefault().post(new onClickMyHotel(hotelModel));



                                                }
                                            }
                                            ;
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }



            });
        }

        public void setData(HotelModel hotelModel) {
            tvName.setText(hotelModel.nameHotel);
            tvAddress.setText(hotelModel.address);
            rbStar.setRating(hotelModel.danhGiaTB);
            tvPrice.setText(hotelModel.gia);

            ivImage.setImageBitmap(ImageUtils.base64ToImage(hotelModel.images.get(0)));
        }
    }
}
