package com.noname.genuisplaza.presentation.screens.usersfragment

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.noname.genuisplaza.datalayer.apidata.ServerGateway
import com.noname.genuisplaza.datalayer.localdata.deo.UserDao
import com.noname.genuisplaza.model.entities.AddUser
import com.noname.genuisplaza.model.entities.User
import com.noname.genuisplaza.model.entities.Users
import com.noname.genuisplaza.model.usecases.addnewuser
import com.noname.genuisplaza.model.usecases.isConnected
import com.noname.genuisplaza.model.usecases.retrievUsersData
import com.noname.genuisplaza.model.usecases.retrieveLocal

class UsersViewModel(apiService: ServerGateway, val userDeo1: UserDao, val con: Context) : ViewModel() {
    val usersdata: MutableLiveData<Users> = MutableLiveData()
    val userslocaldata: MutableLiveData<ArrayList<User>> = MutableLiveData()
    val errormesege: MutableLiveData<String> = MutableLiveData()
    val addusersdata: MutableLiveData<AddUser> = MutableLiveData()
    val errorLivedat: MutableLiveData<Throwable> = MutableLiveData()
    val loadingLivedat: MutableLiveData<Boolean> = MutableLiveData()
    val server:ServerGateway =apiService
    val UserDao:UserDao =userDeo1
    init {
        if (con.isConnected)
        getUsers()
        else
            getLocalData()

    }

    fun getUsers()
    {
        loadingLivedat.postValue(true)
        retrievUsersData(server,userDeo1,usersdata,errorLivedat,loadingLivedat)
    }


    fun adduser(name:String,job:String)
    {
        if (validate(name,job))
        {loadingLivedat.postValue(true)
        addnewuser(server,name,job,addusersdata,errorLivedat,loadingLivedat)}
        else
            errormesege.postValue("plese complete your data")
    }


    fun validate(name: String,job: String):Boolean
    {
        return name.length>0&&job.length>0
    }



    fun getLocalData()
    {
        loadingLivedat.postValue(true)
        retrieveLocal(UserDao,userslocaldata,errorLivedat,loadingLivedat)
    }

}
