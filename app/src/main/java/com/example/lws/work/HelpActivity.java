package com.example.lws.work;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initResources(); // 리소스 초기화
    }

    private void initResources() {
        txtVersion = findViewById(R.id.txtVersion);
        txtVersion.setText(String.format("Version %s", getAppVersion())); // 버젼을 텍스트로 뿌린다.

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    public String getAppVersion() {
        String version = null;  // 버젼 없으면
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName; // 버젼 생성하고
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();    // 이름 못찾으면 오류창 띄워줘라
        }
        return version;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack: // 백버튼
                finish();
                break;

            case R.id.button6: // help2버튼
                Intent intent01 = new Intent(HelpActivity.this, Help2Activity.class);
                startActivity(intent01);
                break;
        }
    }
}