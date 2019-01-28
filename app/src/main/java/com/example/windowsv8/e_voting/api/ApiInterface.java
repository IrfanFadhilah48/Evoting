package com.example.windowsv8.e_voting.api;

import com.example.windowsv8.e_voting.model.Pemilih;
import com.example.windowsv8.e_voting.model.ResponseCek;
import com.example.windowsv8.e_voting.model.ResponseDaftarAdmin;
import com.example.windowsv8.e_voting.model.ResponseDaftarCalon;
import com.example.windowsv8.e_voting.model.ResponseDaftarPemilih;
import com.example.windowsv8.e_voting.model.ResponseLogin;
import com.example.windowsv8.e_voting.model.ResponseLoginAdmin;
import com.example.windowsv8.e_voting.model.ResponsePemungutan;
import com.example.windowsv8.e_voting.model.ResponsePerhitungan;
import com.example.windowsv8.e_voting.model.ResponseTambahCalon;
import com.example.windowsv8.e_voting.model.ResponseUpload;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginRequest(@Field("npm") String npm, @Field("password")String password);

    @FormUrlEncoded
    @POST("cekpemilih.php")
    Call<ResponseCek> cekRequest(@Field("npm") String npm);

    @Multipart
    @POST("uploaduser.php")
    Call<ResponseUpload> uploadPeserta(@Part MultipartBody.Part file,
                                       @Part MultipartBody.Part profile,
                                       @Part("npm")RequestBody npm,
                                       @Part("password")RequestBody password,
                                       @Part("nama")RequestBody nama,
                                       @Part("jkel")RequestBody jkel,
                                       @Part("token")RequestBody token);

    @GET("calon.php")
    Call<ResponseDaftarCalon> getCalon();

    @GET("jumlahpeserta.php")
    Call<Pemilih> getJumlahPemilih();

    @FormUrlEncoded
    @POST("pilihcalon.php")
    Call<ResponsePemungutan> getPemilihan(@Field("npm") String npm, @Field("nomor") String nomor);

    @FormUrlEncoded
    @POST("memilih.php")
    Call<ResponseBody> getUpdateMemilih(@Field("npm") String npm);

    @GET("perhitungansuaraasli.php")
    Call<ResponsePerhitungan> getPerhitunganSuara();

    @GET("daftaradmin.php")
    Call<ResponseDaftarAdmin> getAdmin();

    @FormUrlEncoded
    @POST("loginadmin.php")
    Call<ResponseLoginAdmin> loginRequestAdmin(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("tambahcalon.php")
    Call<ResponseTambahCalon> uploadTambahCalon(@Part MultipartBody.Part file,
                                                @Part("id")RequestBody id,
                                                @Part("nama")RequestBody nama,
                                                @Part("deskripsi")RequestBody deskripsi,
                                                @Part("tanggal")RequestBody tanggal);

    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<ResponseTambahCalon> updateToken(@Field("npm") String npm, @Field("token") String token);

    @GET("daftarpeserta.php")
    Call<ResponseDaftarPemilih> getPemilih();

    @FormUrlEncoded
    @POST("verifikasiadmin.php")
    Call<ResponseTambahCalon> verifikasiAdmin(@Field("npm")String npm, @Field("id_admin") String id_admin);

    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AIzaSyAvwZE1JkhyzHuraA3B3BCWWWJqVjRcrF0"
    })
    @POST("https://fcm.googleapis.com/fcm/send")
    Call<ResponseBody> sendNotification(@Body String body);

    @FormUrlEncoded
    @POST("tambahpemilih.php")
    Call<ResponseUpload> uploadTambahPemilih(@Field("npm")String npm);


}
