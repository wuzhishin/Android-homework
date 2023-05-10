package edu.hitsz.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.util.HashMap;

import edu.hitsz.R;

public class MusicService extends Service {
    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();
    private static  final String TAG = "MusicService";
    public MusicService() {
    }
    //创建播放器对象
    private MediaPlayer player;
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "==== MusicService onStartCommand ===");
        String action = intent.getStringExtra("action");
        if ("play".equals(action)) {
            //开始播放普通bgm
            playMusic0();
        } else if ("stop".equals(action)) {
            //停止
            stopMusic();
        } else if ("pause".equals(action)) {
            //暂停
            pauseMusic();
        }
        else if ("aTob".equals(action)) {
            //普通bgm切换bossbgm
            stopMusic();
            playMusic1();
        }
        else if ("bToa".equals(action)) {
            //bossbgm切换普通bgm
            stopMusic();
            playMusic0();
        }

        return super.onStartCommand(intent, flags, startId);
    }
    //播放音乐
    public void playMusic0(){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.bgm);
        }
        player.start();
    }

    public void playMusic1(){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.bgm_boss);
        }
        player.start();
    }
    //暂停播放
    public void pauseMusic(){
        if(player != null && player.isPlaying()){
            player.pause();
        }
    }

    /**
     * 停止播放
     */
    public void stopMusic() {
        if (player != null) {
            player.stop();
            player.reset();//重置
            player.release();//释放
            player = null;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "==== MusicService onCreate ===");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
}

