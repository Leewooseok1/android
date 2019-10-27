package com.example.lws.work;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lws.work.adapter.RecommendColorAdapter;
import com.example.lws.work.common.GlideManager;
import com.example.lws.work.model.WardrobeMO;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends BaseActivity implements View.OnClickListener {

    private WardrobeMO topItem;
    private WardrobeMO bottomItem;

    private ImageView imageView1;
    private ImageView imageView2;
    private TextView txtTypeTop;
    private TextView txtTypeBottom;
    private TextView txtResult;
    private String TopColor;
    private String BotColor;

    private RecommendColorAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initData();     // 데이터 초기화
        initResources();    // 리소스 초기화
    }

    //직렬화 Serializable = 자바 인터페이스 =  액티비티에서 다른 액티비티로 데이터를 넘기는 것 (데이터 여러개), object를 넘길수도 있다.
    // 그리드 어뎁터 액티비티 참조

    private void initData() {
        topItem = (WardrobeMO) getIntent().getExtras().getSerializable("topItem");
        bottomItem = (WardrobeMO) getIntent().getExtras().getSerializable("bottomItem");
    }

    private void initResources() {
        findViewById(R.id.btnBack).setOnClickListener(this); // 백버튼 클릭리스너 연결
        findViewById(R.id.btnSetting).setOnClickListener(this); // help 클릭리스너 연결

        txtTypeTop = findViewById(R.id.txtTypeTop); // 상의 아이디 찾기
        txtTypeBottom = findViewById(R.id.txtTypeBottom); // 하의 아이디 찾기
        txtResult = findViewById(R.id.txtResult); // 결과텍스트 아이디 찾기

        txtTypeTop.setText(topItem.getType());      // 아래에 있는 색상 텍스트에서 상의 텍스트 결정
        txtTypeBottom.setText(bottomItem.getType()); // 아래에 있는 색상 텍스트에서 하의 텍스트 결정

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)); // 리니어 레이아웃 수평으로
        adapter = new RecommendColorAdapter(activity);      // 본 액티비티 어뎁터에서 추천색상 얻어서 액티비티에 담는다.

        // 색상 코드 값

        //TopColor = "빨간색";
        //txtTypeTop.setTextColor(Color.parseColor("#FF0000"));
        //TopColor = "주황색";
        //txtTypeTop.setTextColor(Color.parseColor("#FFA500"));
        //TopColor = "노란색";
        //txtTypeTop.setTextColor(Color.parseColor("#FFFF00"));
        //TopColor = "초록색";
        //txtTypeTop.setTextColor(Color.parseColor("#00FF00"));
        //TopColor = "파란색";
        //txtTypeTop.setTextColor(Color.parseColor("#0000FF"));
        //TopColor = "보라색";
        //txtTypeTop.setTextColor(Color.parseColor("#800080"));
        //TopColor = "분홍색";
        //txtTypeTop.setTextColor(Color.parseColor("#FFC0CB"));
        //TopColor = "흰색";
        //txtTypeTop.setTextColor(Color.parseColor("#FFFAFA"));
        //TopColor = "검은색";
        //txtTypeTop.setTextColor(Color.parseColor("#000000"));
        //TopColor = "회색";
        //txtTypeTop.setTextColor(Color.parseColor("#808080"));
        //TopColor = "갈색";
        //txtTypeTop.setTextColor(Color.parseColor("#A52A2A"));

        //어플에서 컨텍스트 가져오고, 상의 이미지 url불러오고, 리소스를 비트맵으로 변경한다.
        //글라이드 이용해서 상의 url을 비트맵으로 변환
        Glide.with(getApplicationContext()).asBitmap().load(topItem.getImageUrl()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmapTop, @Nullable Transition<? super Bitmap> transition) {
                //imageView1.setImageBitmap(bitmapTop);
                //이 코드는 비트맵을 이미지뷰에 등록..인데 이 코드로 등록하면 크기가 이상하므로 아래코드로 함.
                //그래서 이 코드는 순전히 아래 픽셀계산을 위한 용도임.

                int width=0, height=0;
                width = bitmapTop.getWidth();       // 넓이
                height = bitmapTop.getHeight();     // 높이

                int[] pix = new int[width * height];    // 픽셀 구한다.
                bitmapTop.getPixels(pix, 0, width, 0, 0, width, height);

                int R=0, G=0, B=0;      // 초기화

                for (int y = 0; y < height; y++){
                    for (int x = 0; x < width; x++)
                    {
                        int index = y * width + x;
                        R = (pix[index] >> 16) & 0xff;     //bitwise shifting
                        G = (pix[index] >> 8) & 0xff;
                        B = pix[index] & 0xff;


                        //R,G.B - Red, Green, Blue
                        //to restore the values after RGB modification, use
                        //next statement
                        //pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
                    }}
                //txtTypeBottom.setText("R값:"+ R +" "+ "G값:"+G +" "+ "B값:"+ B);
                //RGB분석이 제대로 되는지 확인하기 위해 상의 RGB값을 바텀 텍스트뷰에 띄우게 했던 확인용코드.

                //Toast.makeText(this, "R값:"+ R +" "+ "G값:"+G +" "+ "B값:"+ B, Toast.LENGTH_LONG).show();
                //여기까지가 RGB분석 코드


                //색상 범위
                //빨간색: 200~255, 0~50, 0~50
                //주황색: 255, 100~160,0~50
                //노랑색: 240~255, 255~220, 0~90
                //초록색: 0~100, 230~255, 0~100
                //파란색: 0~135, 0~206, 128~255
                //보라색: 110~128, 0~75 , 110~128
                //분홍색: 240~255, 100~190, 160~200
                //흰색: 220~255,220~255,220~255
                //검정색: 0~60, 0~60, 0~60
                //회색: 100~219, 100~219, 100~219
                //갈색: 150~199, 57~100, 0~55

                //빨간색판별: 200~255, 0~50, 0~50


                // 상의 색상 r,g,b 범위설정해서 분석 후 텍스트와 컬러 결과창에 표시한다.

                if (R >= 200 && R <= 255) {
                    if (G >= 0 && G <= 50) {
                        if (B >= 0 && B <= 50) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 빨간색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "빨간색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }
                }
                //주황색판별: 255, 100~160,0~50
                if (R == 255) {
                    if (G >= 100 && G <= 160) {
                        if (B >= 0 && B <= 50) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 주황색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "주황색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#FFA500"));
                        }
                    }
                }
                //노란색판별: 240~255, 255~220, 0~90
                if (R >= 240 && R <= 255) {
                    if (G >= 220 && G <= 255) {
                        if (B >= 0 && B <= 90) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 노란색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "노란색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#FFFF00"));
                        }
                    }
                }
                //초록색: 0~100, 230~255, 0~100
                if (R >= 0 && R <= 100) {
                    if (G >= 230 && G <= 255) {
                        if (B >= 0 && B <= 100) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 초록색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "초록색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#00FF00"));
                        }
                    }
                }
                //파란색: 0~135, 0~206, 128~255
                if (R >= 0 && R <= 135) {
                    if (G >= 0 && G <= 206) {
                        if (B >= 128 && B <= 255) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 파란색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "파란색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#0000FF"));
                        }
                    }
                }
                //보라색: 90~150, 0~75 , 900~150
                if (R >= 90 && R <= 150) {
                    if (G >= 0 && G <= 75) {
                        if (B >= 90 && B <= 127) {
                            //  Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 보라색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "보라색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#800080"));
                        }
                    }
                }
                //분홍색: 240~255, 100~190, 160~200
                if (R >= 240 && R <= 255) {
                    if (G >= 50 && G <= 190) {
                        if (B >= 130 && B <= 200) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 분홍색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "분홍색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#FFC0CB"));
                        }
                    }
                }
                //흰색: 220~255,220~255,220~255
                if (R >= 220 && R <= 255) {
                    if (G >= 220 && G <= 255) {
                        if (B >= 220 && B <= 255) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 흰색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "흰색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#808080"));
                        }
                    }
                }
                //검은색: 0~60, 0~60, 0~60
                if (R >= 0 && R <= 60) {
                    if (G >= 0 && G <= 60) {
                        if (B >= 0 && B <= 60) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 검은색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "검은색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                }
                //회색: 100~219, 100~219, 100~219
                if (R >= 100 && R <= 219) {
                    if (G >= 20 && G <= 219) {
                        if (B >= 100 && B <= 219) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 회색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "회색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#808080"));
                        }
                    }
                }
                //갈색: 150~199, 57~100, 0~55
                if (R >= 130 && R <= 199) {
                    if (G >= 40 && G <= 100) {
                        if (B >= 0 && B <= 55) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 갈색입니다.", Toast.LENGTH_LONG).show();
                            TopColor = "갈색";
                            txtTypeTop.setText(TopColor);
                            txtTypeTop.setTextColor(Color.parseColor("#A52A2A"));
                        }
                    }
                }
            }
        });

        //여기부터 하의
        //글라이드를 이용하여 topItem을 Url로 불러와 비트맵으로 변환
        Glide.with(getApplicationContext()).asBitmap().load(bottomItem.getImageUrl()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmapBot, @Nullable Transition<? super Bitmap> transition) {
                //imageView2.setImageBitmap(bitmapBot);
                //이 코드는 비트맵을 이미지뷰에 등록..인데 이 코드로 등록하면 크기가 이상하므로 아래코드로 함.
                //그래서 이 코드는 순전히 아래 픽셀계산을 위한 용도임.


                // 알고리즘 코드는 동일하다.
                int width=0, height=0;
                width = bitmapBot.getWidth();
                height = bitmapBot.getHeight();

                int[] pix = new int[width * height];
                bitmapBot.getPixels(pix, 0, width, 0, 0, width, height);

                int R=0, G=0, B=0;

                for (int y = 0; y < height; y++){
                    for (int x = 0; x < width; x++)
                    {
                        int index = y * width + x;
                        R = (pix[index] >> 16) & 0xff;     //bitwise shifting
                        G = (pix[index] >> 8) & 0xff;
                        B = pix[index] & 0xff;


                        //R,G.B - Red, Green, Blue
                        //to restore the values after RGB modification, use
                        //next statement
                        //pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
                    }}
                //txtTypeBottom.setText("R값:"+ R +" "+ "G값:"+G +" "+ "B값:"+ B);
                //RGB분석이 제대로 되는지 확인하기 위해 상의 RGB값을 바텀 텍스트뷰에 띄우게 했던 확인용코드.

                //Toast.makeText(this, "R값:"+ R +" "+ "G값:"+G +" "+ "B값:"+ B, Toast.LENGTH_LONG).show();
                //여기까지가 RGB분석 코드

                //색상 범위
                //빨간색: 200~255, 0~50, 0~50
                //주황색: 255, 100~160,0~50
                //노랑색: 240~255, 255~220, 0~90
                //초록색: 0~100, 230~255, 0~100
                //파란색: 0~135, 0~206, 128~255
                //보라색: 110~128, 0~75 , 110~128
                //분홍색: 240~255, 100~190, 160~200
                //흰색: 220~255,220~255,220~255
                //검정색: 0~60, 0~60, 0~60
                //회색: 100~219, 100~219, 100~219
                //갈색: 150~199, 57~100, 0~55

                //빨간색판별: 200~255, 0~50, 0~50




                if (R >= 200 && R <= 255) {
                    if (G >= 0 && G <= 50) {
                        if (B >= 0 && B <= 50) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 빨간색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "빨간색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#FF0000"));

                            //switch (TopColor) {
                            //    case "빨간색" : txtResult.setText("B");
                            //        break;
                             //   case "검은색" : txtResult.setText("G");
                             //       break;
                             //   default: txtResult.setText("범위 내에 존재하지 않는 색입니다.");
                              //      break;

                            //} 이유는 모르겠으나, 스위치 케이스문이나 if+else if문을 사용하면 검은색및 기타 색에서 오류가 남
                            // 따라서 깡 if문반복으로 처리함
                            //참고로 스위치 케이스문을 쓰려면 Try/catch문으로 감싸야함.

                            // 하의를 기준으로 한 상의 색상 11가지를 상황에 맞게 결과 텍스트와 추천색상을 뿌려준다.
                                if (TopColor == "검은색") {
                                    txtResult.setText("GOOD");
                                }
                                if (TopColor == "회색") {
                                    txtResult.setText("GOOD");
                                }


                                if (TopColor == "노란색") {
                                    txtResult.setText("SOSO");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }
                                if (TopColor == "보라색") {
                                    txtResult.setText("SOSO");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }
                                if (TopColor == "흰색") {
                                    txtResult.setText("SOSO");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }
                                if (TopColor == "갈색") {
                                    txtResult.setText("SOSO");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }
                                if (TopColor == "초록색") {
                                    txtResult.setText("SOSO");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }
                                if (TopColor == "주황색") {
                                    txtResult.setText("SOSO");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }


                               if (TopColor == "파란색") {
                                   txtResult.setText("BAD");
                                   adapter.setItem(recommendColors("#000000", "#808080"));
                               }
                                if (TopColor == "분홍색") {
                                    txtResult.setText("BAD");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }
                                if (TopColor == "빨간색") {
                                    txtResult.setText("BAD");
                                    adapter.setItem(recommendColors("#000000", "#808080"));
                                }



                        }
                    }
                }
                //주황색판별: 255, 100~160,0~50
                if (R == 255) {
                    if (G >= 100 && G <= 160) {
                        if (B >= 0 && B <= 50) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 주황색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "주황색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#FFA500"));
                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "노란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "회색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }


                            if (TopColor == "초록색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "분홍색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                            }
                            if (TopColor == "빨간색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA"));
                                //수정해야함 메모장 색목록에 빨간색 누락됐음
                            }
                        }
                    }
                }
                //노란색판별: 240~255, 255~220, 0~90
                if (R >= 240 && R <= 255) {
                    if (G >= 220 && G <= 255) {
                        if (B >= 0 && B <= 90) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 노란색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "노란색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#FFFF00"));

                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "빨간색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }


                            if (TopColor == "노란색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }
                            if (TopColor == "분홍색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }
                            if (TopColor == "회색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF"));
                            }

                        }
                    }
                }
                //초록색: 0~100, 230~255, 0~100
                if (R >= 0 && R <= 100) {
                    if (G >= 230 && G <= 255) {
                        if (B >= 0 && B <= 100) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 초록색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "초록색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#00FF00"));

                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "회색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "노란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }
                            if (TopColor == "빨간색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }


                            if (TopColor == "주황색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }
                            if (TopColor == "분홍색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#0000FF", "#808080"));
                            }
                        }
                    }
                }
                //파란색: 0~135, 0~206, 128~255
                if (R >= 0 && R <= 135) {
                    if (G >= 0 && G <= 206) {
                        if (B >= 128 && B <= 255) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 파란색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "파란색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#0000FF"));

                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "분홍색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#808080", "#FFFF00", "#00FF00"));
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#808080", "#FFFF00", "#00FF00"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#808080", "#FFFF00", "#00FF00"));
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#808080", "#FFFF00", "#00FF00"));
                            }
                            if (TopColor == "회색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#808080", "#FFFF00", "#00FF00"));
                            }


                            if (TopColor == "빨간색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#000000", "#FFFAFA", "#808080", "#FFFF00", "#00FF00"));
                            }
                        }
                    }
                }
                //보라색: 110~128, 0~75 , 110~128
                if (R >= 90 && R <= 150) {
                    if (G >= 0 && G <= 75) {
                        if (B >= 90 && B <= 127) {
                            //  Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 보라색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "보라색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#800080"));

                            if (TopColor == "빨간색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "분홍색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "흰색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "회색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }


                            if (TopColor == "갈색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FF0000", "#FFC0CB"));
                            }
                        }
                    }
                }
                //분홍색: 240~255, 100~190, 160~200
                if (R >= 240 && R <= 255) {
                    if (G >= 50 && G <= 190) {
                        if (B >= 130 && B <= 200) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 분홍색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "분홍색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#FFC0CB"));

                            if (TopColor == "분홍색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "파란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }


                            if (TopColor == "회색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                            if (TopColor == "빨간색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#FFC0CB", "#800080", "#FFFAFA"));
                            }
                        }
                    }
                }
                //갈색: 150~199, 57~100, 0~55
                if (R >= 130 && R <= 199) {
                    if (G >= 40 && G <= 100) {
                        if (B >= 0 && B <= 55) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 갈색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "갈색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#A52A2A"));

                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "회색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "초록색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }
                            if (TopColor == "분홍색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }
                            if (TopColor == "빨간색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }


                            if (TopColor == "보라색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#A52A2A", "#FFFAFA", "#808080"));
                            }

                        }
                    }
                }
                //흰색: 220~255,220~255,220~255
                if (R >= 220 && R <= 255) {
                    if (G >= 220 && G <= 255) {
                        if (B >= 220 && B <= 255) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 흰색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "흰색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#808080"));

                            if (TopColor == "주황색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            //흰색 두번적혀있음
                            if (TopColor == "회색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "분홍색") {
                                txtResult.setText("GOOD");
                            }
                            //분홍색 누락
                            if (TopColor == "빨간색") {
                                txtResult.setText("GOOD");
                            }
                            //빨간색도 누락



                            if (TopColor == "보라색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#000000", "#A52A2A", "#FFFAFA",
                                        "#FF0000", "#FFA500", "#FFFF00", "#0000FF"));
                            }

                        }
                    }
                }

                //회색: 100~219, 100~219, 100~219
                if (R >= 100 && R <= 219) {
                    if (G >= 20 && G <= 219) {
                        if (B >= 100 && B <= 219) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 회색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "회색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#808080"));

                            if (TopColor == "회색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "빨간색") {
                                txtResult.setText("GOOD");
                            }

                            if (TopColor == "보라색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#FFFAFA", "#000000", "#A52A2A", "#00FF00", "#FF0000"));
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#FFFAFA", "#000000", "#A52A2A", "#00FF00", "#FF0000"));
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#FFFAFA", "#000000", "#A52A2A", "#00FF00", "#FF0000"));
                            }


                            if (TopColor == "분홍색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#808080", "#FFFAFA", "#000000", "#A52A2A", "#00FF00", "#FF0000"));
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("BAD");
                                adapter.setItem(recommendColors("#808080", "#FFFAFA", "#000000", "#A52A2A", "#00FF00", "#FF0000"));
                            }
                        }
                    }
                }
                //검은색: 0~60, 0~60, 0~60
                if (R >= 0 && R <= 60) {
                    if (G >= 0 && G <= 60) {
                        if (B >= 0 && B <= 60) {
                            // Toast.makeText(this, "R값:" + R + " " + "G값:" + G + " " + "B값:" + B + " 이 옷은 검은색입니다.", Toast.LENGTH_LONG).show();
                            BotColor = "검은색";
                            txtTypeBottom.setText(BotColor);
                            txtTypeBottom.setTextColor(Color.parseColor("#000000"));

                            if (TopColor == "회색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "주황색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "빨간색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "검은색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "노란색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "파란색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "흰색") {
                                txtResult.setText("GOOD");
                            }
                            if (TopColor == "초록색") {
                                txtResult.setText("GOOD");
                            }


                            if (TopColor == "분홍색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#000000", "#FF0000", "#00FF00",
                                        "#FFFAFA", "#FFA500", "#FFFF00", "#0000FF"));
                            }
                            if (TopColor == "보라색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#000000", "#FF0000", "#00FF00",
                                        "#FFFAFA", "#FFA500", "#FFFF00", "#0000FF"));
                            }
                            if (TopColor == "갈색") {
                                txtResult.setText("SOSO");
                                adapter.setItem(recommendColors("#808080", "#000000", "#FF0000", "#00FF00",
                                        "#FFFAFA", "#FFA500", "#FFFF00", "#0000FF"));
                            }
                        }
                    }
                }
            }
        });



        GlideManager.GlideCenterCrop(activity, topItem.getImageUrl(), imageView1); // 글라인드 이용해서 상의 이미지를 이미지뷰에 뿌린다.
        GlideManager.GlideCenterCrop(activity, bottomItem.getImageUrl(), imageView2); // 글라인드 이용해서 하의 이미지를 이미지뷰에 뿌린다.


        //adapter.setItem(recommendColors());
        recyclerView.setAdapter(adapter);       // 라사이클러뷰에 맨위에서 나온 어뎁터 지정한걸로 추천색상을 리니어레이아웃에 뿌린다.
    }

    private List<Integer> recommendColors(String... colors) {
        List<Integer> colorList = new ArrayList<>();        // 어레이리스트 지정

        for (String color : colors) {
            colorList.add(Color.parseColor(color));         // 추천색상들을 상.하의 색상에 하나씩 넣는다.
        }

        return colorList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:      // 백버튼
                finish();
                break;

            case R.id.btnSetting:       // help버튼
                startActivity(new Intent(activity, HelpActivity.class));
                break;
        }
    }
}
