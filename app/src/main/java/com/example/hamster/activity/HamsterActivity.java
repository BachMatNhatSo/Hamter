package com.example.hamster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hamster.R;
import com.example.hamster.adapter.hamsterAdapter;
import com.example.hamster.model.sanPhamMoi;
import com.example.hamster.retrofit.ApiHamster;
import com.example.hamster.retrofit.retrofitClient;
import com.example.hamster.utils.utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HamsterActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiHamster apiHamster;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page =1 ;
    int loai;
    hamsterAdapter adapterhamster;
    List<sanPhamMoi> sanPhamMoiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamster);
        apiHamster = retrofitClient.getInstance(utils.BASE_URL).create(ApiHamster.class);
        loai = getIntent().getIntExtra("loai", 1);

        AnhXa();
        ActionToolBar();
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiHamster.getSPTheoLoai(page,loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                sanPhamMoiList= sanPhamMoiModel.getResult();
                                adapterhamster =new hamsterAdapter(getApplicationContext(),sanPhamMoiList);
                                recyclerView.setAdapter(adapterhamster);
                            }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong the ket noi ", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarManHinhHamster);
        recyclerView =findViewById(R.id.rcvManHinhHamster);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}