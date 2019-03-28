package com.egov.smartqr.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.egov.smartqr.LoginActivity;
import com.egov.smartqr.MainActivity;
import com.egov.smartqr.R;
import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.BitmapUtil;
import com.egov.smartqr.util.KeyboardUtil;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.util.UnitUtil;
import com.egov.smartqr.vo.AddrVO;
import com.egov.smartqr.vo.QrVO;
import com.egov.smartqr.vo.SearchInfo;
import com.egov.smartqr.vo.SearchVO;
import com.egov.smartqr.vo.ThemeVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class MapSearchFragment extends Fragment implements View.OnClickListener, MapView.POIItemEventListener {

    @BindView(R.id.frame_map) FrameLayout frame_map;
    @BindView(R.id.btn_search_view) Button btn_search_view;

    @BindView(R.id.cardview_search) CardView cardview_search;
    @BindView(R.id.radio_num) RadioButton radio_num;
    @BindView(R.id.radio_addr) RadioButton radio_addr;

    @BindView(R.id.frame_sigungu) FrameLayout frame_sigungu;
    @BindView(R.id.textview_sigungu) TextView textview_sigungu;

    @BindView(R.id.linear_num) LinearLayout linear_num;
    @BindView(R.id.edittext_num) EditText edittext_num;

    @BindView(R.id.linear_upmyundong) LinearLayout linear_upmyundong;
    @BindView(R.id.frame_upmyundong) FrameLayout frame_upmyundong;
    @BindView(R.id.textview_upmyundong) TextView textview_upmyundong;
    @BindView(R.id.linear_ri) LinearLayout linear_ri;
    @BindView(R.id.frame_ri) FrameLayout frame_ri;
    @BindView(R.id.textview_ri) TextView textview_ri;

    @BindView(R.id.frame_search) FrameLayout frame_search;
    @BindView(R.id.frame_qr) FrameLayout frame_qr;

    @BindView(R.id.frame_map_plus) FrameLayout frame_map_plus;
    @BindView(R.id.frame_map_minus) FrameLayout frame_map_minus;

    //테마검색
    @BindView(R.id.frame_theme_plus) FrameLayout frame_theme_plus;
    @BindView(R.id.cardview_theme) CardView cardview_theme;
    @BindView(R.id.linear_theme_1) LinearLayout linear_theme_1;
    @BindView(R.id.check_theme_1) CheckBox check_theme_1;
    @BindView(R.id.imageview_theme_1) ImageView imageview_theme_1;
    @BindView(R.id.linear_theme_2) LinearLayout linear_theme_2;
    @BindView(R.id.check_theme_2) CheckBox check_theme_2;
    @BindView(R.id.imageview_theme_2) ImageView imageview_theme_2;
    @BindView(R.id.linear_theme_3) LinearLayout linear_theme_3;
    @BindView(R.id.check_theme_3) CheckBox check_theme_3;
    @BindView(R.id.imageview_theme_3) ImageView imageview_theme_3;
    @BindView(R.id.linear_theme_4) LinearLayout linear_theme_4;
    @BindView(R.id.check_theme_4) CheckBox check_theme_4;
    @BindView(R.id.imageview_theme_4) ImageView imageview_theme_4;


    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.linear_no_result) LinearLayout linear_no_result;

    //ArrayList<SearchInfo> listSearch = new ArrayList<SearchInfo>();
    ArrayList<SearchVO> arySearch;//검색결과
    SearchRecyclerAdapter searchRecyclerAdapter = null;

    //테마검색
    ArrayList<ThemeVO> aryThemeJihasu;
    ArrayList<ThemeVO> aryThemeGonggong;
    ArrayList<ThemeVO> aryThemePerm;
    ArrayList<ThemeVO> aryThemeCivil;

    //주소검색
    ArrayList<AddrVO> aryUmd = null;//읍면동
    ArrayList<AddrVO> aryRi = null;//리

    //다음지도
    MapView mapView;

    Bitmap bmpIcon, bmpIconSel;
    Bitmap bmpTheme1, bmpTheme1Sel;
    Bitmap bmpTheme2, bmpTheme2Sel;
    Bitmap bmpTheme3, bmpTheme3Sel;
    Bitmap bmpTheme4, bmpTheme4Sel;

    public static MapSearchFragment newInstance() {
        return new MapSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_search, container, false);
        ButterKnife.bind(this, view);

        btn_search_view.setOnClickListener(this);
        radio_num.setOnClickListener(this);
        radio_addr.setOnClickListener(this);
        frame_sigungu.setOnClickListener(this);
        frame_upmyundong.setOnClickListener(this);
        frame_ri.setOnClickListener(this);
        frame_search.setOnClickListener(this);
        frame_qr.setOnClickListener(this);
        frame_theme_plus.setOnClickListener(this);
        linear_theme_1.setOnClickListener(this);
        check_theme_1.setOnClickListener(this);
        linear_theme_2.setOnClickListener(this);
        check_theme_2.setOnClickListener(this);
        linear_theme_3.setOnClickListener(this);
        check_theme_3.setOnClickListener(this);
        linear_theme_4.setOnClickListener(this);
        check_theme_4.setOnClickListener(this);
        frame_map_plus.setOnClickListener(this);
        frame_map_minus.setOnClickListener(this);

        setRadio(radio_num);

//test data
//for(int i=0; i<50; i++) {
//    if(i%2==0) listSearch.add(new SearchInfo("2200600016", "공공지하수시설", "농업용", "기타"));
//    else listSearch.add(new SearchInfo("2200600018", "대용량지하수시설", "생활용", "민방위용"));
//}
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyclerAdapter = new SearchRecyclerAdapter();
        recycler.setAdapter(searchRecyclerAdapter);


        //Map
        mapView = new MapView(getContext());

        ViewGroup mapViewContainer = frame_map;
        mapViewContainer.addView(mapView);

        // 중심점 변경 : 충남도청
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(36.658871, 126.672844), true);

        // 줌 레벨 변경
        mapView.setZoomLevel(5, true);
        mapView.setPOIItemEventListener(this);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        makeMarkerBitmap();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_search_view) {
//            Popup popup = new Popup(map);
//            vw.Coord coord = new Coord();
//            coord.setX(127.1);
//            coord.setY(37.32);
//            popup.show("테스트 팝업창 - 좌표값 : " + String.format("%.4f", coord.getX()) + " // " + String.format("%.4f", coord.getY()), coord);

            if(cardview_search.getVisibility() == View.VISIBLE) {
                cardview_search.setVisibility(View.GONE);
            } else {
                cardview_search.setVisibility(View.VISIBLE);
            }
        }
        else if(view == radio_num) {
            setRadio(radio_num);
        }
        else if(view == radio_addr) {
            setRadio(radio_addr);

            textview_upmyundong.setText("");
            textview_ri.setText("");

            //관정주소 라디오 선택시
            reqSelectJusoList(textview_sigungu.getText().toString(), "");
        }
        else if(view == frame_sigungu) {//시군구 선택
            DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), frame_sigungu);
            for(int i=0; i< Common.getArySfTeamCode().size(); i++) {
                droppyBuilder.addMenuItem(new DroppyMenuItem(Common.getArySfTeamCode().get(i).sggNm)).addSeparator();
            }
            droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                @Override
                public void call(View v, int id) {
                    textview_sigungu.setText(Common.getArySfTeamCode().get(id).sggNm);

                    //관정주소 라디오 선택시
                    if(radio_addr.isChecked()) {
                        reqSelectJusoList(textview_sigungu.getText().toString(), "");
                    }
                }
            });
            DroppyMenuPopup droppyMenu = droppyBuilder.build();
            droppyMenu.show();
        }
        else if(view == frame_upmyundong) {//읍면동 선택
            if(aryUmd != null && aryUmd.size() > 0) {
                setUmbCombo();
            }
        }
        else if(view == frame_ri) {//리 선택
            if(aryRi != null && aryRi.size() > 0) {
                setRiCombo();
            }
        }
        else if(view == frame_search) {
            //관정번호 선택
            if(radio_num.isChecked()) {
                String sel_type = "no";
                String search_text = edittext_num.getText().toString();
                String stc = Common.sfteamcodeOfName(textview_sigungu.getText().toString());
                String sgg = "";
                String umd = "";
                String ri = "";
                reqSelectSearchJihasuList(sel_type, search_text, stc, sgg, umd, ri);
            } else {//관정주소 선택
                String sel_type = "addr";
                String search_text = "";
                String stc = "";
                String sgg = textview_sigungu.getText().toString();
                String umd = textview_upmyundong.getText().toString();
                String ri = textview_ri.getText().toString();
                if(umd.endsWith("전체")) umd = "";
                if(ri.endsWith("전체")) ri = "";
                reqSelectSearchJihasuList(sel_type, search_text, stc, sgg, umd, ri);
            }
        }
        else if(view == frame_qr) {
            NaviUtil.gotoQr(this);
//            new IntentIntegrator(getActivity()).initiateScan();
        }
        else if(view == frame_theme_plus) {
            if(cardview_theme.getVisibility() == View.VISIBLE) {
                cardview_theme.setVisibility(View.GONE);
            } else {
                cardview_theme.setVisibility(View.VISIBLE);
            }
        }
        else if(view == linear_theme_1 || view == check_theme_1) {
            if(view == linear_theme_1) {
                if(!check_theme_1.isChecked()) {
                    check_theme_1.setChecked(true);
                } else {
                    check_theme_1.setChecked(false);
                }
            }
            if(check_theme_1.isChecked()) {
                setTheme(linear_theme_1, true);
            } else {
                setTheme(linear_theme_1, false);
            }
        }
        else if(view == linear_theme_2 || view == check_theme_2) {
            if(view == linear_theme_2) {
                if(!check_theme_2.isChecked()) {
                    check_theme_2.setChecked(true);
                } else {
                    check_theme_2.setChecked(false);
                }
            }
            if(check_theme_2.isChecked()) {
                setTheme(linear_theme_2, true);
            } else {
                setTheme(linear_theme_2, false);
            }
        }
        else if(view == linear_theme_3 || view == check_theme_3) {
            if(view == linear_theme_3) {
                if(!check_theme_3.isChecked()) {
                    check_theme_3.setChecked(true);
                } else {
                    check_theme_3.setChecked(false);
                }
            }
            if(check_theme_3.isChecked()) {
                setTheme(linear_theme_3, true);
            } else {
                setTheme(linear_theme_3, false);
            }
        }
        else if(view == linear_theme_4 || view == check_theme_4) {
            if(view == linear_theme_4) {
                if(!check_theme_4.isChecked()) {
                    check_theme_4.setChecked(true);
                } else {
                    check_theme_4.setChecked(false);
                }
            }
            if(check_theme_4.isChecked()) {
                setTheme(linear_theme_4, true);
            } else {
                setTheme(linear_theme_4, false);
            }
        }
        else if(view == frame_map_plus) {
            mapView.zoomIn(true);
        }
        else if(view == frame_map_minus) {
            mapView.zoomOut(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == NaviUtil.ReqQrActivity) {
//            if(resultCode == Activity.RESULT_OK) {
//                //http://smartgw.chungnam.go.kr/mobile/jihasuView.do?gid=89
//                String text = data.getStringExtra("text");
//                int find = text.indexOf("gid=");
//                String gid = text.substring(find+4, text.length());
//                reqGetPermAndSf(gid);
//            }
//        }

        // QR코드/ 바코드를 스캔한 결과
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String text = result.getContents();
        int find = text.indexOf("gid=");
        String gid = text.substring(find+4, text.length());
        reqGetPermAndSf(gid);
    }

    //--- Daum지도 마커 클릭 이벤트 리스너 ------------------------------
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        if(mapPOIItem.getTag() == 1) {
            SearchVO search = (SearchVO)mapPOIItem.getUserObject();

            String stc = "", pnn = "";
            if(search != null) {
                stc = search.sf_team_code;
                pnn = search.perm_nt_no;
            }
            ((MainActivity)getActivity()).screenMove(DetailInfoFragment.class, stc, pnn);
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    //------------------------------------------------------------

    void makeMarkerBitmap() {
        bmpIcon = BitmapFactory.decodeResource(getResources(), R.drawable.gw_map_icon);
        bmpIconSel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_map_icon_sel);
        bmpTheme1 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_country_icon);
        bmpTheme1Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_country_icon_sel);
        bmpTheme2 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_perm_icon);
        bmpTheme2Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_perm_icon_sel);
        bmpTheme3 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_farm_icon);
        bmpTheme3Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_farm_icon_sel);
        bmpTheme4 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_def_icon);
        bmpTheme4Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_def_icon_sel);

        int size = UnitUtil.dp2pxl(getContext(), 40);
        //resize
        bmpIcon = Bitmap.createScaledBitmap(bmpIcon, size, size, true);
        bmpIconSel = Bitmap.createScaledBitmap(bmpIconSel, size, size, true);
        bmpTheme1 = Bitmap.createScaledBitmap(bmpTheme1, size, size, true);
        bmpTheme1Sel = Bitmap.createScaledBitmap(bmpTheme1Sel, size, size, true);
        bmpTheme2 = Bitmap.createScaledBitmap(bmpTheme2, size, size, true);
        bmpTheme2Sel = Bitmap.createScaledBitmap(bmpTheme2Sel, size, size, true);
        bmpTheme3 = Bitmap.createScaledBitmap(bmpTheme3, size, size, true);
        bmpTheme3Sel = Bitmap.createScaledBitmap(bmpTheme3Sel, size, size, true);
        bmpTheme4 = Bitmap.createScaledBitmap(bmpTheme4, size, size, true);
        bmpTheme4Sel = Bitmap.createScaledBitmap(bmpTheme4Sel, size, size, true);
    }

    void setRadio(View view) {
        radio_num.setChecked(false);
        radio_addr.setChecked(false);
        linear_num.setVisibility(View.GONE);
        linear_upmyundong.setVisibility(View.GONE);

        if(view == radio_num) {
            radio_num.setChecked(true);
            linear_num.setVisibility(View.VISIBLE);
            linear_upmyundong.setVisibility(View.GONE);
            linear_ri.setVisibility(View.INVISIBLE);
        }
        else if(view == radio_addr) {
            radio_addr.setChecked(true);
            linear_num.setVisibility(View.GONE);
            linear_upmyundong.setVisibility(View.VISIBLE);
            linear_ri.setVisibility(View.VISIBLE);
        }
    }

    void setTheme(View view, boolean add) {
        String type = "";
        if(view == linear_theme_1) {
            type = "jihasu";
        } else if(view == linear_theme_2) {
            type = "gonggong";
        } else if(view == linear_theme_3) {
            type = "perm";
        } else if(view == linear_theme_4) {
            type = "civil";
        }

        if(add) {
            String sgg = Common.sfteamcodeOfName(textview_sigungu.getText().toString());
            reqThemeMap(sgg, type);
        } else {
            addTheme(type, null);
        }
    }

    //시설물조회
    void reqThemeMap(final String sgg, final String type) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {
                            ArrayList<ThemeVO> aryTheme = null;
                            if(type.equals("jihasu")) {
                                aryThemeJihasu = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemeJihasu = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemeJihasu;
                                } catch (Exception e) {
                                }
                            } else if(type.equals("gonggong")) {
                                aryThemeGonggong = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemeGonggong = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemeGonggong;
                                } catch (Exception e) {
                                }
                            } else if(type.equals("perm")) {
                                aryThemePerm = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemePerm = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemePerm;
                                } catch (Exception e) {
                                }
                            } else if(type.equals("civil")) {
                                aryThemeCivil = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemeCivil = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemeCivil;
                                } catch (Exception e) {
                                }
                            }
                            //Process...
                            addTheme(type, aryTheme);
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=themeMap;sgg=%s;type=%s", sgg, type));
        lib.request(intent);
    }

    //시설물조회 - 검색
    void reqSelectSearchJihasuList(final String sel_type, final String search_text, final String stc, final String sgg, final String umd, final String ri) {
        // 중계서버 연계 API 객체 생성
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {
                            arySearch = null;
                            try {
                                Type listType = new TypeToken<ArrayList<SearchVO>>() {}.getType();
                                arySearch = new Gson().fromJson(re.getResultData(), listType);
                            } catch (Exception e) {
                            }

                            //TODO : 기존 하이브리드 앱과 순서가 다른것 같은데.. 필요하면 소팅 넣으면 됨!

                            if(arySearch == null || arySearch.size() <= 0) {
                                recycler.setVisibility(View.GONE);
                                linear_no_result.setVisibility(View.VISIBLE);
                            } else {
                                recycler.setVisibility(View.VISIBLE);
                                linear_no_result.setVisibility(View.GONE);

                                searchRecyclerAdapter.notifyDataSetChanged();
                                recycler.setScrollY(0);

                                //map
                                addMarker();
                            }
                        } else {
                            UIUtil.alert(getContext(), "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
                        }
                    }
                });

        String p = "method=selectSearchJihasuList";
        p += ";sel_type=" + sel_type;
        p += ";search_text=" + search_text;
        p += ";stc=" + stc;
        p += ";sgg=" + sgg;
        p += ";umd=" + umd;
        p += ";ri=" + ri;

        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", p);
        lib.request(intent);
    }

    //주소 검색
    void reqSelectJusoList(final String sgg, final String umd) {
        // 중계서버 연계 API 객체 생성
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
                            if(StrUtil.isEmpty(umd)) {
                                aryUmd = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<AddrVO>>() {}.getType();
                                    aryUmd = new Gson().fromJson(re.getResultData(), listType);
                                } catch (Exception e) {
                                }

                                aryUmd.add(0, new AddrVO(0, "", "전체", ""));
                                textview_upmyundong.setText("전체");
                                setUmbCombo();

                                textview_ri.setText("");
                                clearRiCombo();
                            } else {
                                aryRi = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<AddrVO>>() {
                                    }.getType();
                                    aryRi = new Gson().fromJson(re.getResultData(), listType);
                                } catch (Exception e) {
                                }

                                aryRi.add(0, new AddrVO(0, "", "", "전체"));
                                textview_ri.setText("전체");
                                setRiCombo();
                            }
                        } else {
                            UIUtil.alert(getContext(), "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
                        }
                    }
                });

        String p = "method=selectJusoList";
        p += ";sgg=" + sgg;
        p += ";umd=" + umd;

        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", p);
        lib.request(intent);
    }

    //QR검색
    void reqGetPermAndSf(final String gid) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
                            //{"sf_team_code":"5580000","perm_nt_no":"2201500013"}
                            QrVO qr = null;
                            try {
                                qr = new Gson().fromJson(re.getResultData(), QrVO.class);
                            } catch (Exception e) {
                            }

                            //상세이동
                            if(qr != null) {
                                ((MainActivity)getActivity()).screenMove(DetailInfoFragment.class, qr.sf_team_code, qr.perm_nt_no);
                            }
                        } else {
                            UIUtil.alert(getContext(), "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
                        }
                    }
                });

        String p = "method=getPermAndSf";
        p += ";gid=" + gid;

        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", p);
        lib.request(intent);
    }

    void setUmbCombo() {
        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), frame_upmyundong);
        for (int i = 0; i < aryUmd.size(); i++) {
            droppyBuilder.addMenuItem(new DroppyMenuItem(aryUmd.get(i).umd)).addSeparator();
        }
        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                textview_upmyundong.setText(aryUmd.get(id).umd);

                if(textview_upmyundong.getText().toString().endsWith("전체")) {
                    textview_ri.setText("");
                    clearRiCombo();
                } else {
                    reqSelectJusoList(textview_sigungu.getText().toString(), textview_upmyundong.getText().toString());
                }
            }
        });
        DroppyMenuPopup droppyMenu = droppyBuilder.build();
        //droppyMenu.show();
    }
    void setRiCombo() {
        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), frame_ri);
        for (int i = 0; i < aryRi.size(); i++) {
            droppyBuilder.addMenuItem(new DroppyMenuItem(aryRi.get(i).ri)).addSeparator();
        }
        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                textview_ri.setText(aryRi.get(id).ri);
            }
        });
        DroppyMenuPopup droppyMenu = droppyBuilder.build();
        //droppyMenu.show();
    }
    void clearRiCombo() {
        frame_ri.setOnClickListener(null);
    }

    View.OnClickListener onClickList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchVO search = (SearchVO) view.getTag();

            //선택처리
            for(int i=0; i<arySearch.size(); i++) {
                if(arySearch.get(i).is_selected) {
                    arySearch.get(i).is_selected = false;
                    searchRecyclerAdapter.notifyItemChanged(i);
                }
            }
            search.is_selected = true;
            searchRecyclerAdapter.notifyItemChanged(arySearch.indexOf(search));

            //지도이동
            //double lat = Double.parseDouble(search.lttd_dg) + Double.parseDouble(search.lttd_mint) / 60. + Double.parseDouble(search.lttd_sc) / 3600.;
            //double lon = Double.parseDouble(search.litd_dg) + Double.parseDouble(search.litd_mint) / 60. + Double.parseDouble(search.litd_sc) / 3600.;

            // 중심점 변경
            //mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lon), true);
            // 줌 레벨 변경
            mapView.setZoomLevel(4, true);

            MapPOIItem[] items = mapView.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 1 && items[i].getUserObject().equals(search)) {
                        mapView.selectPOIItem(items[i], true);
                        break;
                    }
                }
            }

//            String stc = "", pnn = "";
//            if(search != null) {
//                stc = search.sf_team_code;
//                pnn = search.perm_nt_no;
//            }
//            ((MainActivity)getActivity()).screenMove(DetailInfoFragment.class, stc, pnn);
        }
    };

    void addMarker() {
        MapPOIItem[] items = mapView.getPOIItems();
        if(items != null && items.length > 0) {
            for(int i=0; i<items.length; i++) {
                if(items[i].getTag() == 1) {
                    mapView.removePOIItem(items[i]);
                }
            }
        }

        double minX=999999999, minY=999999999, maxX=0, maxY=0;
        for(int i=0; i<arySearch.size(); i++) {
            SearchVO search = arySearch.get(i);

            try {
                //얘가 null인 경우가 있음..
                //if(search.lttd_dg == null) search.lttd_dg = "37";
                double lat = Double.parseDouble(search.lttd_dg) + Double.parseDouble(search.lttd_mint) / 60. + Double.parseDouble(search.lttd_sc) / 3600.;
                double lon = Double.parseDouble(search.litd_dg) + Double.parseDouble(search.litd_mint) / 60. + Double.parseDouble(search.litd_sc) / 3600.;

                if(lon < minX) minX = lon;
                if(lon > maxX) maxX = lon;
                if(lat < minY) minY = lat;
                if(lat > maxY) maxY = lat;

                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName("Custom Marker");
                customMarker.setTag(1);
                customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                customMarker.setCustomImageBitmap(bmpIcon);
                customMarker.setCustomSelectedImageBitmap(bmpIconSel);
                customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                customMarker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                customMarker.setUserObject(search);

                mapView.addPOIItem(customMarker);
            } catch (Exception e) {
                Log.e("", e.toString());
            }
        }

        //전체 포인트가 다 보이게 바운드 설정
        MapPointBounds bounds = new MapPointBounds(MapPoint.mapPointWithGeoCoord(minY, minX), MapPoint.mapPointWithGeoCoord(maxY, maxX));
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, 100));
    }

    void addTheme(String type, ArrayList<ThemeVO> aryTheme) {
        if(type.equals("jihasu")) {
            MapPOIItem[] items = mapView.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 10) {
                        mapView.removePOIItem(items[i]);
                    }
                }
            }
        } else if(type.equals("gonggong")) {
            MapPOIItem[] items = mapView.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 11) {
                        mapView.removePOIItem(items[i]);
                    }
                }
            }
        } else if(type.equals("perm")) {
            MapPOIItem[] items = mapView.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 12) {
                        mapView.removePOIItem(items[i]);
                    }
                }
            }
        } else if(type.equals("civil")) {
            MapPOIItem[] items = mapView.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 13) {
                        mapView.removePOIItem(items[i]);
                    }
                }
            }
        }

        if(aryTheme != null) {
            for (int i = 0; i < aryTheme.size(); i++) {
                ThemeVO theme = aryTheme.get(i);

                try {
                    //얘가 null인 경우가 있음..
                    //if(search.lttd_dg == null) search.lttd_dg = "37";
                    double lat = Double.parseDouble(theme.lttd_dg) + Double.parseDouble(theme.lttd_mint) / 60. + Double.parseDouble(theme.lttd_sc) / 3600.;
                    double lon = Double.parseDouble(theme.litd_dg) + Double.parseDouble(theme.litd_mint) / 60. + Double.parseDouble(theme.litd_sc) / 3600.;

                    MapPOIItem customMarker = new MapPOIItem();
                    customMarker.setItemName("Custom Marker");
                    customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                    customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                    if (type.equals("jihasu")) {
                        customMarker.setTag(10);
                        customMarker.setCustomImageBitmap(bmpTheme1);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme1Sel);
                    } else if (type.equals("gonggong")) {
                        customMarker.setTag(11);
                        customMarker.setCustomImageBitmap(bmpTheme2);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme2Sel);
                    } else if (type.equals("perm")) {
                        customMarker.setTag(12);
                        customMarker.setCustomImageBitmap(bmpTheme3);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme3Sel);
                    } else if (type.equals("civil")) {
                        customMarker.setTag(13);
                        customMarker.setCustomImageBitmap(bmpTheme4);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme4Sel);
                    }
                    customMarker.setUserObject(theme);
                    customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                    customMarker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

                    mapView.addPOIItem(customMarker);
                } catch (Exception e) {
                    Log.e("", e.toString());
                }
            }
        }
    }

    public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

        public SearchRecyclerAdapter(){
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_info, viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            SearchVO search = arySearch.get(position);
            viewHolder.imagebtn_click.setTag(search);
            viewHolder.imagebtn_click.setOnClickListener(onClickList);

            viewHolder.linear_root.setSelected(search.is_selected);

            viewHolder.textview_num.setText(StrUtil.hipen(search.perm_nt_no));
            viewHolder.textview_gubun.setText(StrUtil.hipen(search.pub_gbn));
            viewHolder.textview_useage.setText(StrUtil.hipen(search.uwater_srv));
            viewHolder.textview_useage_detail.setText(StrUtil.hipen(search.uwater_dul_srv));
        }

        @Override
        public int getItemCount() {
            if(arySearch == null) return 0;
            else return arySearch.size();
        }

        /**
         * 뷰 재활용을 위한 viewHolder
         */
        public class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.linear_root) LinearLayout linear_root;
            @BindView(R.id.imagebtn_click) ImageButton imagebtn_click;
            @BindView(R.id.textview_num) TextView textview_num;
            @BindView(R.id.textview_gubun) TextView textview_gubun;
            @BindView(R.id.textview_useage) TextView textview_useage;
            @BindView(R.id.textview_useage_detail) TextView textview_useage_detail;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getActivity().getLayoutInflater().inflate(R.layout.item_marker_info, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            LinearLayout linear_search = (LinearLayout) mCalloutBalloon.findViewById(R.id.linear_search);
            LinearLayout linear_theme = (LinearLayout) mCalloutBalloon.findViewById(R.id.linear_theme);
            if(poiItem.getTag() == 1) {
                linear_search.setVisibility(View.VISIBLE);
                linear_theme.setVisibility(View.GONE);
            } else {
                linear_search.setVisibility(View.GONE);
                linear_theme.setVisibility(View.VISIBLE);

                TextView textview_addr = (TextView) mCalloutBalloon.findViewById(R.id.textview_addr);
                TextView textview_guanjung_hyungtae = (TextView) mCalloutBalloon.findViewById(R.id.textview_guanjung_hyungtae);
                TextView textview_yongdo = (TextView) mCalloutBalloon.findViewById(R.id.textview_yongdo);
                TextView textview_saebu_yongdo = (TextView) mCalloutBalloon.findViewById(R.id.textview_saebu_yongdo);
                TextView textview_umyong_yebu = (TextView) mCalloutBalloon.findViewById(R.id.textview_umyong_yebu);
                TextView textview_gulchak_jikgyung = (TextView) mCalloutBalloon.findViewById(R.id.textview_gulchak_jikgyung);
                TextView textview_gaebal_day = (TextView) mCalloutBalloon.findViewById(R.id.textview_gaebal_day);
                TextView textview_sulchi_simdo = (TextView) mCalloutBalloon.findViewById(R.id.textview_sulchi_simdo);
                TextView textview_chisu_plan_amount = (TextView) mCalloutBalloon.findViewById(R.id.textview_chisu_plan_amount);
                TextView textview_yangsu_nungryuk = (TextView) mCalloutBalloon.findViewById(R.id.textview_yangsu_nungryuk);
                TextView textview_donryuk_jangchi_maryuk = (TextView) mCalloutBalloon.findViewById(R.id.textview_donryuk_jangchi_maryuk);
                TextView textview_tochulguan_jikgyung = (TextView) mCalloutBalloon.findViewById(R.id.textview_tochulguan_jikgyung);
                TextView textview_gonggong_sasul = (TextView) mCalloutBalloon.findViewById(R.id.textview_gonggong_sasul);

                ThemeVO theme = (ThemeVO)poiItem.getUserObject();

                String addr = "";
                if(StrUtil.notEmpty(theme.sido)){
                    addr += theme.sido + " ";
                }
                if(StrUtil.notEmpty(theme.sgg)){
                    addr += theme.sgg + " ";
                }
                if(StrUtil.notEmpty(theme.umd)){
                    addr += theme.umd + " ";
                }
                if(StrUtil.notEmpty(theme.ri)){
                    addr += theme.ri + " ";
                }
                if(StrUtil.notEmpty(theme.bunji)){
                    addr += theme.bunji + " ";
                }
                textview_addr.setText(addr);
                textview_guanjung_hyungtae.setText(StrUtil.hipen(theme.perm_nt_form));
                textview_yongdo.setText(StrUtil.hipen(theme.uwater_srv_nm));
                textview_saebu_yongdo.setText(StrUtil.hipen(theme.uwater_dtl_srv));
                textview_umyong_yebu.setText(StrUtil.hipen(theme.pota_yn));
                textview_gulchak_jikgyung.setText(StrUtil.hipen(theme.dig_diam) + " mm");
                textview_gaebal_day.setText(StrUtil.hipen(theme.dvop_ymd));
                textview_sulchi_simdo.setText(StrUtil.hipen(theme.dph) + " m");
                textview_chisu_plan_amount.setText(StrUtil.hipen(theme.frw_pln_qua) + " ㎥/일");
                textview_yangsu_nungryuk.setText(StrUtil.hipen(theme.rwt_cap) + " ㎥/일");
                textview_donryuk_jangchi_maryuk.setText(StrUtil.hipen(theme.pump_hrp) + " 마력");
                textview_tochulguan_jikgyung.setText(StrUtil.hipen(theme.pipe_diam) + " mm");
                textview_gonggong_sasul.setText(StrUtil.hipen(theme.pub_pri_gbn));
            }
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }
}
