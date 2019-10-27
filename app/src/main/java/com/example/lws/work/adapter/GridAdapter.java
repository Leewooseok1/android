package com.example.lws.work.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lws.work.BaseActivity;
import com.example.lws.work.R;
import com.example.lws.work.SelectActivity;
import com.example.lws.work.Sub1Activity;
import com.example.lws.work.common.GlideManager;
import com.example.lws.work.common.JacksonFactory;
import com.example.lws.work.common.JsonUtil;
import com.example.lws.work.common.SharedPref;
import com.example.lws.work.common.SquareImageView;
import com.example.lws.work.model.WardrobeMO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 어뎁터 연속된 데이터를 처리할 경우 아답터가 이 연속된 데이터를 받아서 리스트에 뿌려주고 이벤트도 관리 할 수 있도록 하는 클래스
    // 리스트뷰 그리드뷰 등 리스트 형태의 뷰를 사용할 때 꼭 필요한 기능

    // 리사이클러뷰 어뎁터를 상속받는다
    // 그리드 뷰( GridView ) 는 2차원의 그리드에 항목들을 표시하는 뷰그룹


    //상수는 값의 변화가 없어야 하기 때문에, 상수로 사용할 변수 앞에는 final을 붙인다
    public static final int TYPE_MAIN = 1;
    public static final int TYPE_FAVORITES = 2;
    public static final int TYPE_FAVORITES2 = 3;

    private List<InnerItem> listItem = new ArrayList<>();
    //ArrayList = 자료구조를 이용한 리스트, 배열과 달리 데이터 이동이 자유롭다.
    //ArrayList선언 -- ArrayList<데이터타입>  변수명 = new ArrayList<데이터타입>

    private class InnerItem {
        public int type;    // 클래스 속성
        public Object item;

        public InnerItem(int type, Object obj) {
            this.type = type; //
            this.item = obj; // InnerItem 객체의 item 속성 = obj 매개변수 == InnerItem 객체의 속성에 값입력
            // this = 자기자신
        }
    }

    private List<WardrobeMO> list = new ArrayList<>(); // ArrayList 선언
    private BaseActivity activity; //참조

    public GridAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    public void setItem(List<WardrobeMO> list, int type) {
        this.list = list;
        listItem.clear(); // 리스트 안의 모든 값 초기화

        // for (변수 : 배열 = 배열에 있는 값들을 하나씩 변수에 대입)
        for (WardrobeMO wardrobeMO : list) {
            listItem.add(new InnerItem(type, wardrobeMO)); // 데이터 추가
        }

        notifyDataSetChanged(); // 데이터 갱신
    }

    @NonNull
    @Override // 상위 클래스에서 구현되지 않은 메소드를 상속받아서 구현한다.

    // RecyclerView.Adapter를 상속받아 새로운 어뎁터를 만들때 오버라이드 필요한 메소드 3개
    // onCreateViewHolder, onBindViewHolder, getItemCount

    // RecyclerView == 한 화면 표시되는 데이터를 스크롤 가능한 리스트로 표시하는 뷰 = 리스트뷰의 확장판
    // viewholder == 성능향상, findviewbyId 함수 호출 줄이기 위한 저장소 = 스크롤이 부드러워진다. = 홀더에 보관하는 객체
    // viewgroup == 여랙의 view 포함하는 컨테이너 = 화면 배치속성을 갖는 layout가지고 있다.

    // onCreateViewHoloder == viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder holder;

        // layoutinflate = xml에 정의된 리소스를 view 형태로 반환
        // viewgroup 구현시 배경화면이 되는 layout 만들어놓고 view 형태로 반환환받아 ativity에서 실행

        // 조건문으로 case마다 view를 activity에서 실행
        // attachToRoot 레이아웃이 팽창되지만 다른 레이아웃에는 연결되지 않습니다

        switch (viewType) {
            case TYPE_MAIN:
                v = LayoutInflater.from(activity).inflate(R.layout.row_wardrobe_1, parent, false);
                holder = new MainViewHolder(v);
                break;
            case TYPE_FAVORITES:
                v = LayoutInflater.from(activity).inflate(R.layout.row_wardrobe_2, parent, false);
                holder = new FavoriteViewHolder(v);
                break;
            case TYPE_FAVORITES2:
                v = LayoutInflater.from(activity).inflate(R.layout.row_wardrobe_1, parent, false);
                holder = new FavoriteViewHolder2(v);
                break;
            default:
                v = LayoutInflater.from(activity).inflate(R.layout.row_wardrobe_1, parent, false);
                holder = new MainViewHolder(v);
                break;
        }

        return holder;
    }

    // onBindViewHolder == position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType(); // position에 해당하는 뷰타입 리턴.

        switch (viewType) {
            case TYPE_MAIN:
                ((MainViewHolder) holder).setItem((WardrobeMO) listItem.get(position).item);
                break;
            case TYPE_FAVORITES:
                ((FavoriteViewHolder) holder).setItem((WardrobeMO) listItem.get(position).item);
                break;
            case TYPE_FAVORITES2:
                ((FavoriteViewHolder2) holder).setItem((WardrobeMO) listItem.get(position).item);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItem.get(position).type;
    }


    // getItemCount = 전체 아이템 갯수 리턴턴
   @Override
    public int getItemCount() {
        return listItem.size();
    }


    // 아이템 뷰를 저장하는 메인화면 뷰홀더 클래스
    // r.layout wardrobe_1
    class MainViewHolder extends RecyclerView.ViewHolder {

        View v;
        ViewGroup container;
        SquareImageView imageView;


        public MainViewHolder(@NonNull View v) {
            super(v);
            this.v = v;
            container = v.findViewById(R.id.container);
            imageView = v.findViewById(R.id.imageView);
        }

        public void setItem(final WardrobeMO item) {
            imageView.setImageResource((int) item.getImageUrl()); //이미지 iri를
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, Sub1Activity.class);
                    intent.putExtra("model", item);
                    activity.startActivity(intent);
                }
            });
        }
    }

    // 아이템 뷰를 저장하는 즐겨찾기 뷰홀더 클래스
    // r.layout wardrobe_2 (+모양)
    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        View v;
        ViewGroup container;
        SquareImageView imageView;
        ImageView imgPlus;


        public FavoriteViewHolder(@NonNull View v) {
            super(v);
            this.v = v;
            container = v.findViewById(R.id.container);
            imageView = v.findViewById(R.id.imageView);
            imgPlus = v.findViewById(R.id.imgPlus);
        }

        // setVisibility 뷰,위젯,레이아웃 숨기기 없애기 다시보이기 visible(보임), gone(없어짐)

        public void setItem(final WardrobeMO item) {
            if (item.getImageUrl() != null) {
                imgPlus.setVisibility(View.GONE);       // 이미지가 추가가 되었을경우 즐겨찾기화면에서 +이미지 사라진다
            } else {
                imgPlus.setVisibility(View.VISIBLE);    // 이미지가 없는경우  +이미지 나타내준다.
            }
            GlideManager.Glide(activity, item.getImageUrl(), imageView);

            // onclick = 클릭시 나타나는 이벤트
            // AbstractListener = BaseActivity에서 오버라이드
            // onActivityResult = A에서 B로갔다가 다시 A로 넘어올때

            // 즐겨찾기에 이미지를 추가하는 코드 (클릭)
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.registerListener(new BaseActivity.AbstractListener() {
                        @Override
                        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                            activity.unregisterListener(this);
                            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                                Uri photoUri = data.getData();      //uri로 데이터 가져오기

                                list.clear(); // 리스트 초기화

                                //
                                for (int i = 0; i < listItem.size(); i++) {
                                    if (listItem.get(i).type == TYPE_FAVORITES) {
                                        WardrobeMO model = (WardrobeMO) listItem.get(i).item;
                                        if (model.getIdx() == item.getIdx()) {
                                            model.setImageUrl(photoUri.toString());
                                            listItem.remove(i);
                                            listItem.add(i, new InnerItem(TYPE_FAVORITES, model));
                                        }
                                        list.add(model);
                                    }
                                }


                                SharedPref.setFavorites(activity, new Gson().toJson(list)); // 액티비티에있는 url를 sharedpref에 넘겨줄건데
                                // gson으로부터 json에게 데이터 교환해서(object->gson(키와 값의 형태로 sharedpref에 넘겨준다.)

                                notifyDataSetChanged(); // 데이터 갱신
                            }
                        }
                    });

                    Intent intent = new Intent(Intent.ACTION_PICK); // 사진 선택
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE); // 타입 미디어스토어
                    activity.startActivityForResult(intent, 1);
                }
            });

            // 즐겨찾기에서 이미지를 삭제하는 코드 (롱클릭)
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (item.getImageUrl() == null) return false; // 이미지가 없으면 리턴 실패

                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 프레임의 타이틀 제거
                    dialog.setContentView(R.layout.dialog_select_menu); // delete 레이아웃 소환
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // semi-transparent

                    // delete 버튼 클릭하면
                    dialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list.clear();
                            for (int i = 0; i < listItem.size(); i++) {
                                if (listItem.get(i).type == TYPE_FAVORITES) {
                                    WardrobeMO model = (WardrobeMO) listItem.get(i).item;
                                    if (model.getIdx() == item.getIdx()) {
                                        model.setImageUrl(null);
                                        listItem.remove(i);
                                        listItem.add(i, new InnerItem(TYPE_FAVORITES, model));
                                    }
                                    list.add(model);
                                }
                            }

                            SharedPref.setFavorites(activity, new Gson().toJson(list));

                            notifyDataSetChanged(); // 데이터 갱신

                            dialog.dismiss(); // 종료
                        }
                    });

                    dialog.show(); // 보여준다.

                    return true;
                }
            });
        }
    }

    // select창에서 즐겨찾기버튼 눌러서 즐겨찾기창에서 빈칸 선택시
    class FavoriteViewHolder2 extends RecyclerView.ViewHolder {

        View v;
        ViewGroup container;
        SquareImageView imageView;
//        TextView txtDesc;

        public FavoriteViewHolder2(@NonNull View v) {
            super(v);
            this.v = v;
            container = v.findViewById(R.id.container);
            imageView = v.findViewById(R.id.imageView);
        }

        public void setItem(final WardrobeMO item) {
            GlideManager.Glide(activity, item.getImageUrl(), imageView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getImageUrl() == null) {
                        Toast.makeText(activity, "빈칸은 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ((SelectActivity) activity).setItem(item);

                }
            });
        }
    }
}
