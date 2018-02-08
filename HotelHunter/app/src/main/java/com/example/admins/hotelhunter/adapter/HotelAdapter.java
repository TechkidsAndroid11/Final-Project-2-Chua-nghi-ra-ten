package com.example.admins.hotelhunter.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.Utils.ImageUtils;
import com.example.admins.hotelhunter.fragment.EditHotelFragment;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    RelativeLayout rlMain;


    public HotelAdapter(Context context, List<HotelModel> hotelModels) {
        this.context = context;
        this.hotelModels = hotelModels;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d(TAG, "HotelAdapter: ");
    }

    @Override
    public HotelAdapter.HotelViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_hotel_fix, parent, false);
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
            rlMain=itemView.findViewById(R.id.rl_main);
//            ivMenu.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//                    @Override
//                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
////                    menu.setHeaderTitle("Select The Action");

//
//                    }
//            });
//            itemView.setOnCreateContextMenuListener(this);
//            tvEdit = itemView.findViewById(R.id.tv_edit);
//            tvEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();

//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
//
//
//            });
            PopupMenu popupMenu = new PopupMenu(context, ivMenu);
            popupMenu.inflate(R.menu.main);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.action_settings:
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            dialogBuilder.setMessage("Bạn chắc chắn muốn xóa?")
                                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    })
                                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    })
                                    .show();
                            break;


                        case R.id.action_edit:
                            ImageUtils.openFragment(,R.id.rl_main, );
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
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
//            itemView.setOnLongClickListener(new View.OnLongClickListener()
//
//    {
//        @Override
//        public boolean onLongClick (View view){
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//        dialogBuilder.setMessage("Bạn chắc chắn muốn xóa?")
//                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        firebaseAuth = FirebaseAuth.getInstance();
//                        firebaseDatabase = FirebaseDatabase.getInstance();
//                        firebaseDatabase.getReference("hotels").addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot bookSnapShot : dataSnapshot.getChildren()) {
//
//
//                                    databaseReference.child(bookSnapShot.getKey()).removeValue();
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//                })
//                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                    }
//                })
//                .show();
//        return false;
//    }
//    });
//}


