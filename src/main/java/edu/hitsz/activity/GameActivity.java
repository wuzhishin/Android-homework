package edu.hitsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.hitsz.game.BaseGame;
import edu.hitsz.game.EasyGame;
import edu.hitsz.game.HardGame;
import edu.hitsz.game.MediumGame;
import edu.hitsz.music.MusicService;
import edu.hitsz.music.SoundPoolService;
import edu.hitsz.socketclient.ClientThread;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";
    private StringBuilder sb = null;
    private boolean isMuti = false;
    private int gameType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseGame basGameView;

        Intent musicIntent = new Intent(this, MusicService.class);
        Intent Sound = new Intent(this, SoundPoolService.class);

        if(getIntent().getExtras().getString("isMuti").equals("Y")){
            isMuti = true;
        }

        Handler handler0 = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG,"handleMessage");
                switch (msg.what){
                    case 1:
                        Toast.makeText(GameActivity.this,"GameOver",Toast.LENGTH_SHORT).show();

                        Sound.putExtra("action","game_over");
                        startService(Sound);

                        stopService(musicIntent);
                        stopService(Sound);
                        if(isMuti){
                            Intent intent = new Intent(GameActivity.this, ConRankActivity.class);
                            Bundle bundle = getIntent().getExtras();
                            String[] rivaldata = msg.obj.toString().split(";");
                            bundle.putString("rivalName",rivaldata[0]);
                            bundle.putString("rivalScore",rivaldata[1]);
                            bundle.putString("score",Integer.toString(BaseGame.getScore()));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(GameActivity.this, SQLiteWriteActivity.class);
                            startActivity(intent);
                        }

                        break;
                    case 2://出现boss
                        musicIntent.putExtra("action","aTob");
                        startService(musicIntent);
                        break;
                    case 3://消灭boss
                        musicIntent.putExtra("action","bToa");
                        startService(musicIntent);
                        break;

                }
            }
        };

        Handler handler1 = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "handleMessage");
                switch (msg.what) {
                    case 4://击中敌机
                        Sound.putExtra("action","bullet_hit");
                        startService(Sound);
                        break;
                    case 5://炸弹
                        Sound.putExtra("action","bomb_explosion");
                        startService(Sound);
                        break;
                    case 6://获得道具
                        Sound.putExtra("action","get_supply");
                        startService(Sound);
                        break;
                }
            }
        };


        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
        }

        if(gameType == 1){
            basGameView = new MediumGame(this,handler0, handler1,isMuti,getIntent().getExtras().getString("name"));

        }else if(gameType == 3){
            basGameView = new HardGame(this,handler0, handler1,isMuti,getIntent().getExtras().getString("name"));
        }else{
            basGameView = new EasyGame(this,handler0, handler1,isMuti,getIntent().getExtras().getString("name"));
        }



        setContentView(basGameView);
        musicIntent.putExtra("action","play");
        startService(musicIntent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}