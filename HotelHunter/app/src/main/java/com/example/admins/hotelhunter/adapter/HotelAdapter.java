package com.example.admins.hotelhunter.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.model.HotelModel;
import com.example.admins.hotelhunter.model.ReviewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Admins on 1/31/2018.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewholder> {
    private Context context;
    private List<HotelModel> hotelModels;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    public HotelAdapter(Context context, List<HotelModel> hotelModels) {
        this.context = context;
        this.hotelModels = hotelModels;
    }

    @Override
    public HotelAdapter.HotelViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.item_hotel,parent,false);
        return new HotelViewholder(view);
    }

    @Override
    public void onBindViewHolder(HotelAdapter.HotelViewholder holder, int position) {
        holder.setData(hotelModels.get(position));

    }

    @Override
    public int getItemCount() {
        return hotelModels.size();
    }

    public class HotelViewholder extends RecyclerView.ViewHolder {
        public HotelViewholder(View itemView) {
            super(itemView);
            TextView tvName= itemView.findViewById(R.id.tv_name);
            TextView tvPrice= itemView.findViewById(R.id.tv_price);
            TextView tvAddress= itemView.findViewById(R.id.tv_address);
            ImageView ivImage= itemView.findViewById(R.id.iv_image);
            RatingBar rbStar= itemView.findViewById(R.id.rb_star);
            TextView tvEdit= itemView.findViewById(R.id.tv_edit);
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        public void setData(HotelModel hotelModel) {

        }
    }
}
