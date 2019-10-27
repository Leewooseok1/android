package com.example.lws.work.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;


public class GlideManager {
    // 이미지 로딩을 빠르게 처리해주는 라이브러리
    // Android에서 리소스에 이미지를 불러 오거나 디바이스 내 파일 혹은 URL을 통해 이미지를 가져와
    // 표시 하는 경우가 많은데 이럴때 사용되는 라이브러리가 바로 'Glide'이다.
    // 블러 처리 = 흐릿하게

    public static void GlideBlur(Context context, String imgUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()   //옵션 요청
                .bitmapTransform(new BlurTransformation(25, 1)) // 비트맵 변환 블러/원형
                .diskCacheStrategy(DiskCacheStrategy.ALL);  // 모든 이미지를 캐싱
    // 캐시 =  매번 이미지가 필요할 때마다 로딩하는 것이 아니라, 메모리에 캐싱해서 로딩하지 않고 바로 가져다 쓰는 것

        Glide.with(context) // api 이용하기 위함
                .load(imgUrl) //이미지 uri 불러온다
                .apply(options) // 적용
                .into(imageView); // 다운받은 이미지를 보여줄 뷰
    }

    public static void GlideBlur(Context context, Object resId, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new BlurTransformation(25, 1))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(resId) // 리소스 식별자를 로드한다.
                .thumbnail(0.2f) // 20% 사이즈로 그림을 줄여서 보여준다.
                .apply(options)
                .into(imageView);
    }

    public static void GlideFitCenter(Context context, String imgUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .fitCenter()    // 실제 이미지가 이미지뷰의 사이즈와 다를때, 둘 사이 중간을 맞춰 이미지크기 스케일링 함
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(imgUrl)
                .thumbnail(0.2f)
                .apply(options)
                .into(imageView);
    }

    public static void GlideFitCenter(Context context, int imgUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(imgUrl)
                .thumbnail(0.2f)
                .apply(options)
                .into(imageView);
    }

    public static void GlideCenterCrop(Context context, Object object, ImageView imageView) {
        GlideCenterCrop(context, object, imageView, null); // 실제이미지가 이미지뷰 사이즈보다 클때 이미지 중간 잘라 스케일링
    }

    public static void GlideCenterCrop(Context context, Object object, ImageView imageView, final Runnable runnable) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(object)
                .thumbnail(0.2f)

                .listener(new RequestListener<Drawable>() {
                    // 콜백 리스너

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false; // 이미지 로딩 실패시
                    }

                    // 이미지 로드 완료시
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).setLoopCount(1); // gif 1번재생
                        }

                        if (isFirstResource) return false;

                        if (runnable != null) {
                            // runnable = 스레드 구현 인터페이스
                            // 스레드 = 무언가를 백그라운드로 돌려놓고 다른 여러가지 일을 하는 것
                            // 핸들러 = 다른 객체들이 보낸 데이터를 받고 이 데이터를 처리하는 것

                            // runnable이 실행중이면 핸들러의 딜레이를 0.4초로 줘라
                            new Handler().postDelayed(runnable, 400);
                        }
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);
    }

    public static void Glide(Context context, Object object, ImageView imageView) {
        Glide(context, object, imageView, null);
    }

    public static void Glide(Context context, Object object, ImageView imageView, final Runnable runnable) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(object)
                .thumbnail(0.2f)

                .listener(new RequestListener<Drawable>() {
                    // 콜백 리스너
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (runnable != null) runnable.run();
                        // runnable이 실행중이 아니면 runnable 실행해라.
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);
    }


    public static void GlideCenterCrop200(Context context, String imgUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(400, 400)     // 이미지 사이즈 (400, 400)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(imgUrl)
                .thumbnail(0.2f)
                .apply(options)
                .into(imageView);
    }

    public static void GlideColorFilter(Context context, String imgUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new ColorFilterTransformation(Color.argb(100, 0, 0, 0)))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        // 컬러필터(필터주는 것 r,g,b,a  0~255 a=100=투명도 40%)

        Glide.with(context)
                .load(imgUrl)
                .thumbnail(0.2f)
                .apply(options)
                .into(imageView);
    }
}
