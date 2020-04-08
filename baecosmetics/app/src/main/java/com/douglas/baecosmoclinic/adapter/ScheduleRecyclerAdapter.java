package com.douglas.baecosmoclinic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douglas.bean.Service;
import com.example.baecosmoclinic.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aws on 28/01/2018.
 */

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Service> mData ;


    public ScheduleRecyclerAdapter(Context mContext, List<Service> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_layout_schedule,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.txtViewItem.setText(mData.get(position).getCategory());
        holder.txtViewItemId.setText(mData.get(position).getTitle());



     //   holder.txtService.setText(mData.get(position).getTitle());
        //holder.imgService.setImageResource(mData.get(position).getThumbnail());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(mContext,Book_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);*/

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewItem, txtViewItemId;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            txtViewItem = (TextView) view.findViewById(R.id.txtViewItem);
            txtViewItemId = (TextView) view.findViewById(R.id.txtViewItemId);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
    }



