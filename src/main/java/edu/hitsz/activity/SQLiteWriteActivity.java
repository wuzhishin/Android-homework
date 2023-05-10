package edu.hitsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import edu.hitsz.R;
import edu.hitsz.game.BaseGame;
import edu.hitsz.record.MyDBHelper;
import edu.hitsz.record.Ranking;

public class SQLiteWriteActivity extends AppCompatActivity implements OnClickListener {
    private MyDBHelper mHelper;
    private static final String TAG = "SQLiteWriteActivity";

    private EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        et_name = (EditText) findViewById(R.id.et_name);
        ((Button)findViewById(R.id.btn_save)).setOnClickListener(this);
        ((TextView)findViewById(R.id.tv_scoreReport)).setText(String.format("得分为%d",BaseGame.getScore()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = MyDBHelper.getInstance(this, 2);
        mHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = et_name.getText().toString();

            Ranking info = new Ranking();

            info.setUsername(name);
            info.setScore(BaseGame.getScore());
            info.setRecordTime(new Date().toString());
            long result = mHelper.insert(info);
            showToast("数据已写入SQLite数据库" + Long.toString(result));
            showToast(getFilesDir() + "");
            Intent intent = new Intent(this, RankActivity.class);
            startActivity(intent);
        }
    }

    private void showToast(String desc) {
//		Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_SHORT).show();
    }

}
