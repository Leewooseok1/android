package com.example.lws.work;

import android.media.MediaScannerConnection; // 수동 (미디어스캐닝) 사진찍고 갤러리에서 바로 사진이 보이지 않을경우
import android.net.Uri;

class OnscanCompletedListener implements MediaScannerConnection.OnScanCompletedListener {
    @Override
        public void onScanCompleted(String path, Uri uri) {
        // 미디어 스캐너가 파일 스캔을 마쳤을 때 클라이언트에 알리기 위해 호출됩니다.
        // 매개변수 path : 스캔한 파일 경로, uri: 이미지 주소
    }
}
