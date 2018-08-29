package com.evgeniy.royality.Net

import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("/api/example/get-user")
    fun getUsers(): Observable<List<User>>
}