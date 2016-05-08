package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.MapData;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by tarek on 2/17/16.
 */

public interface MapDataClient {

  @GET("location") Observable<MapData> loadMapData();
}

