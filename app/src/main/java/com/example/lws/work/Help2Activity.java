package com.example.lws.work;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Help2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();           // 백버튼 누르면 보고있는 액티비티화면 종료 후 help Activity로 이동
            }
        });
    }
}
