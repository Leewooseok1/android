package com.example.lws.work.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lws.work.BaseActivity;
import com.example.lws.work.R;
import com.example.lws.work.adapter.GridAdapter;
import com.example.lws.work.common.JacksonFactory;
import com.example.lws.work.common.JsonUtil;
import com.example.lws.work.common.SharedPref;
import com.example.lws.work.model.WardrobeMO;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    // 프래그먼트 = 하나의 Activity 에서 여러 개의 프래그먼트로 화면을 구성한 뒤 동적으로 부분 교체가 가능하며,
    // 여러 Activity 에서 재사용이 가능하다

    private List<WardrobeMO> list = new ArrayList<>(); // arraylist 선언

    private RecyclerView recyclerView;
    private GridAdapter adapter;

    public FavoriteFragment() {
        // 클래스 생성
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false); // 프래그먼트메인 = 리사이클러뷰 있다.

        initData(view); // init = 초기화
        initResources(view);

        return view;
    }

    private void initData(View view) {
        setItems();
    } // 초기 setitem 데이터 초기화

    // 초기 즐겨찾기 넣어져있는 이미지uri 없음(null)
    private void setItems() {
        if (TextUtils.isEmpty(SharedPref.getFavorites(getActivity()))) {
            // setitem 6개는 즐겨찾기 6개아이콘 이라는 소리
            setItem(1, null);
            setItem(2, null);
            setItem(3, null);
            setItem(4, null);
            setItem(5, null);
            setItem(6, null);

            return;
        }
        Type type = new TypeToken<List<WardrobeMO>>(){}.getType();
        Gson gson = new Gson();
        list = gson.fromJson(SharedPref.getFavorites(getActivity()), type);
    }

    // 추가된 정보들을 list에 담는다 이름, 이미지uri
    private void setItem(int idx, Object imageUrl) {
        WardrobeMO wardrobeMO = new WardrobeMO();
        wardrobeMO.setIdx(idx);
        wardrobeMO.setImageUrl(imageUrl);
        list.add(wardrobeMO);
    }

    private void initResources(View view) {
        recyclerView = view.findViewById(R.id.recyclerView); // 리사이클러뷰 찾아서
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // 그리드 레이아웃 spancount = 그리드 아이템 2개까지 설정 한다.
        adapter = new GridAdapter((BaseActivity) getActivity()); // 베이스액티비티에서 얻어온 그리드어뎁터 생성
        adapter.setItem(list, 2); // 리스트 타입2 = favorite xml = +아이콘
        recyclerView.setAdapter(adapter); // +아이콘을 리사이클러뷰에 세팅
    }

}
