package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.SponsorsSection;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tarek on 4/5/16.
 */

public interface SponsorsClient {

  @GET("sponsors") Call<List<SponsorsSection>> getSponsors();
}

