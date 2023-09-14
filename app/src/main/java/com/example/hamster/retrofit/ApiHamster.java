package com.example.hamster.retrofit;


import com.example.hamster.model.loaiSPModel;
import com.example.hamster.model.sanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiHamster {
    @GET("getsanpham.php")
    Observable<loaiSPModel> getLoaiSP();

    @GET("getsanphammoi.php")
    Observable<sanPhamMoiModel> getSPMoi();

    @POST("getSpTheoLoai.php")
    @FormUrlEncoded
    Observable<sanPhamMoiModel> getSPTheoLoai(
            @Field("page") int page,
            @Field("loai") int loai
    );

}
