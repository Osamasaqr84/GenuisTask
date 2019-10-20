package com.noname.genuisplaza.datalayer.localdata;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import com.noname.genuisplaza.datalayer.localdata.deo.UserDao;
import com.noname.genuisplaza.model.entities.User;

@Database(entities = {User.class}
        ,version = 3,exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDao taskDeo ();

    public static LocalDatabase instance;

    public static LocalDatabase getInstance (Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),LocalDatabase.class,
                    "task_database").addCallback(roomcallback)
            .fallbackToDestructiveMigration().build();
        }

        return instance;
    }

    public static RoomDatabase.Callback roomcallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
