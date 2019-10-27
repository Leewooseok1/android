package com.example.lws.work;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FavoriteActivity extends AppCompatActivity {

    Button btn01, btn03, btn05,           btn07, btn12, btn08, btn13;
    Button btn11, btn14, btn24, btn15,    btn26, btn16, btn27, btn17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);        // 타이틀 바 제거

        setContentView(R.layout.activity_favorite);


        //btn01 = 메인화면
        btn01 = (Button) findViewById(R.id.button1);
        btn01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(FavoriteActivity.this, MainActivity.class);
                startActivity(intentMain);
            }
        });


        //btnPlus = 선택창
        btn05 = (Button) findViewById(R.id.btnPlus);
        btn05.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentSelect = new Intent(FavoriteActivity.this, SelectActivity.class);
                startActivity(intentSelect);
            }
        });

        //btnSetting = 설정창
        btn03 = (Button) findViewById(R.id.btnSetting);
        btn03.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentSet = new Intent(FavoriteActivity.this, HelpActivity.class);
                startActivity(intentSet);
            }
        });



        //btn07 = 첫번째 상의 - 테두리
        btn07 = (Button) findViewById(R.id.button7);
        btn07.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentTop1 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentTop1);
            }
        });

        //btn12 = 첫번째 상의
        btn12 = (Button) findViewById(R.id.button12);
        btn12.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentTop1 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentTop1);
            }
        });



        //btn08 = 첫번째 하의 - 테두리
        btn08 = (Button) findViewById(R.id.button8);
        btn08.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentBottom1 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentBottom1);
            }
        });

        //btn13 = 첫번째 하의
        btn13 = (Button) findViewById(R.id.button13);
        btn13.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentBottom1 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentBottom1);
            }
        });



        //btn11 = 두번째 상의 - 테두리
        btn11 = (Button) findViewById(R.id.button11);
        btn11.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentTop2 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentTop2);
            }
        });

        //btn14 = 두번째 상의
        btn14 = (Button) findViewById(R.id.button14);
        btn14.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentTop2 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentTop2);
            }
        });



        //btn24 = 두번째 하의 - 테두리
        btn24 = (Button) findViewById(R.id.button24);
        btn24.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentBottom2 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentBottom2);
            }
        });

        //btn15 = 두번째 하의
        btn15 = (Button) findViewById(R.id.button15);
        btn15.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentBottom2 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentBottom2);
            }
        });



        //btn26 = 세번째 상의 - 테두리
        btn26 = (Button) findViewById(R.id.button26);
        btn26.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentTop3 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentTop3);
            }
        });

        //btn16 = 세번째 상의
        btn16 = (Button) findViewById(R.id.button16);
        btn16.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentTop3 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentTop3);
            }
        });



        //btn27 = 세번째 하의 - 테두리
        btn27 = (Button) findViewById(R.id.button27);
        btn27.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentBottom3 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentBottom3);
            }
        });

        //btn17 = 세번째 하의
        btn17 = (Button) findViewById(R.id.button17);
        btn17.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intentBottom3 = new Intent(FavoriteActivity.this, GalleryActivity.class);
                startActivity(intentBottom3);
            }
        });

    }
}
