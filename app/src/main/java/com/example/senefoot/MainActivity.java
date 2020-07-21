package com.example.senefoot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.senefoot.adapter.EquipeAdapter;
import com.example.senefoot.model.Equipe;
import com.example.senefoot.rest.ApiClient;
import com.example.senefoot.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiInterface apiService;
    public static List<Equipe> equipeList = new ArrayList<>();
    public static String met="";
    private EquipeAdapter equipeAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_team);

        mRecyclerView.setHasFixedSize(true);
        //mLayoutManager = new GridLayoutManager(MetsActivity.this,2,GridLayoutManager.VERTICAL,false);
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        //it get the title of menu initialized before in MenuActivity
        met=getIntent().getStringExtra("MET");

        //Integrate Retrofit
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Equipe>> call= apiService.listeEquipes("senior");
        /*Log the URL called*/
        Log.d("URL Called", call.request().url() + "");
        call.enqueue(new Callback<List<Equipe>>() {
            @Override
            public void onResponse(Call<List<Equipe>> call, Response<List<Equipe>> response) {
                Log.d("Tous les équipes", response.body()+"");
                Toast.makeText(MainActivity.this, response.body()+"", Toast.LENGTH_SHORT).show();
                equipeList = response.body();
                showEquipe();

            }

            @Override
            public void onFailure(Call<List<Equipe>> call, Throwable t) {
                Log.e("Error",t.getMessage());

            }
        });
/*
        call.enqueue(new Callback<List<E>>() {
            @Override
            public void onResponse(Call<List<Met>> call, Response<List<Met>> response) {
                metsRetrofit= response.body();

                Log.d("response cmd", response.body()+"");
                swipeRefresh_projet_list.setRefreshing(false);


                showMet();
            }

            @Override
            public void onFailure(Call<List<Met>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });*/
    }

    // this method manage the view of Equipes
    public void showEquipe() {
        equipeAdapter =new EquipeAdapter(equipeList);
        mRecyclerView.setAdapter(equipeAdapter);

    }
}