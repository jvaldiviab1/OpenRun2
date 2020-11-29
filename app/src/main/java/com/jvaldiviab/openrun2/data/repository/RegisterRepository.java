package com.jvaldiviab.openrun2.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jvaldiviab.openrun2.data.database.LoggedInDatabase;
import com.jvaldiviab.openrun2.data.model.LoginDao;
import com.jvaldiviab.openrun2.data.model.LoginPojo;

import java.util.List;

public class RegisterRepository {

    private LoginDao mLoginDao;
    private LiveData<List<LoginPojo>> allLoginUsers;

    public RegisterRepository(Application application){
        LoggedInDatabase database = LoggedInDatabase.getInstance(application);
        mLoginDao = database.loginDao();
        allLoginUsers = mLoginDao.getAllLoggedInUsers();
    }

    public void insert(LoginPojo loginPojo){
        new RegisterRepository.InsertNoteAsyncTask(mLoginDao).execute(loginPojo);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<LoginPojo, Void, Void> {
        private LoginDao loginDao;

        private InsertNoteAsyncTask(LoginDao loginDao) {
            this.loginDao = loginDao;
        }

        @Override
        protected Void doInBackground(LoginPojo... notes) {
            loginDao.insert(notes[0]);
            return null;
        }
    }

}
