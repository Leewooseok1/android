package com.example.lws.work.model;

import java.io.Serializable; // "직렬화", 해당 Object를 구성하는 모든 것들을 한줄로, 직렬로 만들어서 저장, 전송 등을 편하게 한다는 의미
import java.util.ArrayList;
import java.util.List;

public class WardrobeMO implements Serializable {
    // 맴버 변수들을 연속된 메모링 ㅔ할당된 형태인 직렬화시킨다. 그래야 다른 액티비티에 데이터 객체 넘겨줄수있다.
    private int idx;
    private Object imageUrl;
    private String type;
    private int color;
    private List<Integer> recommendColors;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Integer> getRecommendColors() {
        return recommendColors;
    }

    public void setRecommendColors(List<Integer> recommendColors) {
        this.recommendColors = recommendColors;
    }
}
