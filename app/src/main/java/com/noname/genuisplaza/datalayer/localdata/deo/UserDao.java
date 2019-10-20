package com.noname.genuisplaza.datalayer.localdata.deo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.noname.genuisplaza.model.entities.User;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert
    public void insertUser(User user);


    @Query("select * from User")
    public Single<List<User>> selectAll();

    @Query("delete  from User")
    public void removeAll();


}
