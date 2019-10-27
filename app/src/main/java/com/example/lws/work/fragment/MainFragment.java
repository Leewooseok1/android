package com.example.lws.work.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lws.work.BaseActivity;
import com.example.lws.work.R;
import com.example.lws.work.adapter.GridAdapter;
import com.example.lws.work.model.WardrobeMO;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    // 프래그먼트 = 하나의 Activity 에서 여러 개의 프래그먼트로 화면을 구성한 뒤 동적으로 부분 교체가 가능하며,
    // 여러 Activity 에서 재사용이 가능하다

    private List<WardrobeMO> list = new ArrayList<>(); // ArrayList 선언

    private RecyclerView recyclerView;
    private GridAdapter adapter;

    public MainFragment() {
        // 클래스 생성
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false); // 프래그먼트메인 = 리사이클러뷰 있다.

        initData(view); // 초기화
        initResources(view); // 초기화

        return view;
    }

    private void initData(View view) {
        setItems();
    }

    // sub1~ sub6 layout에 들어갈 각각의 내용
    // parseColor, recommendColors는 각각선언했음
    private void setItems() {
        setItem(1, R.drawable.c1, "TOP", Color.parseColor("#d2d2d2"), recommendColors("#d2d2d2", "#FFFAFA", "#000000", "#A52A2A", "#00FF00", "#FF0000"));
        setItem(2, R.drawable.d1, "BOTTOM", Color.parseColor("#0000ff"), recommendColors("#000000", "#FFFAFA", "#d2d2d2", "#FFFF00", "#00FF00"));
        setItem(3, R.drawable.c2, "TOP", Color.parseColor("#ffea00"), recommendColors("#000000", "#FFFAFA", "#0000FF"));

        setItem(4, R.drawable.d2, "BOTTOM", Color.parseColor("#000000"), recommendColors("#d2d2d2", "#000000", "#FF0000", "#00FF00",
                                                                                                                "#FFFAFA", "#FFA500", "#FFFF00", "#0000FF"));

        setItem(5, R.drawable.c3, "TOP", Color.parseColor("#0000ff"), recommendColors("#000000", "#FFFAFA", "#d2d2d2", "#FFFF00", "#00FF00"));
        setItem(6, R.drawable.d3, "BOTTOM", Color.parseColor("#000000"), recommendColors("#d2d2d2", "#000000", "#FF0000", "#00FF00",
                                                                                                                "#FFFAFA", "#FFA500", "#FFFF00", "#0000FF"));
    }


    private List<Integer> recommendColors(String... colors) {
        // recommend의 String은 colors

        List<Integer> colorList = new ArrayList<>();
        // colorList 이름의 arraylist 선언

        //(변수 : 배열 = 배열에 있는 값들을 하나씩 변수에 대입 == 추천색상을 상.하의 컬러에 대입
        for (String color : colors) {
            colorList.add(Color.parseColor(color)); //컬러리스트에 parseColor 데이터 추가(선언)
        }

        return colorList;
    }

    // 추가된 정보를 list에 담는다. 이름, 이미지uri, 타입, 컬러, 추천색상
    private void setItem(int idx, Object imageUrl, String type, int color, List<Integer> recommendColors) {
        WardrobeMO wardrobeMO = new WardrobeMO();
        wardrobeMO.setIdx(idx);
        wardrobeMO.setImageUrl(imageUrl);
        wardrobeMO.setType(type);
        wardrobeMO.setColor(color);
        wardrobeMO.setRecommendColors(recommendColors);
        list.add(wardrobeMO);
    }

    private void initResources(View view) {
        recyclerView = view.findViewById(R.id.recyclerView); // 리사이클러뷰 id 찾아서
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));  // 그리드 레이아웃 spancount = 그리드 아이템 2개까지 설정한다.
        adapter = new GridAdapter((BaseActivity) getActivity()); // 베이스액티비티에서 얻어온 그리드어뎁터 생성
        adapter.setItem(list, 1); //리스트 타입2 = favorite xml = +아이콘
        recyclerView.setAdapter(adapter); // +아이콘을 리사이클러뷰에 세팅
    }

}
