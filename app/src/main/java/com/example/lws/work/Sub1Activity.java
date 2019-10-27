package com.example.lws.work;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lws.work.adapter.RecommendColorAdapter;
import com.example.lws.work.model.WardrobeMO;

public class Sub1Activity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView txtType;
    private CardView viewColor;
    private WardrobeMO item;

    private RecommendColorAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        initData();         // 데이터를 grid에서의 이미지 uri 초기화
        initResources();    // 리소스 초기화
    }

    //get 데이터 가져오기, set 데이터 입력

    private void initData() {
        item = (WardrobeMO) getIntent().getExtras().getSerializable("model"); //grid에서 setitem(이미지 uri) 넘겨줌 ll 직렬화로 얻어와
    }

    private void initResources() {
        imageView = findViewById(R.id.imageView);       //  이미지뷰 아이디 얻어옴
        imageView.setImageResource((int) item.getImageUrl()); // 이미지 uri 가져와서 이미지지리소스 입력

        findViewById(R.id.btnBack).setOnClickListener(this); // 백버튼 클릭리스너 연결
        findViewById(R.id.btnSetting).setOnClickListener(this); // help 클릭리스너 연결

        txtType = findViewById(R.id.txtType);       // 상,하의 텍스트 타입
        viewColor = findViewById(R.id.viewColor);   // 상,하의의 색상

        txtType.setText(item.getType());            // (메인프래그먼트)아이템에서 타입얻어와서 텍스트타입에 뿌려준다
        viewColor.setCardBackgroundColor(item.getColor());  // (메인 프래그먼트)아이템에서 컬러 얻어와서 뷰컬러에 뿌려준다.

        recyclerView = findViewById(R.id.recyclerView);     // 리사이클러뷰 아이디 엎어옴
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));    // 수평의 리니어 레이아웃(일렬배치)으로
        adapter = new RecommendColorAdapter(activity);  //본 액티비티에 추천컬러어댑터 연결(생성)
        adapter.setItem(item.getRecommendColors());     // (메인프래그먼트)아이템에서 추천색상 얻어와서 어뎁터에 넣고
        recyclerView.setAdapter(adapter);           // 어뎁터에있는 추천색상을 리사이클러뷰에 수평으로 뿌려준다.
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:      // 백버튼
                finish();
                break;

            case R.id.btnSetting:   // 헬프 액티비티로 이동
                startActivity(new Intent(activity, HelpActivity.class));
                break;
        }
    }
}
