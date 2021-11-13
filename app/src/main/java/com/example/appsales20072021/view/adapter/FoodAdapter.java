package com.example.appsales20072021.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsales20072021.databinding.ItemFoodBinding;
import com.example.appsales20072021.model.FoodModel;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodModel> lstFoodModels;

    public FoodAdapter(){

    }

    void updateListFoodModels(List<FoodModel> foodModels){
        lstFoodModels = foodModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        ItemFoodBinding mBinding;

        public FoodViewHolder(ItemFoodBinding itemFoodBinding){
            super(itemFoodBinding.getRoot());
        }
        void bind(FoodModel foodModel){

        }
    }
}