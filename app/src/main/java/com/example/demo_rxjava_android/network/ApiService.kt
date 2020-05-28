package com.example.demo_rxjava_android.network

import com.example.demo_rxjava_android.helper.model.ApiUser
import com.example.demo_rxjava_android.helper.model.User
import com.example.demo_rxjava_android.helper.model.UserDetail


import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {

    /***************************************/
    // getAnUser/{userId}
    @GET("getAnUser/{userId}")
    fun getAnUserAPI(@Path("userId") userId: String) : Observable<ApiUser>

    // getAllCricketFans
    @GET("getAllCricketFans")
    fun getAllCricketFansAPI() : Observable<List<User>>

    // getAllFootballFans
    @GET("getAllFootballFans")
    fun getAllFootballFansAPI() : Observable<List<User>>

    // getAllFriends/{userId}
    @GET("getAllFriends/{userId}")
    fun getAllFriendsAPI(@Path("userId") userId: String) : Observable<List<User>>

    // getAllUsers/{pageNumber}
    @GET("getAllUsers/{pageNumber}")
    fun getAllUsersAPI(@Path("pageNumber") pageNumber: String ,@Query("limit") limit : String) : Observable<List<User>>

    // getAnUserDetail/{userId}
    @GET("getAnUserDetail/{userId}")
    fun getAnUserDetailAPI(@Path("userId") userId: String) : Observable<UserDetail>
}