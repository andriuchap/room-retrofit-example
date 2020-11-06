package edu.ktu.roomdatabase

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService
{
    @GET("get_users")
    fun getUsers(): Observable<List<User>>

    @GET("get_user")
    fun getUser(@Query("name") name: String?): Observable<User>

    companion object
    {
        fun create(): UserApiService
        {
            val retrofitBuilder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://vrlab.lt/test_api/")
                .build()
            return retrofitBuilder.create(UserApiService::class.java)
        }

        val service: UserApiService by lazy {
            create()
        }
    }
}