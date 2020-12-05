package com.jvaldiviab.openrun2.data.var;

import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

public class Constants {


    public static final Uri externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    public static final String URL_FOTO_POR_DEFECTO_USUARIOS = "https://firebasestorage.googleapis.com/v0/b/openrun-bba86.appspot.com/o/Network-Profile.png?alt=media&token=e43d2be9-e175-4fa8-853e-9188484581b0";
    public static final String NODO_USERS = "users";

    public static final Uri internalMusicUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
    public static final Handler HANDLER = new Handler();

    public interface ACTION {
        String MAIN_ACTION = "com.marothiatechs.foregroundservice.action.main";
        String INIT_ACTION = "com.marothiatechs.foregroundservice.action.init";
        String PREV_ACTION = "com.marothiatechs.foregroundservice.action.prev";
        String PLAY_ACTION = "com.marothiatechs.foregroundservice.action.play";
        String NEXT_ACTION = "com.marothiatechs.foregroundservice.action.next";
        String STARTFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.startforeground";
        String STOPFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

}
