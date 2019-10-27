package com.example.lws.work.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.lws.work.fragment.FavoriteFragment;
import com.example.lws.work.fragment.MainFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    // 프래그먼트 = 하나의 Activity 에서 여러 개의 프래그먼트로 화면을 구성한 뒤 동적으로 부분 교체가 가능하며,
    // 여러 Activity 에서 재사용이 가능하다

    // Fragment를 이용한 view pager

    // FragmentStatePagerAdapter = 프래그먼트 개 수가 변하는 화면에 사용

    // 메인화면에서 즐겨찾기화면 이동시 사용

    private Fragment fragment;
    private int size;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // 처음은 메인화면,, default이면 즐겨찾기화면
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            default:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }

    // 리턴 2개
    @Override
    public int getCount() {
        return 2;
    }

    // 현재 표시하고 있는 뷰와 바로 양옆의 뷰만 생성하여 유지하고 범위를 벗어난 뷰는 삭제합니다
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        // instanceof == 객체타입을 확인하는데 사용 == 만약 Frgment클래스가 object타입이면
        if (object instanceof android.app.Fragment) {
            fragment = (Fragment) object;
            FragmentManager fragmentManager = fragment.getFragmentManager(); // 프래그먼트 매니저 불러온다.
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // 프래그먼트 트랜잭션 시작
            fragmentTransaction.remove(fragment); // 트랜잭션 제거
            fragmentTransaction.commit(); // 트랜잭션 마무리
        }
    }
}