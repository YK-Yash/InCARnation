package com.example.dell.incarnation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dell on 03-10-2017.
 */

public class Car_costAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Car_costListing_model> data= Collections.emptyList();
    Car_costListing_model current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public Car_costAdapter(Context context, List<Car_costListing_model> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_cars, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder= (MyHolder) holder;
        Car_costListing_model current=data.get(position);
        myHolder.textPlantName.setText(current.car_company);
        myHolder.textSize.setText(current.car_name);
        myHolder.textPrice.setText(current.cost_day);
        //myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        Glide.with(context).load("http://192.168.1.7/test/images/")
                .placeholder(R.mipmap.figo)
                .error(R.mipmap.figo)
                .into(myHolder.ivPlant);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textPlantName;
        ImageView ivPlant;
        TextView textSize;
        TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textPlantName= (TextView) itemView.findViewById(R.id.textPlantName);
            ivPlant= (ImageView) itemView.findViewById(R.id.ivFish);
            textSize = (TextView) itemView.findViewById(R.id.textSize);
            textPrice = (TextView) itemView.findViewById(R.id.seasonn);
        }

    }

}

