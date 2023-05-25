package edu.hitsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.hitsz.R;

public class ModeActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle saveInsanceState){
        super.onCreate(saveInsanceState);
        setContentView(R.layout.activity_mode);
        Button single = findViewById(R.id.single_mode);
        Button muti = findViewById(R.id.muti_mode);

        single.setOnClickListener(this);
        muti.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v){
        final View lv =v;
        Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = getIntent().getExtras();
        switch (lv.getId()){
            case R.id.single_mode:
                bundle.putString("isMuti","N");
                break;
            case R.id.muti_mode:
                bundle.putString("isMuti","Y");
                break;
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
