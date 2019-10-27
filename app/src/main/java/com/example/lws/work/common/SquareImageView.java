package com.example.lws.work.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


public class SquareImageView extends AppCompatImageView {
    // 사각형 이미지뷰 상속 앱 적합 이미지뷰

    public SquareImageView(Context context) {
        super(context);
    }
    // 자바 코드안에서만 생성시 사용

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    // AttributeSet = Custom View만들때 레이아웃파일로부터 뷰를 초기화하기 위해
    // attrs = 어트리뷰트 객체화 시키는데 사용

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 뷰에대한 dafault값을 제공하는 style 리소스의 idenitifiler이다.
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // measure = 크기(길이, 높이)
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
