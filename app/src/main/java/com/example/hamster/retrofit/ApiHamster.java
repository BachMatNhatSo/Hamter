package com.example.hamster.retrofit;


import com.example.hamster.model.loaiSPModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiHamster {
    @GET("getsanpham.php")
    Observable<loaiSPModel> getLoaiSP();
}
