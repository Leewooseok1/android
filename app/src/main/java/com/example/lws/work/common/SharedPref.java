package com.example.lws.work.common;

import android.content.Context;


public class SharedPref {
    // 어플리케이션에 파일형태로 간단한값(데이터)를 저장한다. = db사용하기엔 복잡하니까 사용한다. = 어플 삭제 전까지 보관됨
    // 즐겨찾기 이미지뷰에 이미지 박아넣을때 사용

    public static final String SHARED_PREF_NAME = "com.example.lws.work.sharedPref"; //이름

    public static final String PREF_KEY_FAVORITES = "PREF_KEY_FAVORITES"; // 키값

    public static final String PREF_DEFAULT_FAVORITES = null; // 실패시

    public static String getFavorites(Context context) {
        // 데이터 가져오기
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(PREF_KEY_FAVORITES, PREF_DEFAULT_FAVORITES);
    }

    public static void setFavorites(Context context, String favorites) {
        // 데이터 입력
        // commit 호출시 처리
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit().putString(PREF_KEY_FAVORITES, favorites).commit();
    }
}
