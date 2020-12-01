package com.jvaldiviab.openrun2.data.repository;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;

import com.jvaldiviab.openrun2.data.model.TrackPojo;
import com.jvaldiviab.openrun2.data.var.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerRepository {

    public static final String CONTENT_MEDIA_EXTERNAL_AUDIO_ALBUMART = "content://media/external/audio/albumart";
    private static MusicPlayerRepository instance;

    public static MusicPlayerRepository getInstance() {
        if (instance == null)
            instance = new MusicPlayerRepository();
        return instance;
    }

    private MutableLiveData<MediaPlayer> mediaPlayer = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private MutableLiveData<TrackPojo> playingTrack = new MutableLiveData<>();
    private MutableLiveData<List<TrackPojo>> playingList = new MutableLiveData<>();

    private Context playingContext;


    private MusicPlayerRepository() {
        this.mediaPlayer.setValue(new MediaPlayer());
        this.isPlaying.setValue(mediaPlayer.getValue().isPlaying());
    }

    public void loadMusics(ContentResolver contentResolver) {

        Cursor cursor = contentResolver.query(Constants.externalMusicUri, null, null, null, null);

        List<TrackPojo> tracks = new ArrayList<>();

        // UgHH
        while (cursor.moveToNext()) {

            // Music Detail
            String trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String trackTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String trackLength = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            // Music Cover Uri
            Uri trackCoverUri = null;

            // handle bad cases !
            if (trackLength == null)
                continue;
            if (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)) != null && !cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).equals(""))
                trackCoverUri = ContentUris.withAppendedId(Uri.parse(CONTENT_MEDIA_EXTERNAL_AUDIO_ALBUMART), Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));

            // Track
            TrackPojo thisLoopTrack = new TrackPojo(Long.parseLong(trackId), trackTitle, trackAlbum, trackArtist, trackCoverUri, Integer.parseInt(trackLength), null);

            tracks.add(thisLoopTrack);

        }

        cursor.close();

        TrackRepository.getInstance().setAllTracks(tracks);

    }

//    public static InputStream getInputStreamOfImage(ContentResolver contentResolver, Uri uri) {
//        ContentResolver res = contentResolver;
//        InputStream in = null;
//        try {
//            in = res.openInputStream(uri);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return in;
//    }

    public void playMusic(TrackPojo track, final Context context) throws IOException {

        playingContext = context;

        mediaPlayer.getValue().stop();

        playingTrack.setValue(track);

        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, playingTrack.getValue().getTrackId());

        mediaPlayer.setValue(new MediaPlayer());
        mediaPlayer.getValue().setAudioAttributes(new AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build());
        mediaPlayer.getValue().setDataSource(context.getApplicationContext(), contentUri);
        mediaPlayer.getValue().setOnCompletionListener(mediaPlayer -> {
            try {
                if (track.getIndex() + 1 < playingList.getValue().size())
                    playMusic(playingList.getValue().get(track.getIndex() + 1), context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mediaPlayer.getValue().prepare();
        mediaPlayer.getValue().start();

        //bindService();

        isPlaying.setValue(true);

    }

    public MutableLiveData<TrackPojo> getPlayingTrack() {
        return playingTrack;
    }

    public static String getStringTime(int seconds) {
        int minutes = seconds / 60;
        int secondReminder = seconds % 60;
        return (minutes < 10 ? "0" + minutes : minutes) + ":" + (secondReminder < 10 ? "0" + secondReminder : secondReminder);
    }

    public void resume() {
        mediaPlayer.getValue().start();
        isPlaying.setValue(true);
       // bindService();
    }

    public void pause() {
        mediaPlayer.getValue().pause();
        isPlaying.setValue(false);
       // bindService();
    }

    // Service bind
   /* private void bindService() {
        // Run Service
        Intent intent = new Intent(playingContext, MusicPlayerService.class);
        playingContext.startForegroundService(intent);
    }*/

    public MutableLiveData<Boolean> isPlaying() {
        return isPlaying;
    }

    public MutableLiveData<List<TrackPojo>> getPlayingList() {
        return playingList;
    }

    public MutableLiveData<MediaPlayer> getMediaPlayer() {
        return mediaPlayer;
    }

}
