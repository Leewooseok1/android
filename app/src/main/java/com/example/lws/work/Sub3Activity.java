package com.example.lws.work;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Sub3Activity extends AppCompatActivity {
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    Button btn01, btn02, btn05, btn03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sub3);

        //btn01 = 메인화면
        btn01 = (Button) findViewById(R.id.button1);
        btn01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(Sub3Activity.this, MainActivity.class);
                startActivity(intentMain);
            }
        });

        //btn02 = 즐겨찾기
        btn02 = (Button) findViewById(R.id.button2);
        btn02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentFavorite = new Intent(Sub3Activity.this, FavoriteActivity.class);
                startActivity(intentFavorite);
            }
        });

        //btn5 = 상.하의 선택창
        btn05 = (Button) findViewById(R.id.btnPlus);
        btn05.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentSelect = new Intent(Sub3Activity.this, SelectActivity.class);
                startActivity(intentSelect);
            }
        });

        //btn03 = 설정창
        btn03 = (Button) findViewById(R.id.btnSetting);
        btn03.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentSet = new Intent(Sub3Activity.this, HelpActivity.class);
                startActivity(intentSet);
            }
        });

    }
}
