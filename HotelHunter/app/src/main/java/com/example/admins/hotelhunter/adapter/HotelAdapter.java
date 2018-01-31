package com.example.admins.hotelhunter.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.model.HotelModel;
import com.example.admins.hotelhunter.model.ReviewModel;

import java.util.List;

/**
 * Created by Admins on 1/31/2018.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewholder> {
    private Context context;
    private List<HotelModel> hotelModels;

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HotelViewholder extends RecyclerView.ViewHolder {
        public HotelViewholder(View itemView) {
            super(itemView);
        }
    }
}
