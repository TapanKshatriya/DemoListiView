package com.demolistiview.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demolistiview.R;
import com.demolistiview.adapters.RecyclerViewAdapter;
import com.demolistiview.interfaces.ApiInterface;
import com.demolistiview.model.ImageList;
import com.demolistiview.utils.ApiClient;
import com.demolistiview.utils.Utilities;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecylerViewActivity extends AppCompatActivity {
    private List<String> imageList;
    private RecyclerView recyclerView;
    private TextView textViewDefault;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter mAdapter;
    private Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view);
        initControls();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (utilities.isNetworkConnected()) {
//            swipeRefreshLayout.setRefreshing(true);
            utilities.showProgressDialog(null, "Please wait...");
            getImageList();
        } else
            utilities.showSimpleMessageDialog("Error", "Please check your internet connection.");

    }

    private void initControls() {
        utilities = new Utilities(RecylerViewActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        textViewDefault = findViewById(R.id.textViewDefault);

    }

    private void setListeners() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (utilities.isNetworkConnected()) {
                    swipeRefreshLayout.setRefreshing(true);
                    getImageList();
                } else
                    utilities.showSimpleMessageDialog("Error", "Please check your internet connection.");
            }
        });
    }

    private void getImageList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ImageList> call = apiService.getImageList();
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                utilities.dismissProgressDialog();
                swipeRefreshLayout.setRefreshing(false);
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (response.body().getMessage() != null) {
                        imageList = response.body().getMessage();
                        mAdapter = new RecyclerViewAdapter(imageList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        textViewDefault.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textViewDefault.setVisibility(View.GONE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textViewDefault.setVisibility(View.VISIBLE);
                    Toast.makeText(RecylerViewActivity.this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                // Log error here since request failed
                Log.e("DataException", t.toString());
                utilities.dismissProgressDialog();
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                textViewDefault.setVisibility(View.VISIBLE);
                Toast.makeText(RecylerViewActivity.this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
