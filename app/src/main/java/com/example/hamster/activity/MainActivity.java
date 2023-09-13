package com.example.hamster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hamster.R;
import com.example.hamster.adapter.loaiSPAdapter;
import com.example.hamster.model.loaiSP;
import com.example.hamster.retrofit.ApiHamster;
import com.example.hamster.retrofit.retrofitClient;
import com.example.hamster.utils.utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    loaiSPAdapter loaiSPAdapter;
    List<loaiSP> mangLoaiSP;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiHamster apiHamster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiHamster = retrofitClient.getInstance(utils.BASE_URL).create(ApiHamster.class);


        AnhXa();
        ActionBar();

        if (isConnected(this)) {

            ActionViewFlipper();
            getLoaiSP();

        }else {
            Toast.makeText(getApplicationContext() ,"khong ket noi ", Toast.LENGTH_SHORT).show();
        }
    }
    private void AnhXa(){
        toolbar =findViewById(R.id.toolBarManHinhChinh);
        viewFlipper = findViewById(R.id.viewFlippermanHinhChinh);
        recyclerView = findViewById(R.id.rcvSanPham);
        navigationView = findViewById(R.id.navigationView);
        listViewManHinhChinh = findViewById(R.id.listViewManHinhChinh);
        drawerLayout = findViewById(R.id.drawerLayout);
        // khoi tao mang
        mangLoaiSP = new ArrayList<>();


    }
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionViewFlipper(){
        List<String> mangQC =new ArrayList<>();
        mangQC.add("https://hamsterkingdom.com/wp-content/uploads/2022/03/z3258207983551_2e91baac66a6f2751dbc2533d1fbbd49-800x671.jpg");
        mangQC.add("https://hamsterkingdom.com/wp-content/uploads/2022/03/z3258207978145_e37da36d57180a2ab7e67423bf4eea74-800x671.jpg");
        mangQC.add("https://hamsterkingdom.com/wp-content/uploads/2022/03/z3258207973574_f925a75207f40e94b031f00807b77d36-800x671.jpg");
        for(int i =0; i<mangQC.size();i++){
            ImageView imageView =new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangQC.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected())||(mobile !=null && mobile.isConnected())){
            return true;
        }else {

            return false;
        }
    }
     private void getLoaiSP(){
        compositeDisposable.add(apiHamster.getLoaiSP()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSPModel -> {
                            if(loaiSPModel.isSuccess()){
                                mangLoaiSP=loaiSPModel.getResult();
                                loaiSPAdapter = new loaiSPAdapter(getApplicationContext(),mangLoaiSP);
                                listViewManHinhChinh.setAdapter(loaiSPAdapter);
                            }
                        }
                ));
     }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}