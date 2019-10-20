package com.noname.genuisplaza.model.usecases

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.noname.genuisplaza.datalayer.apidata.ServerGateway
import com.noname.genuisplaza.datalayer.localdata.deo.UserDao
import com.noname.genuisplaza.datalayer.repositries.*
import com.noname.genuisplaza.model.entities.AddUser
import com.noname.genuisplaza.model.entities.User
import com.noname.genuisplaza.model.entities.Users
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
fun retrievUsersData(serverGateway: ServerGateway,userDeo: UserDao, usersliveData: MutableLiveData<Users>,
                     errorLiveData: MutableLiveData<Throwable>,
                     loadingLivedata: MutableLiveData<Boolean> ) {

    retrieveusers(serverGateway).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe({
            storinDB(it.data as ArrayList<User>,userDeo)
            usersliveData.postValue(it);
        loadingLivedata.postValue(false) },
        {
            errorLiveData.postValue(it);
            loadingLivedata.postValue(false)
        })
}

@SuppressLint("CheckResult")
fun addnewuser(serverGateway: ServerGateway,name:String,job:String, usersliveData: MutableLiveData<AddUser>,
                     errorLiveData: MutableLiveData<Throwable>,
                     loadingLivedata: MutableLiveData<Boolean>) {
    addUser(serverGateway,name,job).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe({
            usersliveData.postValue(it);
            loadingLivedata.postValue(false) }, {
                errorLiveData.postValue(it);
                loadingLivedata.postValue(false)
            })
}

@SuppressLint("CheckResult")
fun retrieveLocal(
    userDeo: UserDao, repositriesliveData: MutableLiveData<ArrayList<User>>,
    errorLiveData: MutableLiveData<Throwable>,
    loadingLivedata: MutableLiveData<Boolean>) {

    retrieveLocalData(userDeo)?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ myData ->
            repositriesliveData.postValue(myData as ArrayList<User>);
            loadingLivedata.postValue(false)// handle data fetched successfully and API call completed
        }, { throwable ->
            errorLiveData.postValue(throwable);
            loadingLivedata.postValue(false)
        });
}


fun storinDB( alldata: ArrayList<User>, deo: UserDao) {
   DeleteAll(deo)
    alldata.map { user -> AddUserinDB(deo, user) }
}
