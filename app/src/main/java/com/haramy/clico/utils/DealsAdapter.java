package com.haramy.clico.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haramy.clico.DealDetailActivity;
import com.haramy.clico.R;
import com.haramy.clico.model.Deal;

import java.util.ArrayList;

/**
 * Project: Clico.
 * Created by Dell on 10/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {

    private ArrayList<Deal> mDealsList;
    private Context mContext;

    public DealsAdapter(Context context, ArrayList<Deal> delalsList) {
        this.mContext = context;
        this.mDealsList = delalsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Deal deal = mDealsList.get(position);
        holder.title.setText(deal.getTitle());
        holder.location.setText(deal.getLocation());
        holder.duration.setText(deal.getDateFrom()+" -> "+deal.getDateTo());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Details of: "+mDealsList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, DealDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location, duration;
        public ImageView detailsArrowImg;
        public RelativeLayout container;

        public ViewHolder(View view) {
            super(view);
            container = (RelativeLayout)view;
            title = (TextView) view.findViewById(R.id.textView_title);
            location = (TextView) view.findViewById(R.id.textView_location);
            duration = (TextView) view.findViewById(R.id.textView_duration);
            detailsArrowImg = (ImageView) view.findViewById(R.id.imageView_arrow);
        }
    }

    public void onItemDismiss(int itemPosition){
        mDealsList.remove(itemPosition);
        notifyItemRemoved(itemPosition);
    }
}
