package com.adelia.nainggolan.absensisiswa.API;


import com.adelia.nainggolan.absensisiswa.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardCreateData(
            @Field("nama") String nama,
            @Field("kelas") String kelas,
            @Field("pelajaran") String pelajaran,
            @Field("keterangan") String keterangan,
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDeleteData(
            @Field("id") int id
    );

}
