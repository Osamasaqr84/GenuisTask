package com.noname.genuisplaza.datalayer.apidata;


import com.noname.genuisplaza.model.entities.AddUser;
import com.noname.genuisplaza.model.entities.Users;

import io.reactivex.Observable;
import retrofit2.http.*;


public interface ServerGateway {

    @GET("https://reqres.in/api/users")
    Observable<Users> retrieveUsers();


    @FormUrlEncoded
    @POST("https://reqres.in/api/users")
    Observable<AddUser> addUser(
            @Field("name") String name,
            @Field("job") String job
    );

}
