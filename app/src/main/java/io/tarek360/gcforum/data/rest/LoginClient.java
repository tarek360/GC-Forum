package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.LoginResponse;
import io.tarek360.gcforum.domain.model.SimpleResponse;
import io.tarek360.gcforum.domain.model.User;
import io.tarek360.gcforum.domain.model.UserForgetPassword;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tarek on 2/17/16.
 */

public interface LoginClient {

  @POST("user/login") Call<LoginResponse> login(@Body User user);

  @POST("user/create") Call<LoginResponse> register(@Body User user);

  @POST("user/login") Observable<LoginResponse> loginRx(@Body User user);

  @POST("user/create") Observable<LoginResponse> registerRx(@Body User user);

  @GET("user/profile") Observable<User> getUserProfileRx(@Query("token") String token);

  @POST("user/edit") Observable<SimpleResponse> editUserProfileRx(@Query("token") String token,
      @Body User user);

  @POST("user/edit") Observable<SimpleResponse> editUserProfilePhotoRx(@Query("token") String token,
      @Body RequestBody body);

  @POST("user/login/facebook") Observable<LoginResponse> loginWithFaceBookAccessTokenRx(
      @Body() User user);

  @POST("user/send-reset") Observable<SimpleResponse> sendResetCodeRx(
      @Body UserForgetPassword user);

  @POST("user/reset-password") Observable<SimpleResponse> resetPasswordRx(
      @Body UserForgetPassword user);
}

