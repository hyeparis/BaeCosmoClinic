package com.douglas.baecosmoclinic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.douglas.bean.ServiceDetail;
import com.example.baecosmoclinic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceDetailAdapter extends RecyclerView.Adapter<ServiceDetailAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ServiceDetail> mData ;


    public ServiceDetailAdapter(Context mContext, List<ServiceDetail> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public ServiceDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.service_layout,parent,false);
        return new ServiceDetailAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ServiceDetailAdapter.MyViewHolder holder, final int position) {

        holder.txtTitle.setText(mData.get(position).getTitle());
        //holder.txtCategory.setText(mData.get(position).getCategory());
        holder.txtDescription.setText(mData.get(position).getDescription());

        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.imgService);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtCategory;
        TextView txtDescription;
        ImageView imgService;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtItemTitle);
            //txtCategory = (TextView) itemView.findViewById(R.id.txtItemCategory);
            txtDescription = (TextView) itemView.findViewById(R.id.txtVItemDes);

            imgService = (ImageView) itemView.findViewById(R.id.imgItemService);
            cardView = (CardView) itemView.findViewById(R.id.card_view3);
        }

    }
}
