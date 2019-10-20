package com.noname.genuisplaza.datalayer.repositries

import android.annotation.SuppressLint
import com.noname.genuisplaza.datalayer.apidata.ServerGateway
import com.noname.genuisplaza.datalayer.localdata.deo.UserDao
import com.noname.genuisplaza.model.entities.AddUser
import com.noname.genuisplaza.model.entities.User
import com.noname.genuisplaza.model.entities.Users
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun retrieveusers(server: ServerGateway): Observable<Users> {
    return server.retrieveUsers()
}


fun addUser(server: ServerGateway,name:String,job:String): Observable<AddUser> {
    return server.addUser(name,job)
}

@SuppressLint("CheckResult")
fun AddUserinDB(userdeo: UserDao,reposirtry: User) {
    Observable.fromCallable { userdeo.insertUser(reposirtry) }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()

}

@SuppressLint("CheckResult")
fun DeleteAll(repodeo: UserDao) {
    Observable.fromCallable { repodeo.removeAll() }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()

}

fun retrieveLocalData(userdeo :UserDao): Single<List<User>>?
{
    return userdeo.selectAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}
