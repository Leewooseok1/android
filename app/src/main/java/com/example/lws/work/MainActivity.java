package com.example.lws.work;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lws.work.adapter.MainViewPagerAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    //BaseActivity 상속

    private MainViewPagerAdapter adapter; // 프래그먼트끼리 뷰페이저 사용하기위해 선언
    private ViewPager viewPager; // 뷰 페이지 - 해당 페이지로 이동한다. 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);        // 상태바 제거

        // 현재 플렛폼 api 버젼이 >= 안드로이드 6.0 마쉬멜로우보다 높으면
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();      // 아래에서 생성한 함수 checkpermission을 실행한다. = 활성화 권한 여부
        }
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnSetting).setOnClickListener(this);
        findViewById(R.id.btnWardrobe).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnFavorites).setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);   // 뷰 페이저 아이디 찾기
        adapter = new MainViewPagerAdapter(getSupportFragmentManager()); // 만들어놓은 프레그먼트뷰페이저액티비티 본 액티비티에 서포팅해준다
        viewPager.setAdapter(adapter); // 연결 = sub1~sub6의 xml이 메인액티비티에 연동되서 프래그먼트 + 뷰페이저 됨

    }

    @TargetApi(Build.VERSION_CODES.M)       //안드로이드 6.0 지정
    private void checkPermission() {
        TedPermission.with(this)
                .setPermissionListener(permissionlistener) // 리스너 설정
                .setDeniedMessage("[설정] > [권한]에서 해당 권한을 활성화 해야 앱을 이용하실 수 있습니다\n") // 메세지
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) // 외부 저장 장치
                .check();
    }

    // 허가 리스너 생성
    PermissionListener permissionlistener = new PermissionListener()  {
        @Override
        public void onPermissionGranted() {
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetting:       // btnsetting = help 액티비티로
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;

            case R.id.btnWardrobe:      // 메인화면 누르면
                viewPager.setCurrentItem(0);    // 첫번째 페이지로 초기화  = 메인화면
                Toast.makeText(getApplicationContext(), "당신이 원하는 색상의 옷이 여기에 있나요? 없으면 색상을 조합해보세요", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnPlus:      // 선택창으로 이동
                startActivity(new Intent(MainActivity.this, SelectActivity.class));
                break;

            case R.id.btnFavorites:         // 두번째 페이지로 초기화 = 즐겨찾기
                                              // 메인뷰페이저어뎁터에서 카운터(개수)2개줬음 = 메인화면(0번)과 즐겨찾기(1번)화면 왔다갔다 가능하게 뷰페이저 기능 하라는것
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
