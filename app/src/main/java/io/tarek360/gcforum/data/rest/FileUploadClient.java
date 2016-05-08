package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.SimpleResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by tarek on 3/10/16.
 */
public interface FileUploadClient {

  @Multipart @POST("user/image") Call<SimpleResponse> upload(
      @Part("image\"; filename=\"image.png\" ") RequestBody file, @Query("token") String token);
}
