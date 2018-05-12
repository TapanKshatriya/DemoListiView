package com.demolistiview.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demolistiview.R;

public class MainActivity extends AppCompatActivity {
    private Button buttonRecyclerView, buttonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        setListeners();
    }

    private void initControls() {
        buttonRecyclerView = findViewById(R.id.buttonRecyclerView);
        buttonListView = findViewById(R.id.buttonListView);
    }

    private void setListeners() {
        buttonRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecylerViewActivity.class);
                startActivity(intent);
            }
        });

        buttonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
