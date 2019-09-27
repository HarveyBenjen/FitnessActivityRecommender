package com.google.healthyhabitsrecommender;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FitbitClient {
    @Headers({"Authorization: Basic MjJCNk1IOjMxMjAyOGQ1ZjEzZGVlODU1NjUwNDljNmE5M2I2NDcx",
            "Content-Type: application/x-www-form-urlencoded"})
    @POST("https://api.fitbit.com/oauth2/token")
    @FormUrlEncoded
    Call<UserFitbit> getAccessToken (
            @Field("client_id") String clientId,
            @Field("grant_type") String grantType,
            @Field("redirect_uri") String callbackURL,
            @Field("code") String code
    );

    @GET("https://api.fitbit.com/1/user/-/profile.json")
    Call<ResponseBody> getResponse(@Header("Authorization") String authToken);
}