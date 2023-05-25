package edu.hitsz.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.hitsz.R;

public class ConRankActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conrank);
        setTitle("联机排行榜");

        TextView u1_name = findViewById(R.id.u1_name);
        TextView u1_score = findViewById(R.id.u1_score);
        TextView u2_name = findViewById(R.id.u2_name);
        TextView u2_score = findViewById(R.id.u2_score);

        int score = Integer.parseInt(getIntent().getExtras().getString("score"));
        String name = getIntent().getExtras().getString("name");
        int rivalScore = Integer.parseInt(getIntent().getExtras().getString("rivalScore"));
        String rivalName = getIntent().getExtras().getString("rivalName");

        if(score > rivalScore){
            u1_name.setText(name);
            u1_score.setText(Integer.toString(score));
            u2_name.setText(rivalName);
            u2_score.setText(Integer.toString(rivalScore));
        }else{
            u2_name.setText(name);
            u2_score.setText(Integer.toString(score));
            u1_name.setText(rivalName);
            u1_score.setText(Integer.toString(rivalScore));
        }

        findViewById(R.id.back_diff).setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
        findViewById(R.id.back_mode).setOnClickListener(
                (v)-> {
                    Intent intent = new Intent(this, ModeActivity.class);
                    startActivity(intent);
                }
        );


    }
}
