package com.noname.genuisplaza.presentation.screens.usersfragment;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.noname.genuisplaza.datalayer.apidata.ApiClient;
import com.noname.genuisplaza.datalayer.apidata.ServerGateway;
import com.noname.genuisplaza.datalayer.localdata.LocalDatabase;
import com.noname.genuisplaza.datalayer.localdata.deo.UserDao;

public class ViewModelFactory implements ViewModelProvider.Factory {


    private Application application;

    public ViewModelFactory(Application application1) {
        application = application1;
    }

    @SuppressWarnings("SingleStatementInBlock")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass == UsersViewModel.class)
        {
            return (T) new UsersViewModel(getApiService(),getRepoDao(),application.getBaseContext());
        }

        throw new IllegalArgumentException("Invalid view model class type");
    }

    private UserDao getRepoDao() {
        return LocalDatabase.getInstance(application).taskDeo();
    }

    private ServerGateway getApiService() {
        return ApiClient.getClient().create(ServerGateway.class);
    }

}
