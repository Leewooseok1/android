
package com.example.lws.work;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CameraActivity extends Activity {
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    /* 폐기된 액티비티*/
    Button btn01;

    Button btn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //btn01
        btn01 = (Button) findViewById(R.id.button9);
        btn01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent01 = new Intent(CameraActivity.this, ResultActivity.class);
                startActivity(intent01);
            }
        });

    setup();
    }

    private void setup() {
        btn = (Button)findViewById(R.id.button25);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });

    }
}

