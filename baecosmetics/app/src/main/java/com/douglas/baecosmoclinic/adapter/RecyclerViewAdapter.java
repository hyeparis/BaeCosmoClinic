package com.douglas.baecosmoclinic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douglas.bean.Service;
import com.douglas.bean.ServiceCategory;
import com.douglas.bean.ServiceDetail;
import com.example.baecosmoclinic.R;
import com.example.baecosmoclinic.ServiceDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ServiceCategory> mData ;


    public RecyclerViewAdapter(Context mContext, List<ServiceCategory> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_item_cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtService.setText(mData.get(position).getCategory());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.imgService);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ServiceDetailActivity.class);

                // passing data to the book activity
                intent.putExtra("Category",mData.get(position).getCategory());

                // start the activity
                mContext.startActivity(intent);

            }


        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtService;
        ImageView imgService;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtService = (TextView) itemView.findViewById(R.id.txtService) ;
            imgService = (ImageView) itemView.findViewById(R.id.imgService);
            cardView = (CardView) itemView.findViewById(R.id.cardView);


        }
    }


}
