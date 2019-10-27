package com.example.lws.work;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;


public class SelectPOPActivity extends AppCompatActivity {
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    Button buttonGallery, buttonFavorite, buttonOK ;
    ImageView imgView;

    //request code
    final int CAMERA_REQUEST_CODE = 1;


    Button cameraBtn=null ;

    // 카메라 실행버튼
    private void setup() {
        cameraBtn = (Button) findViewById(R.id.btnCamera);
        imgView = (ImageView)findViewById(R.id.imgView);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent113 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent113, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAMERA_REQUEST_CODE){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap)bundle.get("data");
            imgView.setImageBitmap(bitmap);
        }
    }

    //카메라 유무
    public boolean IsCameraAvailable(){
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥 레이어 클릭시 안 닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 상태바 제거 ( 전체화면 모드 )
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_select_pop);

        setup();

        //갤러리 이동
        buttonGallery = (Button) findViewById(R.id.btnGallery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent115 = new Intent(SelectPOPActivity.this, GalleryActivity.class);
                startActivity(intent115);
            }
        });

        //즐겨찾기 이동
        buttonFavorite = (Button) findViewById(R.id.btnFavorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1119 = new Intent(SelectPOPActivity.this, FavoriteActivity.class);
                startActivity(intent1119);
            }
        });

        // 확인버튼
        buttonOK = (Button)findViewById(R.id.btnConfirm);
        buttonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent190 = new Intent(SelectPOPActivity.this, SelectActivity.class);
                startActivity(intent190);
            }
        });
    }
}






