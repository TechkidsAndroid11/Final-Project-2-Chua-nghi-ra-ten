package com.example.admins.hotelhunter.distance_matrix;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vanph on 04/02/2018.
 */

public interface DistanceInterface {
    @GET("https://maps.googleapis.com/maps/api/distancematrix/json")
    Call<DistanceResponse> getDistance(@Query("origins") String origins,
                                       @Query("destinations") String destinations,
                                       @Query("key") String key);
}
