package com.example.lws.work;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lws.work.adapter.GridAdapter;
import com.example.lws.work.common.GlideManager;
import com.example.lws.work.common.SharedPref;
import com.example.lws.work.common.SquareImageView;
import com.example.lws.work.model.WardrobeMO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelectActivity extends BaseActivity implements View.OnClickListener {

    private boolean isTopSelected = false;
    private boolean isBottomSelected = false;

    private WardrobeMO topItem;
    private WardrobeMO bottomItem;

    private File tempFile = null;
    private Uri photoUri = null;

    private SelectType selectType;

    private Dialog favoriteDialog;      // dialog = 다중선택을 위한 창(팝업창)
    private Dialog selectDialog;

    private ImageView imageView1;
    private ImageView imageView2;

    private SquareImageView imageView;

    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        imageView1 = findViewById(R.id.imageView1);     // 이미지 아이디 찾기
        imageView2 = findViewById(R.id.imageView2);

        findViewById(R.id.btnBack).setOnClickListener(this);        // 클릭리스너 연결
        findViewById(R.id.btnSetting).setOnClickListener(this);
        findViewById(R.id.btnConfirm).setOnClickListener(this);
        findViewById(R.id.btnTop).setOnClickListener(this);
        findViewById(R.id.btnBottom).setOnClickListener(this);

    }

    // 다이얼로그 상세 코드는 아래쪽에 있다.

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:      // 백버튼
                finish();
                break;

            case R.id.btnSetting:       // help 버튼
                startActivity(new Intent(SelectActivity.this, HelpActivity.class));
                break;

                // 결과버튼 = 상의가 없거나 하의가 없으면 토스트로 둘다 추가해야한다.
            case R.id.btnConfirm: {
                if (!isTopSelected || !isBottomSelected) {
                    Toast.makeText(activity, "상의와 하의를 모두 추가해야 합니다.", Toast.LENGTH_LONG).show();
                    break;
                }

                Intent intent = new Intent(activity, ResultActivity.class);     // 결과창으로 넘겨준다.
                intent.putExtra("topItem", topItem);            // 상의 이미지와
                intent.putExtra("bottomItem", bottomItem);      // 하의 이미지도 넘겨주고 넘어가면서 토스트로 알려준다.
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "결과는 하의를 기준으로 색상을 추천해줍니다.", Toast.LENGTH_LONG).show();
            }
            break;

            case R.id.btnTop:

                showDialog(SelectType.TYPE_TOP);        // 상의이미지 선택창 띄워준다.
                break;

            case R.id.btnBottom:
                showDialog(SelectType.TYPE_BOTTOM);     // 하의 이미지 선택창 띄워준다.
                break;


            /** 다이얼로그 버튼 클릭 이벤트 **/

            // 카메라 알고리즘
            case R.id.btnCamera: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    try {
                        tempFile = createImageFile();
                    } catch (IOException e) {
                        Toast.makeText(activity, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    if (tempFile != null) {
                        photoUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", tempFile);

                         intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        activity.startActivityForResult(intent, 2);
                    }
                }
            }
            break;

            // 갤러리 알고리즘
            case R.id.btnGallery: {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                activity.startActivityForResult(intent, 1);
            }
            break;

            // 즐겨찾기 알고리즘
            case R.id.btnFavorite:
                if (TextUtils.isEmpty(SharedPref.getFavorites(activity))) {
                    // 값이 비어있으면 즐겨찾기 목록이 없는거니까 토스트를 띄워준다.
                    Toast.makeText(activity, "즐겨찾기 목록이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

                showFavoriteDialog();
                break;

                // 결과확인 버튼
            case R.id.btnOk:
                switch (selectType) {
                    case TYPE_TOP:
                        if (topItem == null) break; // 상의값이 없으면 break

                        isTopSelected = true;   // 상의값 존재하면
                        GlideManager.GlideCenterCrop(activity, topItem.getImageUrl(), imageView1);  //이미지 크롭해서 보여준다.
                        break;

                    case TYPE_BOTTOM:
                        if (bottomItem == null) break;  // 하의값이 없으면 break

                        isBottomSelected = true;
                        GlideManager.GlideCenterCrop(activity, bottomItem.getImageUrl(), imageView2);   // 이미지 크롭해서 보여준다.
                        break;
                }
                selectDialog.dismiss(); // 다이얼로그 종료
                break;

            case R.id.btnSave:  // 미완성
                break;
        }
    }

    private void showFavoriteDialog() {
        favoriteDialog = new Dialog(activity);  // 다이얼로그로 창띄워서 즐겨찾기 보여줄꺼다.
        favoriteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);   // 타이틀 제거
        favoriteDialog.setContentView(R.layout.dialog_favorite_list);   // 리사이클러 레아이웃(여러개의 이미지 + 메인화면에서 추가된 이미지와 같음) 연결
        favoriteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // semi-transparent //배경 삽입
        favoriteDialog.setCanceledOnTouchOutside(false); // 바깥쪽 화면 터치해도 종료x

        RecyclerView recyclerView = favoriteDialog.findViewById(R.id.recyclerView); // 리사이클러뷰 아이디 찾기
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2)); // 리사이클러뷰 - 그리드와 연결
        GridAdapter adapter = new GridAdapter(activity);    // 그리드 어뎁터 생성 (즐겨찾기에서 이미지 데이터 관리)

        List<WardrobeMO> list;
        Type type = new TypeToken<List<WardrobeMO>>() {
        }.getType();    //url 타입 가져온다.
        Gson gson = new Gson(); // gson 생성
        list = gson.fromJson(SharedPref.getFavorites(activity), type);  // 데이터변환해서 favorite에 가져온다.
        adapter.setItem(list, 3);   // 아래에 상세 코드 있다. 상의와 하의 타입과 이미지를 얻어온다.
        recyclerView.setAdapter(adapter);   // 리사이클러뷰에 어뎁터 연결해서

        favoriteDialog.show();  // 메인화면 버튼클릭하면 나오는 즐겨찾기 다이얼로그 보여준다.
                                // 메인화면에서 추가된 이미지까지 설정창에서 나와야 되니까 글라이드(이미지 쉽게 처리), gson(데이터 변환), 리사이클러뷰(여러개의 이미지) 사용.
    }

    public void setItem(WardrobeMO item) {
        switch (selectType) {
            case TYPE_TOP:      // 아래 item에서 설정한 상의 타입 가져와
                topItem = item;
                topItem.setType("TOP");
                break;

            case TYPE_BOTTOM:   // 아래 item에서 설정한 하의 타입 가져와
                bottomItem = item;
                bottomItem.setType("BOTTOM");
                break;
        }
        GlideManager.GlideCenterCrop(activity, item.getImageUrl(), imageView);  //글라이드로 용량 낮추고, 이미지url 가져온다.
        favoriteDialog.dismiss();       // 다이얼로그 종료
    }

    private void showDialog(SelectType selectType) {
        this.selectType = selectType;

        selectDialog = new Dialog(activity);        // 선택창 생성
        selectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);     //타이틀 x
        selectDialog.setContentView(R.layout.dialog_select_image);      // 선택창 카,갤,즐 확인 이미지 레이아웃 가져오기
        selectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // semi-transparent // 배경삽입
        selectDialog.setCanceledOnTouchOutside(false); // 화면 바깥 클릭해도 종료x

        selectDialog.findViewById(R.id.btnCamera).setOnClickListener(this); // 온클릭 리스너 연결
        selectDialog.findViewById(R.id.btnGallery).setOnClickListener(this);
        selectDialog.findViewById(R.id.btnFavorite).setOnClickListener(this);
        selectDialog.findViewById(R.id.btnOk).setOnClickListener(this);
        selectDialog.findViewById(R.id.btnSave).setOnClickListener(this);

        imageView = selectDialog.findViewById(R.id.imageView);      // 이미지뷰 선택창에 나올수있께 아이디 찾는다.

        selectDialog.show(); // 다이얼로그 보여준다.
    }

    private File createImageFile() throws IOException {
        // 이미지 파일 생성 예외처리

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";

        // 생성 위치
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

         return image;

    }
    class OnscanCompletedListener implements MediaScannerConnection.OnScanCompletedListener {
        @Override
        public void onScanCompleted(String path, Uri uri) {
            path = "/DCIM/Camera/";
            // 미디어 스캐너가 파일 스캔을 마쳤을 때 클라이언트에 알리기 위해 호출.
            // 매개변수 path : 스캔한 파일 경로, uri: 이미지 주소
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 사진의 url 절대경로 구하기 == 최초 시작점에서 경유되는 모든 경로를 기입

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 2) {
            uploadUserImage(tempFile);      // 상세 코드는 아래에서 기술 || 이미지 데이터(파일) 업로드된 파일을 2에 넘겨준다.
        } else if (requestCode == 1) {
            Uri photoUri = data.getData();  // 이미지 데이터를 얻지 못했으면, 데이터를 uri에 넣는다.

            Cursor cursor = null;   // 선언
            File tempFile;      // 선언

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = {MediaStore.Images.Media.DATA}; // 미디어스토에 있는 이미지 데이터를 proj에 담는다.

                // getContentResolver = 응용p간 데이터 공유 방법
                assert photoUri != null;    // uri값이 있으면
                cursor = activity.getContentResolver().query(photoUri, proj, null, null, null);

                // getColumnIndexOrThrow = 특정 필드의 인덱스값 반환 =
                assert cursor != null;  // 커서 값이 있으면 미디어스토어의 이미지 데이터를 반환해서 colum_index에 넣는다.
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); //

                cursor.moveToFirst();   // 커서를 제일 첫행으로 이동시킨다.

                tempFile = new File(cursor.getString(column_index));    // 위에서 얻어온 이미지 데이터를 커서에 넣고 tempfile에 넣는다.

            } finally {
                if (cursor != null) {
                    cursor.close();     // 커서가 남아 있으면 종료한다.
                }
            }

            uploadUserImage(tempFile);  // 파일(이미지 데이터) 업로드
        }
    }

    private void uploadUserImage(File file) {
        GlideManager.GlideCenterCrop(activity, file, imageView);        // 글라인드로 이미지의 용량 다운

        switch (selectType) {
            case TYPE_TOP:      // 상의
                topItem = new WardrobeMO();     // 탑 아이템 생성
                topItem.setType("TOP");         // 타입 얻어와
                topItem.setImageUrl(file);      // 이미지 얻어와
                break;

            case TYPE_BOTTOM:
                bottomItem = new WardrobeMO();  // 바텀 아이템 생성
                bottomItem.setType("BOTTOM");   // 타입 얻어와
                bottomItem.setImageUrl(file);   // 이미지 얻어와
                break;
        }
    }

    public enum SelectType {
        TYPE_TOP, TYPE_BOTTOM       // enum = 서로 관련 있는 상수들을 모아 집합으로 정의함
    }
}