package edu.hitsz.music;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;

import androidx.appcompat.app.AppCompatActivity;

import java.security.Provider;
import java.util.HashMap;

import edu.hitsz.R;

public class SoundPoolService extends Service {
    private SoundPool mysp0;
    private SoundPool mysp1;

    HashMap<Integer, Integer> soundPoolMap;

    private static  final String TAG = "SoundPoolService";

    public SoundPoolService(){}

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "==== MusicService onStartCommand ===");
        String action = intent.getStringExtra("action");
        if ("bullet_hit".equals(action)) {
            //子弹击中
            mysp0.play(soundPoolMap.get(4),1,1,0,0,1.2f);

        } else if ("bomb_explosion".equals(action)) {
            //炸弹
            mysp0.play(soundPoolMap.get(1),1 , 1,0,0,1);

        } else if ("get_supply".equals(action)) {
            //获得道具
            mysp1.play(soundPoolMap.get(2),1,1,0,0,0.8f);
        }
        else if ("game_over".equals(action)) {
            //游戏结束
            mysp1.play(soundPoolMap.get(3),1,1,0,0,1);
        }



        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate(){
        super.onCreate();

        mysp0 = createSoundPool(mysp0);
        mysp1 = createSoundPool(mysp1);

        soundPoolMap = new HashMap<Integer,Integer>();
        soundPoolMap.put(1,mysp0.load(this,R.raw.bomb_explosion,1));
        soundPoolMap.put(2,mysp1.load(this,R.raw.get_supply,1));
        soundPoolMap.put(3,mysp1.load(this,R.raw.game_over,1));
        soundPoolMap.put(4,mysp0.load(this,R.raw.bullet_hit,1));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createSoundPool(SoundPool mysp) {
        if (mysp == null) {
            // Android 5.0 及 之后版本
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = null;
                audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mysp = new SoundPool.Builder()
                        .setMaxStreams(10)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else { //Android 5.0 以前版本
                mysp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);  // 创建SoundPool
            }
        }
        return mysp;
    }
}
