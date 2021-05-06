package com.example.messedup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView userTV, titleTV, descTV;
    ApiInterface apiInterface;
    List<PostData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTV = findViewById(R.id.userId);
        titleTV = findViewById(R.id.title);
        descTV = findViewById(R.id.desc);


        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>();

        apiInterface.getPost().enqueue(new Callback<List<PostData>>() {
            @Override
            public void onResponse(Call<List<PostData>> call, Response<List<PostData>> response) {

                if(response.body().size() > 0) {

                    list.addAll(response.body());

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId() == 3) {
                            float userId = (list.get(i).getUserId());
                            String title = list.get(i).getTitle();
                            String desc = list.get(i).getBody();

                            userTV.setText("Id - " + userId);
                            titleTV.setText("Title - " + title);
                            descTV.setText("Desc - " + desc);
                        }
                    }

                    Toast.makeText(MainActivity.this, "List is not empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "List is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}