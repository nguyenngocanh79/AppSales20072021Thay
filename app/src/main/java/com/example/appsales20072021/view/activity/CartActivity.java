package com.example.appsales20072021.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appsales20072021.databinding.ActivityCartBinding;
import com.example.appsales20072021.databinding.ActivityHomeBinding;
import com.example.appsales20072021.model.CartModel;
import com.example.appsales20072021.model.FoodModel;
import com.example.appsales20072021.model.OrderedItemModel;
import com.example.appsales20072021.repository.FoodRepository;
import com.example.appsales20072021.view.adapter.CartAdapter;
import com.example.appsales20072021.view.adapter.FoodAdapter;
import com.example.appsales20072021.viewmodel.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    //Binding
    ActivityCartBinding mBinding;
    //Model
    FoodViewModel mFoodViewModel;
    //Recyclerview
    RecyclerView mRcvCart;
    List<OrderedItemModel> mListOrderItemModel;
    CartAdapter mCartAdapter;

    //Token
    String token;

    //Quantity
    int orderedItemQuantity;
    OrderedItemModel orderedItemModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        //ViewModel
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        mFoodViewModel.updateFoodRepository(new FoodRepository());

        //Recyclerview
        mRcvCart= mBinding.recyclerView;
        mListOrderItemModel = new ArrayList<>();
        mCartAdapter = new CartAdapter();
        mCartAdapter.updateListOrderedItemModels(mListOrderItemModel);
        mRcvCart.setLayoutManager(new LinearLayoutManager(this));
        mRcvCart.setHasFixedSize(true);
        mRcvCart.setAdapter(mCartAdapter);

        //Token
        Intent intent = getIntent();
        if(intent != null){
            token = intent.getStringExtra("Token");
            Log.d("BBB", "Token: " + token);
        }

        observerData();
        event();

    }

    private void event() {
        //Load Cart
        mFoodViewModel.fetchCartModel(token);

        //Event khi click Recyclerview
        mCartAdapter.setOnCartListener(new CartAdapter.OnCartListener() {
            @Override
            public void setOnOrderedItemClickListener(int position, int type) {
                switch (type){
                    case 1: //Click v??o n??t "-"

                        break;
                    case 2: //Click v??o n??t "+"
                        orderedItemModel = mListOrderItemModel.get(position);
                        orderedItemModel.quantity = mListOrderItemModel.get(position).quantity+1;
                        orderedItemModel.orderId = mListOrderItemModel.get(position).orderId;
                        orderedItemModel.foodId = mListOrderItemModel.get(position).foodId;
                        mFoodViewModel.fetchUpdateResult(token,orderedItemModel);
                        break;
                }
            }
        });
    }

    private void observerData() {
        //Observe list c??c ordered items
        mFoodViewModel.getCartModelLiveData().observe(this, new Observer<CartModel>() {
            @Override
            public void onChanged(CartModel cartModel) {
                mListOrderItemModel = cartModel.items;
                mCartAdapter.updateListOrderedItemModels(cartModel.items);
                mBinding.textviewTotalAmount.setText(cartModel.total+"");
            }
        });
        //Observe k???t qu??? update
        mFoodViewModel.getUpdateResultLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    if (s.equals("OK")){
                        mFoodViewModel.fetchCartModel(token);
                    }
                }
            }
        });
    }
}