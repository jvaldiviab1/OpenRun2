package com.jvaldiviab.openrun2.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jvaldiviab.openrun2.data.model.LoginDao;
import com.jvaldiviab.openrun2.data.model.LoginPojo;

@Database(entities = LoginPojo.class, version = 1, exportSchema = false)
public abstract class LoggedInDatabase extends RoomDatabase{
    private static LoggedInDatabase instance;

    public abstract LoginDao loginDao();

    public static synchronized LoggedInDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), LoggedInDatabase.class,"logged_in")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
