package com.jvaldiviab.openrun2.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jvaldiviab.openrun2.data.database.LoggedInDatabase;
import com.jvaldiviab.openrun2.data.model.LoginDao;
import com.jvaldiviab.openrun2.data.model.LoginPojo;

import java.util.List;

public class LoginRepository {

    private LoginDao mLoginDao;
    private LiveData<List<LoginPojo>> allLoginUsers;

    public LoginRepository(Application application) {
        LoggedInDatabase database = LoggedInDatabase.getInstance(application);
        mLoginDao = database.loginDao();
        allLoginUsers = mLoginDao.getAllLoggedInUsers();
    }

    public void insert(LoginPojo loginPojo) {
        new InsertNoteAsyncTask(mLoginDao).execute(loginPojo);
    }


    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(mLoginDao).execute();
    }

    public LiveData<List<LoginPojo>> getAllNotes() {
        return allLoginUsers;
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
    public void delete(LoginPojo loginPojo) {
        new DeleteNoteAsyncTask(mLoginDao).execute(loginPojo);
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<LoginPojo, Void, Void> {
        private LoginDao loginDao;

        private DeleteNoteAsyncTask(LoginDao loginDao) {
            this.loginDao = loginDao;
        }

        @Override
        protected Void doInBackground(LoginPojo... loginPojos) {
            loginDao.delete(loginPojos[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private LoginDao loginDao;

        private DeleteAllNotesAsyncTask(LoginDao loginDao) {
            this.loginDao = loginDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loginDao.deleteAllNotes();
            return null;
        }
    }
}
