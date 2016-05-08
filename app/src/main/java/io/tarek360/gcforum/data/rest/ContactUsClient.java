package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.Message;
import io.tarek360.gcforum.domain.model.SimpleResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by tarek on 4/9/16.
 */

public interface ContactUsClient {

  @POST("contact") Observable<SimpleResponse> sendMessageRx(@Body Message message);
}

