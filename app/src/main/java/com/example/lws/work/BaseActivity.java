package com.example.lws.work;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity { // 앱 적합성 = 과거버전과 호환하면서 새버전 기능 사용가능

    public BaseActivity activity;
    private List<Listener> listenerList = new LinkedList<>();       //링크 리스트 생성 ㅣㅣ 객체 생성시, 서로 인접 데이터를 가리키는 방식
    // 매개가 list<listener>
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 제거
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            listenerList.get(i).onResume();     // 화면 갱신 처리

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            listenerList.get(i).onPause();      //일시정지,해체
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            listenerList.get(i).onStart();
        }
    }

    @Override
    protected void onStop() {
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            listenerList.get(i).onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            listenerList.get(i).onDestroy();
        }
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("common BaseActivity", "onActivityResult");   //태그, 메세지 ㅣ log.v = 개발시 사용
        super.onActivityResult(requestCode, resultCode, data);

        for (int i = this.listenerList.size() - 1; i >= 0; --i) {
            ((BaseActivity.Listener) this.listenerList.get(i)).onActivityResult(requestCode, resultCode, data);
        }

    }

    public interface Listener {
        void onCreate();

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onConfigurationChanged(Configuration newConfig);

        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }

    // GridAdapter에서 @Override
    public static class AbstractListener implements Listener {
        @Override
        public void onCreate() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        }
    }


    /**
     * activity life cycle, activity result 등과 같은 이벤트 옵져버를 등록
     * 센서 등록 / 센서 해제
     */
    public void registerListener(Listener listener) {
        listenerList.add(listener);
    }

    public void unregisterListener(Listener listener) {
        listenerList.remove(listener);
    }
}
