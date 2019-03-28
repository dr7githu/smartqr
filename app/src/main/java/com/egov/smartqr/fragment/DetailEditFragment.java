package com.egov.smartqr.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.egov.smartqr.MainActivity;
import com.egov.smartqr.R;
import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.BitmapUtil;
import com.egov.smartqr.util.KeyboardUtil;
import com.egov.smartqr.util.MyLocation;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.vo.DetailResultVO;
import com.egov.smartqr.vo.DetailViewVO;
import com.egov.smartqr.vo.EditSearchInfo;
import com.egov.smartqr.vo.QrVO;
import com.egov.smartqr.vo.SearchInfo;
import com.egov.smartqr.vo.SearchVO;
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

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class DetailEditFragment extends Fragment implements View.OnClickListener {

    final int RESULT_LOAD_IMG = 1005;

    //수정/신규에 따른 UI변화
    @BindView(R.id.textview_fragment_title) TextView textview_fragment_title;
    @BindView(R.id.linear_edit_only) LinearLayout linear_edit_only;
    @BindView(R.id.linear_new_only) LinearLayout linear_new_only;
    @BindView(R.id.textview_photo_title) TextView textview_photo_title;
    @BindView(R.id.textview_gps_cal) TextView textview_gps_cal;

    //New 일때 필수값 타이틀
    @BindView(R.id.textview_title_jachi_danche_code) TextView textview_title_jachi_danche_code;
    @BindView(R.id.textview_title_herga_singo_num_new) TextView textview_title_herga_singo_num_new;
    @BindView(R.id.textview_title_addr) TextView textview_title_addr;
    @BindView(R.id.textview_title_lat) TextView textview_title_lat;
    @BindView(R.id.textview_title_lon) TextView textview_title_lon;
    @BindView(R.id.textview_title_tmx) TextView textview_title_tmx;
    @BindView(R.id.textview_title_tmy) TextView textview_title_tmy;
    @BindView(R.id.textview_title_illyun_num) TextView textview_title_illyun_num;
    @BindView(R.id.textview_title_gubun) TextView textview_title_gubun;
    @BindView(R.id.textview_title_guanjung_youngdo) TextView textview_title_guanjung_youngdo;
    @BindView(R.id.textview_title_jumgum_day) TextView textview_title_jumgum_day;
    @BindView(R.id.textview_title_sahu_jumgum_day) TextView textview_title_sahu_jumgum_day;

    //for New
    @BindView(R.id.frame_jachi_danche_code) FrameLayout frame_jachi_danche_code;
    @BindView(R.id.textview_jachi_danche_code) TextView textview_jachi_danche_code;
    @BindView(R.id.edittext_herga_singo_num_new) EditText edittext_herga_singo_num_new;

    @BindView(R.id.btn_new) Button btn_new;
    @BindView(R.id.frame_sigungu) FrameLayout frame_sigungu;
    @BindView(R.id.textview_sigungu) TextView textview_sigungu;
    @BindView(R.id.edittext_num) EditText edittext_num;
    @BindView(R.id.btn_search) Button btn_search;
    @BindView(R.id.btn_qr) Button btn_qr;
    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.linear_no_result) LinearLayout linear_no_result;
    //---사진변경---
    @BindView(R.id.textview_far_view) TextView textview_far_view;
    @BindView(R.id.textview_near_view) TextView textview_near_view;
    @BindView(R.id.textview_in_01) TextView textview_in_01;
    @BindView(R.id.textview_in_02) TextView textview_in_02;
    @BindView(R.id.textview_in_03) TextView textview_in_03;
    @BindView(R.id.textview_in_04) TextView textview_in_04;
    @BindView(R.id.textview_in_05) TextView textview_in_05;
    @BindView(R.id.textview_in_06) TextView textview_in_06;
    @BindView(R.id.imageview_far_view) ImageView imageview_far_view;
    @BindView(R.id.imageview_near_view) ImageView imageview_near_view;
    @BindView(R.id.imageview_in_01) ImageView imageview_in_01;
    @BindView(R.id.imageview_in_02) ImageView imageview_in_02;
    @BindView(R.id.imageview_in_03) ImageView imageview_in_03;
    @BindView(R.id.imageview_in_04) ImageView imageview_in_04;
    @BindView(R.id.imageview_in_05) ImageView imageview_in_05;
    @BindView(R.id.imageview_in_06) ImageView imageview_in_06;
    //---위치현황---
    @BindView(R.id.edittext_sido) EditText edittext_sido;
    @BindView(R.id.edittext_sigungu) EditText edittext_sigungu;
    @BindView(R.id.edittext_upmyundong) EditText edittext_upmyundong;
    @BindView(R.id.edittext_ri) EditText edittext_ri;
    @BindView(R.id.edittext_jibun) EditText edittext_jibun;
    @BindView(R.id.edittext_real_jibun) EditText edittext_real_jibun;
    @BindView(R.id.edittext_lat1) EditText edittext_lat1;
    @BindView(R.id.edittext_lat2) EditText edittext_lat2;
    @BindView(R.id.edittext_lat3) EditText edittext_lat3;
    @BindView(R.id.edittext_lon1) EditText edittext_lon1;
    @BindView(R.id.edittext_lon2) EditText edittext_lon2;
    @BindView(R.id.edittext_lon3) EditText edittext_lon3;
    @BindView(R.id.edittext_tm_x) EditText edittext_tm_x;
    @BindView(R.id.edittext_tm_y) EditText edittext_tm_y;
    @BindView(R.id.edittext_pyogo) EditText edittext_pyogo;
    //---관리현황---
    @BindView(R.id.edittext_herga_hyungtae) EditText edittext_herga_hyungtae;
    @BindView(R.id.edittext_herga_singo_num) EditText edittext_herga_singo_num;
    @BindView(R.id.edittext_illyun_num) EditText edittext_illyun_num;
    @BindView(R.id.frame_gubun) FrameLayout frame_gubun;
     @BindView(R.id.textview_gubun) TextView textview_gubun;
    @BindView(R.id.frame_guanjung_youngdo) FrameLayout frame_guanjung_youngdo;
     @BindView(R.id.textview_guanjung_youngdo) TextView textview_guanjung_youngdo;
    @BindView(R.id.frame_saebu_yongdo) FrameLayout frame_saebu_yongdo;
     @BindView(R.id.textview_saebu_yongdo) TextView textview_saebu_yongdo;
    //---시설현황---
    @BindView(R.id.frame_umyong_yebu) FrameLayout frame_umyong_yebu;
     @BindView(R.id.textview_umyong_yebu) TextView textview_umyong_yebu;
    @BindView(R.id.frame_jungho_hyungtae) FrameLayout frame_jungho_hyungtae;
     @BindView(R.id.textview_jungho_hyungtae) TextView textview_jungho_hyungtae;
     @BindView(R.id.edittext_jungho_hyungtae) EditText edittext_jungho_hyungtae;
    @BindView(R.id.frame_chungjuk_amban) FrameLayout frame_chungjuk_amban;
     @BindView(R.id.textview_chungjuk_amban) TextView textview_chungjuk_amban;
    @BindView(R.id.frame_yangsu_nungryuk) FrameLayout frame_yangsu_nungryuk;
     @BindView(R.id.textview_yangsu_nungryuk) TextView textview_yangsu_nungryuk;
     @BindView(R.id.edittext_yangsu_nungryuk) EditText edittext_yangsu_nungryuk;
    @BindView(R.id.frame_chisu_plan_amount) FrameLayout frame_chisu_plan_amount;
     @BindView(R.id.textview_chisu_plan_amount) TextView textview_chisu_plan_amount;
     @BindView(R.id.edittext_chisu_plan_amount) EditText edittext_chisu_plan_amount;
    @BindView(R.id.edittext_gaebal_year) EditText edittext_gaebal_year;
    @BindView(R.id.frame_umul_gugyung) FrameLayout frame_umul_gugyung;
     @BindView(R.id.textview_umul_gugyung) TextView textview_umul_gugyung;
     @BindView(R.id.edittext_umul_gugyung) EditText edittext_umul_gugyung;
    @BindView(R.id.frame_umul_simdo) FrameLayout frame_umul_simdo;
     @BindView(R.id.textview_umul_simdo) TextView textview_umul_simdo;
     @BindView(R.id.edittext_umul_simdo) EditText edittext_umul_simdo;
    @BindView(R.id.frame_maryuk) FrameLayout frame_maryuk;
     @BindView(R.id.textview_maryuk) TextView textview_maryuk;
     @BindView(R.id.edittext_maryuk) EditText edittext_maryuk;
    @BindView(R.id.frame_sulchi_simdo) FrameLayout frame_sulchi_simdo;
     @BindView(R.id.textview_sulchi_simdo) TextView textview_sulchi_simdo;
     @BindView(R.id.edittext_sulchi_simdo) EditText edittext_sulchi_simdo;
    @BindView(R.id.frame_tochulguan) FrameLayout frame_tochulguan;
     @BindView(R.id.textview_tochulguan) TextView textview_tochulguan;
     @BindView(R.id.edittext_tochulguan) EditText edittext_tochulguan;
    @BindView(R.id.frame_pump_volt) FrameLayout frame_pump_volt;
     @BindView(R.id.textview_pump_volt) TextView textview_pump_volt;
     @BindView(R.id.edittext_pump_volt) EditText edittext_pump_volt;
    @BindView(R.id.frame_pump_junryu) FrameLayout frame_pump_junryu;
     @BindView(R.id.textview_pump_junryu) TextView textview_pump_junryu;
     @BindView(R.id.edittext_pump_junryu) EditText edittext_pump_junryu;
    @BindView(R.id.frame_pump_jongryu) FrameLayout frame_pump_jongryu;
     @BindView(R.id.textview_pump_jongryu) TextView textview_pump_jongryu;
    @BindView(R.id.edittext_sigong_upche) EditText edittext_sigong_upche;
    @BindView(R.id.edittext_yunjang_herga_year) EditText edittext_yunjang_herga_year;
    //---점검내역---
    @BindView(R.id.edittext_jumgum_day) EditText edittext_jumgum_day;
    @BindView(R.id.frame_josa_yebu) FrameLayout frame_josa_yebu;
     @BindView(R.id.textview_josa_yebu) TextView textview_josa_yebu;
    @BindView(R.id.edittext_jumgumja_sosok) EditText edittext_jumgumja_sosok;
    @BindView(R.id.edittext_jumgumja_myung) EditText edittext_jumgumja_myung;
    @BindView(R.id.edittext_josa_bulga_sayu) EditText edittext_josa_bulga_sayu;
    @BindView(R.id.frame_chukjung_gugyung) FrameLayout frame_chukjung_gugyung;
     @BindView(R.id.textview_chukjung_gugyung) TextView textview_chukjung_gugyung;
     @BindView(R.id.edittext_chukjung_gugyung) EditText edittext_chukjung_gugyung;
    @BindView(R.id.frame_chukjung_maryuk) FrameLayout frame_chukjung_maryuk;
     @BindView(R.id.textview_chukjung_maryuk) TextView textview_chukjung_maryuk;
     @BindView(R.id.edittext_chukjung_maryuk) EditText edittext_chukjung_maryuk;
    @BindView(R.id.frame_chukjung_tochulguan) FrameLayout frame_chukjung_tochulguan;
     @BindView(R.id.textview_chukjung_tochulguan) TextView textview_chukjung_tochulguan;
     @BindView(R.id.edittext_chukjung_tochulguan) EditText edittext_chukjung_tochulguan;
    @BindView(R.id.edittext_tochulryang) EditText edittext_tochulryang;
    @BindView(R.id.frame_junryukryang_jun) FrameLayout frame_junryukryang_jun;
     @BindView(R.id.textview_junryukryang_jun) TextView textview_junryukryang_jun;
     @BindView(R.id.edittext_junryukryang_jun) EditText edittext_junryukryang_jun;
    @BindView(R.id.frame_junryukryang_hu) FrameLayout frame_junryukryang_hu;
     @BindView(R.id.textview_junryukryang_hu) TextView textview_junryukryang_hu;
     @BindView(R.id.edittext_junryukryang_hu) EditText edittext_junryukryang_hu;
    @BindView(R.id.frame_yuryang_geryang_jun) FrameLayout frame_yuryang_geryang_jun;
     @BindView(R.id.textview_yuryang_geryang_jun) TextView textview_yuryang_geryang_jun;
     @BindView(R.id.edittext_yuryang_geryang_jun) EditText edittext_yuryang_geryang_jun;
    @BindView(R.id.frame_yuryang_geryang_hu) FrameLayout frame_yuryang_geryang_hu;
     @BindView(R.id.textview_yuryang_geryang_hu) TextView textview_yuryang_geryang_hu;
     @BindView(R.id.edittext_yuryang_geryang_hu) EditText edittext_yuryang_geryang_hu;
    @BindView(R.id.frame_yangsuryang_jukjung_yebu) FrameLayout frame_yangsuryang_jukjung_yebu;
     @BindView(R.id.textview_yangsuryang_jukjung_yebu) TextView textview_yangsuryang_jukjung_yebu;
    @BindView(R.id.frame_imuljil_baechul_yebu) FrameLayout frame_imuljil_baechul_yebu;
     @BindView(R.id.textview_imuljil_baechul_yebu) TextView textview_imuljil_baechul_yebu;
    @BindView(R.id.edittext_gyunyul) EditText edittext_gyunyul;
    @BindView(R.id.edittext_nusu) EditText edittext_nusu;
    @BindView(R.id.edittext_chimha) EditText edittext_chimha;
    @BindView(R.id.edittext_dubgae_pason) EditText edittext_dubgae_pason;
    @BindView(R.id.edittext_oyum_yuib) EditText edittext_oyum_yuib;
    @BindView(R.id.edittext_dubgae_busik) EditText edittext_dubgae_busik;
    @BindView(R.id.edittext_yuryangge) EditText edittext_yuryangge;
    @BindView(R.id.edittext_chulsu_jangchi) EditText edittext_chulsu_jangchi;
    @BindView(R.id.edittext_suyi_chukjunggan) EditText edittext_suyi_chukjunggan;
    @BindView(R.id.edittext_suyi_pump_jakdong_yebu) EditText edittext_suyi_pump_jakdong_yebu;
    @BindView(R.id.edittext_iyongryang_zosa_il_1cha) EditText edittext_iyongryang_zosa_il_1cha;
    @BindView(R.id.edittext_iyongryang_1cha) EditText edittext_iyongryang_1cha;
    @BindView(R.id.edittext_yuryang_gesuchi_1cha) EditText edittext_yuryang_gesuchi_1cha;
    @BindView(R.id.edittext_junryuk_gesuchi_1cha) EditText edittext_junryuk_gesuchi_1cha;
    @BindView(R.id.edittext_iyongryang_zosa_il_2cha) EditText edittext_iyongryang_zosa_il_2cha;
    @BindView(R.id.edittext_iyongryang_2cha) EditText edittext_iyongryang_2cha;
    @BindView(R.id.edittext_yuryang_gesuchi_2cha) EditText edittext_yuryang_gesuchi_2cha;
    @BindView(R.id.edittext_junryuk_gesuchi_2cha) EditText edittext_junryuk_gesuchi_2cha;
    @BindView(R.id.edittext_iyongryang_zosa_il_3cha) EditText edittext_iyongryang_zosa_il_3cha;
    @BindView(R.id.edittext_iyongryang_3cha) EditText edittext_iyongryang_3cha;
    @BindView(R.id.edittext_yuryang_gesuchi_3cha) EditText edittext_yuryang_gesuchi_3cha;
    @BindView(R.id.edittext_junryuk_gesuchi_3cha) EditText edittext_junryuk_gesuchi_3cha;
    @BindView(R.id.edittext_iyongryang_zosa_il_4cha) EditText edittext_iyongryang_zosa_il_4cha;
    @BindView(R.id.edittext_iyongryang_4cha) EditText edittext_iyongryang_4cha;
    @BindView(R.id.edittext_yuryang_gesuchi_4cha) EditText edittext_yuryang_gesuchi_4cha;
    @BindView(R.id.edittext_junryuk_gesuchi_4cha) EditText edittext_junryuk_gesuchi_4cha;
    @BindView(R.id.frame_gechukgi_jakdong_yebu) FrameLayout frame_gechukgi_jakdong_yebu;
     @BindView(R.id.textview_gechukgi_jakdong_yebu) TextView textview_gechukgi_jakdong_yebu;
     @BindView(R.id.edittext_gechukgi_jakdong_yebu) EditText edittext_gechukgi_jakdong_yebu;
    @BindView(R.id.edittext_sulchi_sangtae) EditText edittext_sulchi_sangtae;
    @BindView(R.id.edittext_siliyongryang_snajung) EditText edittext_siliyongryang_snajung;
    @BindView(R.id.edittext_dongjak_sangtae) EditText edittext_dongjak_sangtae;
    @BindView(R.id.edittext_pegong_yebu) EditText edittext_pegong_yebu;
    @BindView(R.id.edittext_suryang_bujok) EditText edittext_suryang_bujok;
    @BindView(R.id.edittext_sujil_akhwa) EditText edittext_sujil_akhwa;
    @BindView(R.id.edittext_sangsudo_daeche) EditText edittext_sangsudo_daeche;
    @BindView(R.id.edittext_tojil_hyungjil_change) EditText edittext_tojil_hyungjil_change;
    @BindView(R.id.edittext_soyuju_change) EditText edittext_soyuju_change;
    @BindView(R.id.edittext_yongdo_change) EditText edittext_yongdo_change;
    @BindView(R.id.edittext_sayong_jungji) EditText edittext_sayong_jungji;
    @BindView(R.id.edittext_yumbun_zngga) EditText edittext_yumbun_zngga;
    @BindView(R.id.frame_bulyonggong_sangtae) FrameLayout frame_bulyonggong_sangtae;
     @BindView(R.id.textview_bulyonggong_sangtae) TextView textview_bulyonggong_sangtae;
    @BindView(R.id.edittext_pegong_balsaengil) EditText edittext_pegong_balsaengil;
    @BindView(R.id.edittext_pegong_cheriil) EditText edittext_pegong_cheriil;
    @BindView(R.id.edittext_gita_sayu) EditText edittext_gita_sayu;
    //---사후관리---
    @BindView(R.id.edittext_sahu_jumgum_day) EditText edittext_sahu_jumgum_day;
    @BindView(R.id.edittext_sahu_munjejum) EditText edittext_sahu_munjejum;

    @BindView(R.id.btn_edit) Button btn_edit;


    ArrayList<SearchVO> arySearch;//검색결과
    DetailEditFragment.SearchRecyclerAdapter searchRecyclerAdapter = null;

    public static DetailEditFragment newInstance() {
        return new DetailEditFragment();
    }

    String sf_team_code = null, perm_nt_no = null;

    boolean isQr = false;

    int loadImageIndex = -1;

    //신규등록 모드
    public boolean isNewMode = false;

    //조회데이터
    DetailResultVO detailResult = null;

    String fileImg01 = null;
    String fileImg02 = null;
    String fileImg03 = null;
    String fileImg04 = null;
    String fileImg05 = null;
    String fileImg06 = null;
    String fileImg07 = null;
    String fileImg08 = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_edit, container, false);
        ButterKnife.bind(this, view);

        btn_new.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_qr.setOnClickListener(this);
        textview_far_view.setOnClickListener(this);
        textview_near_view.setOnClickListener(this);
        textview_in_01.setOnClickListener(this);
        textview_in_02.setOnClickListener(this);
        textview_in_03.setOnClickListener(this);
        textview_in_04.setOnClickListener(this);
        textview_in_05.setOnClickListener(this);
        textview_in_06.setOnClickListener(this);
        imageview_far_view.setOnClickListener(this);
        imageview_near_view.setOnClickListener(this);
        imageview_in_01.setOnClickListener(this);
        imageview_in_02.setOnClickListener(this);
        imageview_in_03.setOnClickListener(this);
        imageview_in_04.setOnClickListener(this);
        imageview_in_05.setOnClickListener(this);
        imageview_in_06.setOnClickListener(this);
        frame_sigungu.setOnClickListener(this);
        frame_gubun.setOnClickListener(this);
        frame_guanjung_youngdo.setOnClickListener(this);
        frame_saebu_yongdo.setOnClickListener(this);
        frame_umyong_yebu.setOnClickListener(this);
        frame_jungho_hyungtae.setOnClickListener(this);
        frame_chungjuk_amban.setOnClickListener(this);
        frame_yangsu_nungryuk.setOnClickListener(this);
        frame_chisu_plan_amount.setOnClickListener(this);
        frame_umul_gugyung.setOnClickListener(this);
        frame_umul_simdo.setOnClickListener(this);
        frame_maryuk.setOnClickListener(this);
        frame_sulchi_simdo.setOnClickListener(this);
        frame_tochulguan.setOnClickListener(this);
        frame_pump_volt.setOnClickListener(this);
        frame_pump_junryu.setOnClickListener(this);
        frame_pump_jongryu.setOnClickListener(this);
        frame_josa_yebu.setOnClickListener(this);
        frame_chukjung_gugyung.setOnClickListener(this);
        frame_chukjung_maryuk.setOnClickListener(this);
        frame_chukjung_tochulguan.setOnClickListener(this);
        frame_junryukryang_jun.setOnClickListener(this);
        frame_junryukryang_hu.setOnClickListener(this);
        frame_yuryang_geryang_jun.setOnClickListener(this);
        frame_yuryang_geryang_hu.setOnClickListener(this);
        frame_yangsuryang_jukjung_yebu.setOnClickListener(this);
        frame_imuljil_baechul_yebu.setOnClickListener(this);
        frame_gechukgi_jakdong_yebu.setOnClickListener(this);
        frame_bulyonggong_sangtae.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        frame_jachi_danche_code.setOnClickListener(this);
        textview_gps_cal.setOnClickListener(this);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyclerAdapter = new SearchRecyclerAdapter();
        recycler.setAdapter(searchRecyclerAdapter);

        //시설점검
        if(getArguments() != null && getArguments().size() >= 2) {
            sf_team_code = getArguments().getString("sf_team_code");
            perm_nt_no = getArguments().getString("perm_nt_no");

            //상단 리스트에 해당 관정 세팅시키고 상세정보 조회하게
            textview_sigungu.setText(Common.nameOfSfteamcode(sf_team_code));
            edittext_num.setText(perm_nt_no);

            //컨트럴 조정
            btn_new.setVisibility(View.GONE);

            clearData();
            isQr = true;
            onClick(btn_search);
        }
        //신규등록
        else {
            //컨트럴 조정
            btn_new.setVisibility(View.VISIBLE);

            clearData();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_new) {
            setNewMode(true);
        }
        else if(view == textview_far_view || view == imageview_far_view) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 0;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_near_view || view == imageview_near_view) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 1;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_in_01 || view == imageview_in_01) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 2;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_in_02 || view == imageview_in_02) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 3;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_in_03 || view == imageview_in_03) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 4;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_in_04 || view == imageview_in_04) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 5;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_in_05 || view == imageview_in_05) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 6;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_in_06 || view == imageview_in_06) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 7;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == btn_search) {
            String sel_type = "no";
            String search_text = edittext_num.getText().toString();
            String stc = Common.sfteamcodeOfName(textview_sigungu.getText().toString());
            String sgg = "";
            String umd = "";
            String ri = "";
            reqSelectSearchJihasuList(sel_type, search_text, stc, sgg, umd, ri);
        }
        else if(view == btn_qr) {
            NaviUtil.gotoQr(this);
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
                }
            });
            DroppyMenuPopup droppyMenu = droppyBuilder.build();
            droppyMenu.show();
        }
//        else if(view == frame_gubun) {
//            setDropList(view, Common.getAryPubGbn(), textview_gubun);
//        }
//        else if(view == frame_guanjung_youngdo) {
//            setDropList(view, Common.getAryUwaterSrv(), textview_guanjung_youngdo);
//        }
//        else if(view == frame_saebu_yongdo) {
//            setDropList(view, Common.getAryUwaterDulSrv(), textview_saebu_yongdo);
//        }
//        else if(view == frame_umyong_yebu) {
//            setDropList(view, Common.getAryPotaYn(), textview_umyong_yebu);
//        }
//        else if(view == frame_jungho_hyungtae) {
//            setDropList(view, Common.getAryJhhoForm(), textview_jungho_hyungtae, edittext_jungho_hyungtae);
//        }
//        else if(view == frame_chungjuk_amban) {
//            setDropList(view, Common.getAryAuvBdrGbn(), textview_chungjuk_amban);
//        }
//        else if(view == frame_yangsu_nungryuk) {
//            setDropList(view, Common.getAryRwtCap(), textview_yangsu_nungryuk, edittext_yangsu_nungryuk);
//        }
//        else if(view == frame_chisu_plan_amount) {
//            setDropList(view, Common.getAryFrwPlnQua(), textview_chisu_plan_amount, edittext_chisu_plan_amount);
//        }
//        else if(view == frame_umul_gugyung) {
//            setDropList(view, Common.getAryDigDiam(), textview_umul_gugyung, edittext_umul_gugyung);
//        }
//        else if(view == frame_umul_simdo) {
//            setDropList(view, Common.getAryDigDhp(), textview_umul_simdo, edittext_umul_simdo);
//        }
//        else if(view == frame_maryuk) {
//            setDropList(view, Common.getAryPumpHrp(), textview_maryuk, edittext_maryuk);
//        }
//        else if(view == frame_sulchi_simdo) {
//            setDropList(view, Common.getAryEsbDph(), textview_sulchi_simdo, edittext_sulchi_simdo);
//        }
//        else if(view == frame_tochulguan) {
//            setDropList(view, Common.getAryPipeDiam(), textview_tochulguan, edittext_tochulguan);
//        }
//        else if(view == frame_pump_volt) {
//            setDropList(view, Common.getAryPumpVol(), textview_pump_volt, edittext_pump_volt);
//        }
//        else if(view == frame_pump_junryu) {
//            setDropList(view, Common.getAryPumpAmp(), textview_pump_junryu, edittext_pump_junryu);
//        }
//        else if(view == frame_pump_jongryu) {
//            setDropList(view, Common.getAryPumpForm(), textview_pump_jongryu);
//        }
//        else if(view == frame_josa_yebu) {
//            setDropList(view, Common.getAryInsYn(), textview_josa_yebu);
//        }
//        else if(view == frame_chukjung_gugyung) {
//            setDropList(view, Common.getAryDigDiamR(), textview_chukjung_gugyung, edittext_chukjung_gugyung);
//        }
//        else if(view == frame_chukjung_maryuk) {
//            setDropList(view, Common.getAryPumpHrpR(), textview_chukjung_maryuk, edittext_chukjung_maryuk);
//        }
//        else if(view == frame_chukjung_tochulguan) {
//            setDropList(view, Common.getAryPipeDiamR(), textview_chukjung_tochulguan, edittext_chukjung_tochulguan);
//        }
//        else if(view == frame_junryukryang_jun) {
//            setDropList(view, Common.getAryMeter(), textview_junryukryang_jun, edittext_junryukryang_jun);
//        }
//        else if(view == frame_junryukryang_hu) {
//            setDropList(view, Common.getAryMeter(), textview_junryukryang_hu, edittext_junryukryang_hu);
//        }
//        else if(view == frame_yuryang_geryang_jun) {
//            setDropList(view, Common.getAryMeter(), textview_yuryang_geryang_jun, edittext_yuryang_geryang_jun);
//        }
//        else if(view == frame_yuryang_geryang_hu) {
//            setDropList(view, Common.getAryMeter(), textview_yuryang_geryang_hu, edittext_yuryang_geryang_hu);
//        }
//        else if(view == frame_yangsuryang_jukjung_yebu) {
//            setDropList(view, Common.getAryRwtProYn(), textview_yangsuryang_jukjung_yebu);
//        }
//        else if(view == frame_imuljil_baechul_yebu) {
//            setDropList(view, Common.getAryDebDisYn(), textview_imuljil_baechul_yebu);
//        }
//        else if(view == frame_gechukgi_jakdong_yebu) {
//            setDropList(view, Common.getAryMetOprRt(), textview_gechukgi_jakdong_yebu, edittext_gechukgi_jakdong_yebu);
//        }
//        else if(view == frame_bulyonggong_sangtae) {
//            setDropList(view, Common.getAryLnhoRaiseStatus(), textview_bulyonggong_sangtae);
//        }
        //for new
        else if(view == frame_jachi_danche_code) {
            //setDropList(view, Common.getAryLnhoRaiseStatus(), textview_jachi_danche_code);
            DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), frame_jachi_danche_code);
            for(int i=0; i< Common.getArySfTeamCode().size(); i++) {
                droppyBuilder.addMenuItem(new DroppyMenuItem(Common.getArySfTeamCode().get(i).sggNm)).addSeparator();
            }
            droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                @Override
                public void call(View v, int id) {
                    textview_jachi_danche_code.setText(Common.getArySfTeamCode().get(id).sggNm);

                    edittext_sido.setText(Common.getArySfTeamCode().get(id).sidoNm);
                    edittext_sigungu.setText(Common.getArySfTeamCode().get(id).sggNm);
                }
            });
            DroppyMenuPopup droppyMenu = droppyBuilder.build();
            droppyMenu.show();
        }
        else if(view == textview_gps_cal) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location){
                    UIUtil.hideProgress();
                    //Got the location!
                    try {
                        DecimalFormat dformat = new DecimalFormat("#.####");
                        double lon1 = Math.floor(location.getLongitude());
                        double lon2 = Math.floor(((location.getLongitude() - lon1)*1000000/1000000)*60);
                        double lon3 = (((((location.getLongitude() - lon1)*1000000/1000000)*60) - lon2)*60);
                        double lat1 = Math.floor(location.getLatitude());
                        double lat2 = Math.floor(((location.getLatitude() - lat1)*1000000/1000000)*60);
                        double lat3 = (((((location.getLatitude() - lat1)*1000000/1000000)*60) - lat2)*60);

                        edittext_lon1.setText(lon1+"");
                        edittext_lon2.setText(lon2+"");
                        edittext_lon3.setText(dformat.format(lon3));
                        edittext_lat1.setText(lat1+"");
                        edittext_lat2.setText(lat2+"");
                        edittext_lat3.setText(dformat.format(lat3));

                        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude());
                        edittext_tm_x.setText(dformat.format(mapPoint.getMapPointCONGCoord().x));
                        edittext_tm_y.setText(dformat.format(mapPoint.getMapPointCONGCoord().y));

                        edittext_pyogo.setText(dformat.format(location.getAltitude()));
                    }
                    catch(Exception x){
                        x.getMessage();
                    }
                }
            };
            if(!new MyLocation().getLocation(getContext(), locationResult)) {
                UIUtil.alert(getContext(), "GPS 상태를 확인해주세요.");
            } else {
                UIUtil.showProgress(getContext());
            }
        }
        else if(view == btn_edit) {
            if(!isNewMode) {
                if (StrUtil.isEmpty(sf_team_code) || StrUtil.isEmpty(perm_nt_no)) {
                    UIUtil.alert(getContext(), "선택한 지하수 시설이 없습니다.");
                    return;
                }
                reqInsertInspect(sf_team_code, perm_nt_no);
            } else {
                String stc = Common.sfteamcodeOfName(textview_jachi_danche_code.getText().toString());
                String pnn = edittext_herga_singo_num_new.getText().toString();
                reqInsertInspect(stc, pnn);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMG) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if(loadImageIndex == 0) {
                        imageview_far_view.setImageBitmap(selectedImage);
                        fileImg01 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 1) {
                        imageview_near_view.setImageBitmap(selectedImage);
                        fileImg02 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 2) {
                        imageview_in_01.setImageBitmap(selectedImage);
                        fileImg03 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 3) {
                        imageview_in_02.setImageBitmap(selectedImage);
                        fileImg04 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 4) {
                        imageview_in_03.setImageBitmap(selectedImage);
                        fileImg05 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 5) {
                        imageview_in_04.setImageBitmap(selectedImage);
                        fileImg06 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 6) {
                        imageview_in_05.setImageBitmap(selectedImage);
                        fileImg07 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 7) {
                        imageview_in_06.setImageBitmap(selectedImage);
                        fileImg08 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }
        else {
            // QR코드/ 바코드를 스캔한 결과
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String text = result.getContents();
            int find = text.indexOf("gid=");
            String gid = text.substring(find + 4, text.length());
            reqGetPermAndSf(gid);
        }
    }

    //DropList 셋
    void setDropList(View view, final ArrayList<Common.ValueText> ary, final TextView textview) {
        setDropList(view, ary, textview, null);
    }
    void setDropList(View view, final ArrayList<Common.ValueText> ary, final TextView textview, final EditText edittext) {
        if(edittext != null) textview.setText("직접입력");

        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), view);
        boolean exiseEqual = false;
        for(int i = 0; i< ary.size(); i++) {
            droppyBuilder.addMenuItem(new DroppyMenuItem(ary.get(i).text)).addSeparator();

            if(edittext != null && !exiseEqual) {
                if(StrUtil.exEqual(edittext.getText().toString(), ary.get(i).text)) {
                    exiseEqual = true;

                    textview.setText(ary.get(i).text);
                    edittext.setText(ary.get(i).text);
                    edittext.setVisibility(View.GONE);
                } else {
                    edittext.setVisibility(View.VISIBLE);
                }
            }
        }
        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                textview.setText(ary.get(id).text);
                if(edittext != null) {
                    edittext.setText(ary.get(id).value);

                    if(ary.get(id).text.endsWith("직접입력")) {
                        edittext.setVisibility(View.VISIBLE);
                    } else {
                        edittext.setVisibility(View.GONE);
                    }
                }
            }
        });
        DroppyMenuPopup droppyMenu = droppyBuilder.build();



        //droppyMenu.show();
    }

    //시설물 상세조회
    void reqSelectJihasuDetailResult(String sf_team_code, String perm_nt_no) {
        clearData();
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
                            Type type = new TypeToken<DetailResultVO>(){}.getType();
                            DetailResultVO data = new Gson().fromJson(re.getResultData(), type);

                            //Process...
                            setData(data);
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=selectJihasuDetailResult;sel_type=no;search_text=%s;sf_team_code=%s;sgg=;umd=;ri=", perm_nt_no, sf_team_code));
        lib.request(intent);
    }

    void setData(DetailResultVO data) {
        //기존데이터 저장
        detailResult = data;

        if(StrUtil.notEmpty(data.img_distant)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_distant;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_far_view);
        }
        if(StrUtil.notEmpty(data.img_short)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_short;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_near_view);
        }
        if(StrUtil.notEmpty(data.img_inside_1)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_inside_1;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_in_01);
        }
        if(StrUtil.notEmpty(data.img_inside_2)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_inside_2;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_in_02);
        }
        if(StrUtil.notEmpty(data.img_inside_3)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_inside_3;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_in_03);
        }
        if(StrUtil.notEmpty(data.img_inside_4)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_inside_4;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_in_04);
        }
        if(StrUtil.notEmpty(data.img_inside_5)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_inside_5;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_in_05);
        }
        if(StrUtil.notEmpty(data.img_inside_6)){
            String imgsrc = "http://smartgw.chungnam.go.kr/mobile/displayFile.do?filePath=" + data.img_path + "&fileName=" + data.img_inside_6;
            Glide.with(getContext()).load(imgsrc)
                    .error(R.drawable.no_image)
                    .into(imageview_in_06);
        }

        edittext_sido.setText(StrUtil.space(data.sido));
        edittext_sigungu.setText(StrUtil.space(data.sgg));
        edittext_upmyundong.setText(StrUtil.space(data.umd));
        edittext_ri.setText(StrUtil.space(data.ri));
        edittext_jibun.setText(StrUtil.space(data.jibun));
        edittext_real_jibun.setText(StrUtil.space(data.real_jibun));

        edittext_lat1.setText(StrUtil.d2s(data.lttd_dg));
        edittext_lat2.setText(StrUtil.d2s(data.lttd_mint));
        edittext_lat3.setText(StrUtil.d2s(data.lttd_sc));
        edittext_lon1.setText(StrUtil.d2s(data.litd_dg));
        edittext_lon2.setText(StrUtil.d2s(data.litd_mint));
        edittext_lon3.setText(StrUtil.d2s(data.litd_sc));
        edittext_tm_x.setText(StrUtil.d2s(data.tmx));
        edittext_tm_y.setText(StrUtil.d2s(data.tmy));
        edittext_pyogo.setText(StrUtil.d2s(data.elev));

        edittext_herga_hyungtae.setText(StrUtil.space(data.perm_nt_form));
        edittext_herga_singo_num.setText(StrUtil.space(data.perm_nt_no));
        edittext_illyun_num.setText(StrUtil.space(data.sno));
        textview_gubun.setText(StrUtil.space(data.pub_gbn));
        textview_guanjung_youngdo.setText(StrUtil.space(data.uwater_srv));
        textview_saebu_yongdo.setText(StrUtil.space(data.uwater_dul_srv));

        textview_umyong_yebu.setText(StrUtil.space(data.pota_yn));
        edittext_jungho_hyungtae.setText(StrUtil.space(data.jhho_form));
        textview_chungjuk_amban.setText(StrUtil.space(data.auv_bdr_gbn));
        edittext_yangsu_nungryuk.setText(StrUtil.d2s(data.rwt_cap));
        edittext_chisu_plan_amount.setText(StrUtil.d2s(data.frw_pln_qua));
        edittext_gaebal_year.setText(StrUtil.space(data.dvop_year));
        edittext_umul_gugyung.setText(StrUtil.d2s(data.dig_diam));
        edittext_umul_simdo.setText(StrUtil.d2s(data.dig_dph));
        edittext_maryuk.setText(StrUtil.d2s(data.pump_hrp));
        edittext_sulchi_simdo.setText(StrUtil.d2s(data.esb_dph));
        edittext_tochulguan.setText(StrUtil.d2s(data.pipe_diam));
        edittext_pump_volt.setText(StrUtil.d2s(data.pump_vol));
        edittext_pump_junryu.setText(StrUtil.d2s(data.pump_amp));
        textview_pump_jongryu.setText(StrUtil.space(data.pump_form));
        edittext_sigong_upche.setText(StrUtil.space(data.op_upch_nm));
        edittext_yunjang_herga_year.setText(StrUtil.space(data.ext_chg_year));

        edittext_jumgum_day.setText(StrUtil.space(data.ins_date));
        textview_josa_yebu.setText(StrUtil.space(data.ins_yn));
        edittext_jumgumja_sosok.setText(StrUtil.space(data.ins_org));
        edittext_jumgumja_myung.setText(StrUtil.space(data.ins_nm));
        edittext_josa_bulga_sayu.setText(StrUtil.space(data.im_ins_cau));
        edittext_chukjung_gugyung.setText(StrUtil.d2s(data.dig_diam_r));
        edittext_chukjung_maryuk.setText(StrUtil.d2s(data.pump_hrp_r));
        edittext_chukjung_tochulguan.setText(StrUtil.d2s(data.pipe_diam_r));
        edittext_tochulryang.setText(StrUtil.d2s(data.pipe_out));
        edittext_junryukryang_jun.setText(data.wattmeter_bef);
        edittext_junryukryang_hu.setText(data.wattmeter_aft);
        edittext_yuryang_geryang_jun.setText(data.flowmeter_bef);
        edittext_yuryang_geryang_hu.setText(data.flowmeter_aft);
        textview_yangsuryang_jukjung_yebu.setText(StrUtil.space(data.rwt_pro_yn));
        textview_imuljil_baechul_yebu.setText(StrUtil.space(data.deb_dis_yn));
        edittext_gyunyul.setText(StrUtil.space(data.crack_ctn));
        edittext_nusu.setText(StrUtil.space(data.wat_leak_ctn));
        edittext_chimha.setText(StrUtil.space(data.sink_ctn));
        edittext_dubgae_pason.setText(StrUtil.space(data.cv_dmg_ctn));
        edittext_oyum_yuib.setText(StrUtil.space(data.poll_in_ctn));
        edittext_dubgae_busik.setText(StrUtil.space(data.cv_crs_ctn));
        edittext_yuryangge.setText(StrUtil.space(data.ogg_ctn));
        edittext_chulsu_jangchi.setText(StrUtil.space(data.chsu_eqn_ctn));
        edittext_suyi_chukjunggan.setText(StrUtil.space(data.wtmskw_ctn));
        edittext_suyi_pump_jakdong_yebu.setText(StrUtil.space(data.pump_opr_yn));
        edittext_iyongryang_zosa_il_1cha.setText(StrUtil.space(data.use_ymd_1));
        edittext_iyongryang_1cha.setText(StrUtil.space(data.use_qua_pro_1));
        edittext_yuryang_gesuchi_1cha.setText(StrUtil.space(data.ogg_val_1));
        edittext_junryuk_gesuchi_1cha.setText(StrUtil.space(data.elec_val_1));
        edittext_iyongryang_zosa_il_2cha.setText(StrUtil.space(data.use_ymd_2));
        edittext_iyongryang_2cha.setText(StrUtil.space(data.use_qua_pro_2));
        edittext_yuryang_gesuchi_2cha.setText(StrUtil.space(data.ogg_val_2));
        edittext_junryuk_gesuchi_2cha.setText(StrUtil.space(data.elec_val_2));
        edittext_iyongryang_zosa_il_3cha.setText(StrUtil.space(data.use_ymd_3));
        edittext_iyongryang_3cha.setText(StrUtil.space(data.use_qua_pro_3));
        edittext_yuryang_gesuchi_3cha.setText(StrUtil.space(data.ogg_val_3));
        edittext_junryuk_gesuchi_3cha.setText(StrUtil.space(data.elec_val_3));
        edittext_iyongryang_zosa_il_4cha.setText(StrUtil.space(data.use_ymd_4));
        edittext_iyongryang_4cha.setText(StrUtil.space(data.use_qua_pro_4));
        edittext_yuryang_gesuchi_4cha.setText(StrUtil.space(data.ogg_val_4));
        edittext_junryuk_gesuchi_4cha.setText(StrUtil.space(data.elec_val_4));
        edittext_gechukgi_jakdong_yebu.setText(StrUtil.space(data.met_opr_rt));
        edittext_sulchi_sangtae.setText(StrUtil.space(data.ins_state));
        edittext_siliyongryang_snajung.setText(StrUtil.d2s(data.use_qua));
        edittext_dongjak_sangtae.setText(StrUtil.space(data.mot_state));
        edittext_pegong_yebu.setText(StrUtil.space(data.lnho_raise_yn));
        edittext_suryang_bujok.setText(StrUtil.space(data.lnho_raise_lq_yn));
        edittext_sujil_akhwa.setText(StrUtil.space(data.lnho_raise_wd_yn));
        edittext_sangsudo_daeche.setText(StrUtil.space(data.lnho_raise_we_yn));
        edittext_tojil_hyungjil_change.setText(StrUtil.space(data.lnho_raise_sc_yn));
        edittext_soyuju_change.setText(StrUtil.space(data.lnho_raise_hc_yn));
        edittext_yongdo_change.setText(StrUtil.space(data.lnho_raise_au_yn));
        edittext_sayong_jungji.setText(StrUtil.space(data.lnho_raise_su_yn));
        edittext_yumbun_zngga.setText(StrUtil.space(data.lnho_raise_is_yn));
        textview_bulyonggong_sangtae.setText(StrUtil.space(data.lnho_raise_status));
        edittext_pegong_balsaengil.setText(StrUtil.space(data.lnho_raise_ymd));
        edittext_pegong_cheriil.setText(StrUtil.space(data.lnho_deal_ymd));
        edittext_gita_sayu.setText(StrUtil.space(data.lnho_raise_cau_other));
        edittext_sahu_jumgum_day.setText(StrUtil.space(data.ins_date_follow));
        edittext_sahu_munjejum.setText(StrUtil.space(data.ins_prob_1));
        //$('#sf_team_code').val(data.sf_team_code);//자치단체코드

        setInitDropList();
    }

    void setInitDropList() {
        setDropList(frame_gubun, Common.getAryPubGbn(), textview_gubun);
        setDropList(frame_guanjung_youngdo, Common.getAryUwaterSrv(), textview_guanjung_youngdo);
        setDropList(frame_saebu_yongdo, Common.getAryUwaterDulSrv(), textview_saebu_yongdo);
        setDropList(frame_umyong_yebu, Common.getAryPotaYn(), textview_umyong_yebu);
        setDropList(frame_jungho_hyungtae, Common.getAryJhhoForm(), textview_jungho_hyungtae, edittext_jungho_hyungtae);
        setDropList(frame_chungjuk_amban, Common.getAryAuvBdrGbn(), textview_chungjuk_amban);
        setDropList(frame_yangsu_nungryuk, Common.getAryRwtCap(), textview_yangsu_nungryuk, edittext_yangsu_nungryuk);
        setDropList(frame_chisu_plan_amount, Common.getAryFrwPlnQua(), textview_chisu_plan_amount, edittext_chisu_plan_amount);
        setDropList(frame_umul_gugyung, Common.getAryDigDiam(), textview_umul_gugyung, edittext_umul_gugyung);
        setDropList(frame_umul_simdo, Common.getAryDigDhp(), textview_umul_simdo, edittext_umul_simdo);
        setDropList(frame_maryuk, Common.getAryPumpHrp(), textview_maryuk, edittext_maryuk);
        setDropList(frame_sulchi_simdo, Common.getAryEsbDph(), textview_sulchi_simdo, edittext_sulchi_simdo);
        setDropList(frame_tochulguan, Common.getAryPipeDiam(), textview_tochulguan, edittext_tochulguan);
        setDropList(frame_pump_volt, Common.getAryPumpVol(), textview_pump_volt, edittext_pump_volt);
        setDropList(frame_pump_junryu, Common.getAryPumpAmp(), textview_pump_junryu, edittext_pump_junryu);
        setDropList(frame_pump_jongryu, Common.getAryPumpForm(), textview_pump_jongryu);
        setDropList(frame_josa_yebu, Common.getAryInsYn(), textview_josa_yebu);
        setDropList(frame_chukjung_gugyung, Common.getAryDigDiamR(), textview_chukjung_gugyung, edittext_chukjung_gugyung);
        setDropList(frame_chukjung_maryuk, Common.getAryPumpHrpR(), textview_chukjung_maryuk, edittext_chukjung_maryuk);
        setDropList(frame_chukjung_tochulguan, Common.getAryPipeDiamR(), textview_chukjung_tochulguan, edittext_chukjung_tochulguan);
        setDropList(frame_junryukryang_jun, Common.getAryMeter(), textview_junryukryang_jun, edittext_junryukryang_jun);
        setDropList(frame_junryukryang_hu, Common.getAryMeter(), textview_junryukryang_hu, edittext_junryukryang_hu);
        setDropList(frame_yuryang_geryang_jun, Common.getAryMeter(), textview_yuryang_geryang_jun, edittext_yuryang_geryang_jun);
        setDropList(frame_yuryang_geryang_hu, Common.getAryMeter(), textview_yuryang_geryang_hu, edittext_yuryang_geryang_hu);
        setDropList(frame_yangsuryang_jukjung_yebu, Common.getAryRwtProYn(), textview_yangsuryang_jukjung_yebu);
        setDropList(frame_imuljil_baechul_yebu, Common.getAryDebDisYn(), textview_imuljil_baechul_yebu);
        setDropList(frame_gechukgi_jakdong_yebu, Common.getAryMetOprRt(), textview_gechukgi_jakdong_yebu, edittext_gechukgi_jakdong_yebu);
        setDropList(frame_bulyonggong_sangtae, Common.getAryLnhoRaiseStatus(), textview_bulyonggong_sangtae);
    }

    void clearData() {
        imageview_far_view.setImageResource(R.drawable.no_image);
        imageview_near_view.setImageResource(R.drawable.no_image);
        imageview_in_01.setImageResource(R.drawable.no_image);
        imageview_in_02.setImageResource(R.drawable.no_image);
        imageview_in_03.setImageResource(R.drawable.no_image);
        imageview_in_04.setImageResource(R.drawable.no_image);
        imageview_in_05.setImageResource(R.drawable.no_image);
        imageview_in_06.setImageResource(R.drawable.no_image);

        edittext_sido.setText("");
        edittext_sigungu.setText("");
        edittext_upmyundong.setText("");
        edittext_ri.setText("");
        edittext_jibun.setText("");
        edittext_real_jibun.setText("");

        edittext_lat1.setText("");
        edittext_lat2.setText("");
        edittext_lat3.setText("");
        edittext_lon1.setText("");
        edittext_lon2.setText("");
        edittext_lon3.setText("");
        edittext_tm_x.setText("");
        edittext_tm_y.setText("");
        edittext_pyogo.setText("");

        edittext_herga_hyungtae.setText("");
        edittext_herga_singo_num.setText("");
        edittext_illyun_num.setText("");
        textview_gubun.setText("");
        textview_guanjung_youngdo.setText("");
        textview_saebu_yongdo.setText("");

        textview_umyong_yebu.setText("");
        edittext_jungho_hyungtae.setText("");
        textview_chungjuk_amban.setText("");
        edittext_yangsu_nungryuk.setText("");
        edittext_chisu_plan_amount.setText("");
        edittext_gaebal_year.setText("");
        edittext_umul_gugyung.setText("");
        edittext_umul_simdo.setText("");
        edittext_maryuk.setText("");
        edittext_sulchi_simdo.setText("");
        edittext_tochulguan.setText("");
        edittext_pump_volt.setText("");
        edittext_pump_junryu.setText("");
        textview_pump_jongryu.setText("");
        edittext_sigong_upche.setText("");
        edittext_yunjang_herga_year.setText("");

        edittext_jumgum_day.setText("");
        textview_josa_yebu.setText("");
        edittext_jumgumja_sosok.setText("");
        edittext_jumgumja_myung.setText("");
        edittext_josa_bulga_sayu.setText("");
        edittext_chukjung_gugyung.setText("");
        edittext_chukjung_maryuk.setText("");
        edittext_chukjung_tochulguan.setText("");
        edittext_tochulryang.setText("");
        edittext_junryukryang_jun.setText("");
        edittext_junryukryang_hu.setText("");
        edittext_yuryang_geryang_jun.setText("");
        edittext_yuryang_geryang_hu.setText("");
        textview_yangsuryang_jukjung_yebu.setText("");
        textview_imuljil_baechul_yebu.setText("");
        edittext_gyunyul.setText("");
        edittext_nusu.setText("");
        edittext_chimha.setText("");
        edittext_dubgae_pason.setText("");
        edittext_oyum_yuib.setText("");
        edittext_dubgae_busik.setText("");
        edittext_yuryangge.setText("");
        edittext_chulsu_jangchi.setText("");
        edittext_suyi_chukjunggan.setText("");
        edittext_suyi_pump_jakdong_yebu.setText("");
        edittext_iyongryang_zosa_il_1cha.setText("");
        edittext_iyongryang_1cha.setText("");
        edittext_yuryang_gesuchi_1cha.setText("");
        edittext_junryuk_gesuchi_1cha.setText("");
        edittext_iyongryang_zosa_il_2cha.setText("");
        edittext_iyongryang_2cha.setText("");
        edittext_yuryang_gesuchi_2cha.setText("");
        edittext_junryuk_gesuchi_2cha.setText("");
        edittext_iyongryang_zosa_il_3cha.setText("");
        edittext_iyongryang_3cha.setText("");
        edittext_yuryang_gesuchi_3cha.setText("");
        edittext_junryuk_gesuchi_3cha.setText("");
        edittext_iyongryang_zosa_il_4cha.setText("");
        edittext_iyongryang_4cha.setText("");
        edittext_yuryang_gesuchi_4cha.setText("");
        edittext_junryuk_gesuchi_4cha.setText("");
        edittext_gechukgi_jakdong_yebu.setText("");
        edittext_sulchi_sangtae.setText("");
        edittext_siliyongryang_snajung.setText("");
        edittext_dongjak_sangtae.setText("");
        edittext_pegong_yebu.setText("");
        edittext_suryang_bujok.setText("");
        edittext_sujil_akhwa.setText("");
        edittext_sangsudo_daeche.setText("");
        edittext_tojil_hyungjil_change.setText("");
        edittext_soyuju_change.setText("");
        edittext_yongdo_change.setText("");
        edittext_sayong_jungji.setText("");
        edittext_yumbun_zngga.setText("");
        textview_bulyonggong_sangtae.setText("");
        edittext_pegong_balsaengil.setText("");
        edittext_pegong_cheriil.setText("");
        edittext_gita_sayu.setText("");
        edittext_sahu_jumgum_day.setText("");
        edittext_sahu_munjejum.setText("");
        //$('#sf_team_code').val(data.sf_team_code);//자치단체코드
    }

    //시설물 상세조회 수정/신규
    void reqInsertInspect(String sf_team_code, String perm_nt_no) {

        if(isNewMode) {
            if(StrUtil.isEmpty(edittext_herga_singo_num_new.getText().toString())){
                edittext_herga_singo_num_new.requestFocus();
                UIUtil.alert(getContext(), "허가신고번호는 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(fileImg01)){
                UIUtil.alert(getContext(), "원경 사진은 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(fileImg02)){
                UIUtil.alert(getContext(), "근경 사진은 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(edittext_lat1.getText().toString()) || StrUtil.isEmpty(edittext_lat2.getText().toString()) || StrUtil.isEmpty(edittext_lat3.getText().toString())){
                edittext_lat1.requestFocus();
                UIUtil.alert(getContext(), "위도는 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(edittext_lon1.getText().toString()) || StrUtil.isEmpty(edittext_lon2.getText().toString()) || StrUtil.isEmpty(edittext_lon3.getText().toString())){
                edittext_lon1.requestFocus();
                UIUtil.alert(getContext(), "경도는 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(edittext_illyun_num.getText().toString())){
                edittext_illyun_num.requestFocus();
                UIUtil.alert(getContext(), "일렬번호는 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(textview_gubun.getText().toString())){
                UIUtil.alert(getContext(), "구분은 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(textview_guanjung_youngdo.getText().toString())){
                UIUtil.alert(getContext(), "관정용도는 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(edittext_jumgum_day.getText().toString())){
                UIUtil.alert(getContext(), "점검내역의 점검일자는 필수입력 항목입니다.");
                return;
            }
            if(StrUtil.isEmpty(edittext_sahu_jumgum_day.getText().toString())){
                UIUtil.alert(getContext(), "사후관리의 점검일자는 필수입력 항목입니다.");
                return;
            }
        }

        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
                            UIUtil.hideProgress();
                            if(isNewMode) {
                                UIUtil.alert(getContext(), "신규 등록을 완료하였습니다.");
                            } else {
                                UIUtil.alert(getContext(), "점검내역 전송을 완료하였습니다.");
                            }
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });

        String p = "";

        if(isNewMode) {
            p += "method=insertInspectNew";
            p += ";sf_team_code=" + sf_team_code;
            p += ";search_text=" + perm_nt_no;
        } else {
            p += "method=insertInspect";
            p += ";sf_team_code=" + sf_team_code;
            p += ";search_text=" + perm_nt_no;
            p += ";perm_nt_no=" + perm_nt_no;
        }
        if(isNewMode) {
            p += ";sno=" + edittext_illyun_num.getText().toString();
        }
        int len = p.length();

        if(isNewMode) {
            if(detailResult == null) detailResult = new DetailResultVO();
        }
        if(!StrUtil.isEqual(detailResult.sido, edittext_sido.getText().toString()))
            p += ";sido=" + StrUtil.utf8(edittext_sido.getText().toString());
        if(!StrUtil.isEqual(detailResult.sgg, edittext_sigungu.getText().toString()))
            p += ";sgg=" + StrUtil.utf8(edittext_sigungu.getText().toString());
        if(!StrUtil.isEqual(detailResult.umd, edittext_upmyundong.getText().toString()))
            p += ";umd=" + StrUtil.utf8(edittext_upmyundong.getText().toString());
        if(!StrUtil.isEqual(detailResult.ri, edittext_ri.getText().toString()))
            p += ";ri=" + StrUtil.utf8(edittext_ri.getText().toString());
        if(!StrUtil.isEqual(detailResult.jibun, edittext_jibun.getText().toString()))
            p += ";jibun=" + edittext_jibun.getText().toString();
        if(!StrUtil.isEqual(detailResult.real_jibun, edittext_real_jibun.getText().toString()))
            p += ";real_jibun=" + edittext_real_jibun.getText().toString();
        if(!StrUtil.isEqual(detailResult.lttd_dg, edittext_lat1.getText().toString()))
            p += ";lttd_dg=" + edittext_lat1.getText().toString();
        if(!StrUtil.isEqual(detailResult.lttd_mint, edittext_lat2.getText().toString()))
            p += ";lttd_mint=" + edittext_lat2.getText().toString();
        if(!StrUtil.isEqual(detailResult.lttd_sc, edittext_lat3.getText().toString()))
            p += ";lttd_sc=" + edittext_lat3.getText().toString();
        if(!StrUtil.isEqual(detailResult.litd_dg, edittext_lon1.getText().toString()))
            p += ";litd_dg=" + edittext_lon1.getText().toString();
        if(!StrUtil.isEqual(detailResult.litd_mint, edittext_lon2.getText().toString()))
            p += ";litd_mint=" + edittext_lon2.getText().toString();
        if(!StrUtil.isEqual(detailResult.litd_sc, edittext_lon3.getText().toString()))
            p += ";litd_sc=" + edittext_lon3.getText().toString();
        if(!StrUtil.isEqual(detailResult.tmx, edittext_tm_x.getText().toString()))
            p += ";tmx=" + edittext_tm_x.getText().toString();
        if(!StrUtil.isEqual(detailResult.tmy, edittext_tm_y.getText().toString()))
            p += ";tmy=" + edittext_tm_y.getText().toString();
        if(!StrUtil.isEqual(detailResult.perm_nt_form, edittext_herga_hyungtae.getText().toString()))
            p += ";perm_nt_form=" + StrUtil.utf8(edittext_herga_hyungtae.getText().toString());
        if(!StrUtil.isEqual(detailResult.pub_gbn, textview_gubun.getText().toString()))
            p += ";pub_gbn=" + StrUtil.utf8(textview_gubun.getText().toString());
        if(!StrUtil.isEqual(detailResult.uwater_srv, textview_guanjung_youngdo.getText().toString()))
            p += ";uwater_srv=" + StrUtil.utf8(textview_guanjung_youngdo.getText().toString());
        if(!StrUtil.isEqual(detailResult.uwater_dul_srv, textview_saebu_yongdo.getText().toString()))
            p += ";uwater_dul_srv=" + StrUtil.utf8(textview_saebu_yongdo.getText().toString());
        if(!StrUtil.isEqual(detailResult.pota_yn, textview_umyong_yebu.getText().toString()))
            p += ";pota_yn=" + StrUtil.utf8(textview_umyong_yebu.getText().toString());
        if(!StrUtil.isEqual(detailResult.jhho_form, edittext_jungho_hyungtae.getText().toString()))
            p += ";jhho_form=" + StrUtil.utf8(edittext_jungho_hyungtae.getText().toString());
        if(!StrUtil.isEqual(detailResult.auv_bdr_gbn, textview_chungjuk_amban.getText().toString()))
            p += ";auv_bdr_gbn=" + StrUtil.utf8(textview_chungjuk_amban.getText().toString());
        if(!StrUtil.isEqual(detailResult.rwt_cap, edittext_yangsu_nungryuk.getText().toString()))
            p += ";rwt_cap=" + edittext_yangsu_nungryuk.getText().toString();
        if(!StrUtil.isEqual(detailResult.frw_pln_qua, edittext_chisu_plan_amount.getText().toString()))
            p += ";frw_pln_qua=" + edittext_chisu_plan_amount.getText().toString();
        if(!StrUtil.isEqual(detailResult.dvop_year, edittext_gaebal_year.getText().toString()))
            p += ";dvop_year=" + edittext_gaebal_year.getText().toString();
        if(!StrUtil.isEqual(detailResult.dig_diam, edittext_umul_gugyung.getText().toString()))
            p += ";dig_diam=" + edittext_umul_gugyung.getText().toString();
        if(!StrUtil.isEqual(detailResult.dig_dph, edittext_umul_simdo.getText().toString()))
            p += ";dig_dph=" + edittext_umul_simdo.getText().toString();
        if(!StrUtil.isEqual(detailResult.pump_hrp, edittext_maryuk.getText().toString()))
            p += ";pump_hrp=" + edittext_maryuk.getText().toString();
        if(!StrUtil.isEqual(detailResult.esb_dph, edittext_sulchi_simdo.getText().toString()))
            p += ";esb_dph=" + edittext_sulchi_simdo.getText().toString();
        if(!StrUtil.isEqual(detailResult.pipe_diam, edittext_tochulguan.getText().toString()))
            p += ";pipe_diam=" + edittext_tochulguan.getText().toString();
        if(!StrUtil.isEqual(detailResult.pump_vol, edittext_pump_volt.getText().toString()))
            p += ";pump_vol=" + edittext_pump_volt.getText().toString();
        if(!StrUtil.isEqual(detailResult.pump_amp, edittext_pump_junryu.getText().toString()))
            p += ";pump_amp=" + edittext_pump_junryu.getText().toString();
        if(!StrUtil.isEqual(detailResult.pump_form, textview_pump_jongryu.getText().toString()))
            p += ";pump_form=" + StrUtil.utf8(textview_pump_jongryu.getText().toString());
        if(!StrUtil.isEqual(detailResult.op_upch_nm, edittext_sigong_upche.getText().toString()))
            p += ";op_upch_nm=" + StrUtil.utf8(edittext_sigong_upche.getText().toString());
        if(!StrUtil.isEqual(detailResult.ext_chg_year, edittext_yunjang_herga_year.getText().toString()))
            p += ";ext_chg_year=" + edittext_yunjang_herga_year.getText().toString();
        if(!StrUtil.isEqual(detailResult.elev, edittext_pyogo.getText().toString()))
            p += ";elev=" + edittext_pyogo.getText().toString();//위치가 원래 위임
        if(!StrUtil.isEqual(detailResult.ins_date, edittext_jumgum_day.getText().toString()))
            p += ";ins_date=" + edittext_jumgum_day.getText().toString();
        if(!StrUtil.isEqual(detailResult.ins_yn, textview_josa_yebu.getText().toString()))
            p += ";ins_yn=" + StrUtil.utf8(textview_josa_yebu.getText().toString());
        if(!StrUtil.isEqual(detailResult.ins_org, edittext_jumgumja_sosok.getText().toString()))
            p += ";ins_org=" + StrUtil.utf8(edittext_jumgumja_sosok.getText().toString());
        if(!StrUtil.isEqual(detailResult.ins_nm, edittext_jumgumja_myung.getText().toString()))
            p += ";ins_nm=" + StrUtil.utf8(edittext_jumgumja_myung.getText().toString());
        if(!StrUtil.isEqual(detailResult.im_ins_cau, edittext_josa_bulga_sayu.getText().toString()))
            p += ";im_ins_cau=" + StrUtil.utf8(edittext_josa_bulga_sayu.getText().toString());
        if(!StrUtil.isEqual(detailResult.dig_diam_r, edittext_chukjung_gugyung.getText().toString()))
            p += ";dig_diam_r=" + edittext_chukjung_maryuk.getText().toString();
        if(!StrUtil.isEqual(detailResult.pump_hrp_r, edittext_chukjung_maryuk.getText().toString()))
            p += ";pump_hrp_r=" + edittext_chukjung_tochulguan.getText().toString();
        if(!StrUtil.isEqual(detailResult.pipe_diam_r, edittext_chukjung_tochulguan.getText().toString()))
            p += ";pipe_diam_r=" + edittext_chukjung_tochulguan.getText().toString();
        if(!StrUtil.isEqual(detailResult.pipe_out, edittext_tochulryang.getText().toString()))
            p += ";pipe_out=" + edittext_tochulryang.getText().toString();
        if(!StrUtil.isEqual(detailResult.wattmeter_bef, edittext_junryukryang_jun.getText().toString()))
            p += ";wattmeter_bef=" + edittext_junryukryang_jun.getText().toString();
        if(!StrUtil.isEqual(detailResult.wattmeter_aft, edittext_junryukryang_hu.getText().toString()))
            p += ";wattmeter_aft=" + edittext_junryukryang_hu.getText().toString();
        if(!StrUtil.isEqual(detailResult.flowmeter_bef, edittext_yuryang_geryang_jun.getText().toString()))
            p += ";flowmeter_bef=" + edittext_yuryang_geryang_jun.getText().toString();
        if(!StrUtil.isEqual(detailResult.flowmeter_aft, edittext_yuryang_geryang_hu.getText().toString()))
            p += ";flowmeter_aft=" + edittext_yuryang_geryang_hu.getText().toString();
        if(!StrUtil.isEqual(detailResult.rwt_pro_yn, textview_yangsuryang_jukjung_yebu.getText().toString()))
            p += ";rwt_pro_yn=" + StrUtil.utf8(textview_yangsuryang_jukjung_yebu.getText().toString());
        if(!StrUtil.isEqual(detailResult.deb_dis_yn, textview_imuljil_baechul_yebu.getText().toString()))
            p += ";deb_dis_yn=" + StrUtil.utf8(textview_imuljil_baechul_yebu.getText().toString());
        if(!StrUtil.isEqual(detailResult.crack_ctn, edittext_gyunyul.getText().toString()))
            p += ";crack_ctn=" + StrUtil.utf8(edittext_gyunyul.getText().toString());
        if(!StrUtil.isEqual(detailResult.wat_leak_ctn, edittext_nusu.getText().toString()))
            p += ";wat_leak_ctn=" + StrUtil.utf8(edittext_nusu.getText().toString());
        if(!StrUtil.isEqual(detailResult.sink_ctn, edittext_chimha.getText().toString()))
            p += ";sink_ctn=" + StrUtil.utf8(edittext_chimha.getText().toString());
        if(!StrUtil.isEqual(detailResult.cv_dmg_ctn, edittext_dubgae_pason.getText().toString()))
            p += ";cv_dmg_ctn=" + StrUtil.utf8(edittext_dubgae_pason.getText().toString());
        if(!StrUtil.isEqual(detailResult.poll_in_ctn, edittext_oyum_yuib.getText().toString()))
            p += ";poll_in_ctn=" + StrUtil.utf8(edittext_oyum_yuib.getText().toString());
        if(!StrUtil.isEqual(detailResult.cv_crs_ctn, edittext_dubgae_busik.getText().toString()))
            p += ";cv_crs_ctn=" + StrUtil.utf8(edittext_dubgae_busik.getText().toString());
        if(!StrUtil.isEqual(detailResult.ogg_ctn, edittext_yuryangge.getText().toString()))
            p += ";ogg_ctn=" + StrUtil.utf8(edittext_yuryangge.getText().toString());
        if(!StrUtil.isEqual(detailResult.chsu_eqn_ctn, edittext_chulsu_jangchi.getText().toString()))
            p += ";chsu_eqn_ctn=" + StrUtil.utf8(edittext_chulsu_jangchi.getText().toString());
        if(!StrUtil.isEqual(detailResult.wtmskw_ctn, edittext_suyi_chukjunggan.getText().toString()))
            p += ";wtmskw_ctn=" + StrUtil.utf8(edittext_suyi_chukjunggan.getText().toString());
        if(!StrUtil.isEqual(detailResult.pump_opr_yn, edittext_suyi_pump_jakdong_yebu.getText().toString()))
            p += ";pump_opr_yn=" + StrUtil.utf8(edittext_suyi_pump_jakdong_yebu.getText().toString());
        if(!StrUtil.isEqual(detailResult.use_ymd_1, edittext_iyongryang_zosa_il_1cha.getText().toString()))
            p += ";use_ymd_1=" + edittext_iyongryang_zosa_il_1cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_qua_pro_1, edittext_iyongryang_1cha.getText().toString()))
            p += ";use_qua_pro_1=" + StrUtil.utf8(edittext_iyongryang_1cha.getText().toString());
        if(!StrUtil.isEqual(detailResult.ogg_val_1, edittext_yuryang_gesuchi_1cha.getText().toString()))
            p += ";ogg_val_1=" + edittext_yuryang_gesuchi_1cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.elec_val_1, edittext_junryuk_gesuchi_1cha.getText().toString()))
            p += ";elec_val_1=" + edittext_junryuk_gesuchi_1cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_ymd_2, edittext_iyongryang_zosa_il_2cha.getText().toString()))
            p += ";use_ymd_2=" + edittext_iyongryang_zosa_il_2cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_qua_pro_2, edittext_iyongryang_2cha.getText().toString()))
            p += ";use_qua_pro_2=" + StrUtil.utf8(edittext_iyongryang_2cha.getText().toString());
        if(!StrUtil.isEqual(detailResult.ogg_val_2, edittext_yuryang_gesuchi_2cha.getText().toString()))
            p += ";ogg_val_2=" + edittext_yuryang_gesuchi_2cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.elec_val_2, edittext_junryuk_gesuchi_2cha.getText().toString()))
            p += ";elec_val_2=" + edittext_junryuk_gesuchi_2cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_ymd_3, edittext_iyongryang_zosa_il_3cha.getText().toString()))
            p += ";use_ymd_3=" + edittext_iyongryang_zosa_il_3cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_qua_pro_3, edittext_iyongryang_3cha.getText().toString()))
            p += ";use_qua_pro_3=" + StrUtil.utf8(edittext_iyongryang_3cha.getText().toString());
        if(!StrUtil.isEqual(detailResult.ogg_val_3, edittext_yuryang_gesuchi_3cha.getText().toString()))
            p += ";ogg_val_3=" + edittext_yuryang_gesuchi_3cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.elec_val_3, edittext_junryuk_gesuchi_3cha.getText().toString()))
            p += ";elec_val_3=" + edittext_junryuk_gesuchi_3cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_ymd_4, edittext_iyongryang_zosa_il_4cha.getText().toString()))
            p += ";use_ymd_4=" + edittext_iyongryang_zosa_il_4cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.use_qua_pro_4, edittext_iyongryang_4cha.getText().toString()))
            p += ";use_qua_pro_4=" + StrUtil.utf8(edittext_iyongryang_4cha.getText().toString());
        if(!StrUtil.isEqual(detailResult.ogg_val_4, edittext_yuryang_gesuchi_4cha.getText().toString()))
            p += ";ogg_val_4=" + edittext_yuryang_gesuchi_4cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.elec_val_4, edittext_junryuk_gesuchi_4cha.getText().toString()))
            p += ";elec_val_4=" + edittext_junryuk_gesuchi_4cha.getText().toString();
        if(!StrUtil.isEqual(detailResult.met_opr_rt, edittext_gechukgi_jakdong_yebu.getText().toString()))
            p += ";met_opr_rt=" + edittext_gechukgi_jakdong_yebu.getText().toString();
        if(!StrUtil.isEqual(detailResult.ins_state, edittext_sulchi_sangtae.getText().toString()))
            p += ";ins_state=" + StrUtil.utf8(edittext_sulchi_sangtae.getText().toString());
        if(!StrUtil.isEqual(detailResult.use_qua, edittext_siliyongryang_snajung.getText().toString()))
            p += ";use_qua=" + edittext_siliyongryang_snajung.getText().toString();
        if(!StrUtil.isEqual(detailResult.mot_state, edittext_dongjak_sangtae.getText().toString()))
            p += ";mot_state=" + StrUtil.utf8(edittext_dongjak_sangtae.getText().toString());
        if(!StrUtil.isEqual(detailResult.lnho_raise_yn, edittext_pegong_yebu.getText().toString()))
            p += ";lnho_raise_yn=" + edittext_pegong_yebu.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_lq_yn, edittext_suryang_bujok.getText().toString()))
            p += ";lnho_raise_lq_yn=" + edittext_suryang_bujok.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_wd_yn, edittext_sujil_akhwa.getText().toString()))
            p += ";lnho_raise_wd_yn=" + edittext_sujil_akhwa.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_we_yn, edittext_sangsudo_daeche.getText().toString()))
            p += ";lnho_raise_we_yn=" + edittext_sangsudo_daeche.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_sc_yn, edittext_tojil_hyungjil_change.getText().toString()))
            p += ";lnho_raise_sc_yn=" + edittext_tojil_hyungjil_change.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_hc_yn, edittext_soyuju_change.getText().toString()))
            p += ";lnho_raise_hc_yn=" + edittext_soyuju_change.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_au_yn, edittext_yongdo_change.getText().toString()))
            p += ";lnho_raise_au_yn=" + edittext_yongdo_change.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_su_yn, edittext_sayong_jungji.getText().toString()))
            p += ";lnho_raise_su_yn=" + edittext_sayong_jungji.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_is_yn, edittext_yumbun_zngga.getText().toString()))
            p += ";lnho_raise_is_yn=" + edittext_yumbun_zngga.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_status, textview_bulyonggong_sangtae.getText().toString()))
            p += ";lnho_raise_status=" + StrUtil.utf8(textview_bulyonggong_sangtae.getText().toString());
        if(!StrUtil.isEqual(detailResult.lnho_raise_ymd, edittext_pegong_balsaengil.getText().toString()))
            p += ";lnho_raise_ymd=" + edittext_pegong_balsaengil.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_deal_ymd, edittext_pegong_cheriil.getText().toString()))
            p += ";lnho_deal_ymd=" + edittext_pegong_cheriil.getText().toString();
        if(!StrUtil.isEqual(detailResult.lnho_raise_cau_other, edittext_gita_sayu.getText().toString()))
            p += ";lnho_raise_cau_other=" + StrUtil.utf8(edittext_gita_sayu.getText().toString());
        if(!StrUtil.isEqual(detailResult.ins_date_follow, edittext_sahu_jumgum_day.getText().toString()))
            p += ";ins_date_follow=" + edittext_sahu_jumgum_day.getText().toString();
        if(!StrUtil.isEqual(detailResult.ins_prob_1, edittext_sahu_munjejum.getText().toString()))
            p += ";ins_prob_1=" + StrUtil.utf8(edittext_sahu_munjejum.getText().toString());

        if(!StrUtil.isEmpty(fileImg01))
            p += ";fileImg01=" + fileImg01;
        if(!StrUtil.isEmpty(fileImg02))
            p += ";fileImg02=" + fileImg02;
        if(!StrUtil.isEmpty(fileImg03))
            p += ";fileImg03=" + fileImg03;
        if(!StrUtil.isEmpty(fileImg04))
            p += ";fileImg04=" + fileImg04;
        if(!StrUtil.isEmpty(fileImg05))
            p += ";fileImg05=" + fileImg05;
        if(!StrUtil.isEmpty(fileImg06))
            p += ";fileImg06=" + fileImg06;
        if(!StrUtil.isEmpty(fileImg07))
            p += ";fileImg07=" + fileImg07;
        if(!StrUtil.isEmpty(fileImg08))
            p += ";fileImg08=" + fileImg08;


        if(len == p.length()) {
            UIUtil.hideProgress();
            UIUtil.alert(getContext(), "수정된 항목이 없습니다.");
        } else {
            Intent intent = new Intent();
            intent.putExtra("sCode", Common.sCode);
            intent.putExtra("parameter", p);
            lib.request(intent);
        }
    }

    //시설물조회 - 검색
    void reqSelectSearchJihasuList(final String sel_type, final String search_text, final String stc, final String sgg, final String umd, final String ri) {
        // 중계서버 연계 API 객체 생성
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        if(!isQr) UIUtil.hideProgress();
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

                                if(isQr) {
                                    isQr = false;
                                    String stc = "", pnn = "";
                                    SearchVO search = arySearch.get(0);
                                    search.is_selected = true;
                                    if(search != null) {
                                        stc = search.sf_team_code;
                                        pnn = search.perm_nt_no;

                                        sf_team_code = stc;
                                        perm_nt_no = pnn;
                                    }
                                    reqSelectJihasuDetailResult(stc, pnn);
                                }

                                searchRecyclerAdapter.notifyDataSetChanged();
                                recycler.setScrollY(0);
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

                            textview_sigungu.setText(Common.nameOfSfteamcode(qr.sf_team_code));
                            edittext_num.setText(qr.perm_nt_no);

                            isQr = true;
                            onClick(btn_search);
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


            String stc = "", pnn = "";
            if(search != null) {
                stc = search.sf_team_code;
                pnn = search.perm_nt_no;

                sf_team_code = stc;
                perm_nt_no = pnn;
            }
            reqSelectJihasuDetailResult(stc, pnn);
        }
    };

    public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

        public SearchRecyclerAdapter(){
        }

        @Override
        public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_search_info, viewGroup,false);
            return new SearchRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder viewHolder, int position) {
            SearchVO search = arySearch.get(position);
            viewHolder.imagebtn_click.setTag(search);
            viewHolder.imagebtn_click.setOnClickListener(onClickList);

            viewHolder.linear_root.setSelected(search.is_selected);

            String addr = "";
            if(StrUtil.notEmpty(search.sido)){
                addr += search.sido + " ";
            }
            if(StrUtil.notEmpty(search.sgg)){
                addr += search.sgg + " ";
            }
            if(StrUtil.notEmpty(search.umd)){
                addr += search.umd + " ";
            }
            if(StrUtil.notEmpty(search.ri)){
                addr += search.ri + " ";
            }
            if(StrUtil.notEmpty(search.jibun)){
                addr += search.jibun + " ";
            }

            viewHolder.textview_num.setText(search.perm_nt_no);
            viewHolder.textview_addr.setText(addr);
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
            @BindView(R.id.textview_addr) TextView textview_addr;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public void setNewMode(boolean newMode) {
        isNewMode = newMode;

        if(newMode) {
            btn_new.setVisibility(View.GONE);
            linear_edit_only.setVisibility(View.GONE);
            linear_new_only.setVisibility(View.VISIBLE);
            textview_gps_cal.setVisibility(View.VISIBLE);
            edittext_sido.setEnabled(false);
            edittext_sigungu.setEnabled(false);
            edittext_herga_singo_num.setEnabled(false);
            edittext_illyun_num.setEnabled(true);

            textview_fragment_title.setText("신규 시설 등록");
            textview_photo_title.setText("사진 등록");
            edittext_herga_singo_num_new.setText("");
            textview_far_view.setText("원경 등록");
            textview_near_view.setText("근경 등록");
            textview_in_01.setText("내부1 등록");
            textview_in_02.setText("내부2 등록");
            textview_in_03.setText("내부3 등록");
            textview_in_04.setText("내부4 등록");
            textview_in_05.setText("내부5 등록");
            textview_in_06.setText("내부6 등록");
            btn_edit.setText("신규 등록");

            int color = 0xffff0000;
            int backColor = getResources().getColor(R.color.colorGray1);
            setTextViewColor(textview_title_jachi_danche_code, color, backColor);
            setTextViewColor(textview_title_herga_singo_num_new, color, backColor);
            setTextViewColor(textview_far_view, color, backColor);
            setTextViewColor(textview_near_view, color, backColor);
            setTextViewColor(textview_title_addr, color, backColor);
            setTextViewColor(textview_title_lat, color, backColor);
            setTextViewColor(textview_title_lon, color, backColor);
            setTextViewColor(textview_title_tmx, color, backColor);
            setTextViewColor(textview_title_tmy, color, backColor);
            setTextViewColor(textview_title_illyun_num, color, backColor);
            setTextViewColor(textview_title_gubun, color, backColor);
            setTextViewColor(textview_title_guanjung_youngdo, color, backColor);
            setTextViewColor(textview_title_jumgum_day, color, backColor);
            setTextViewColor(textview_title_sahu_jumgum_day, color, backColor);

            clearData();
            setInitDropList();

            edittext_sido.setText("충청남도");
            edittext_sigungu.setText("계룡시");
            textview_gubun.setText("공공지하수시설");
            textview_guanjung_youngdo.setText("생활용");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String getTime = sdf.format(new Date(System.currentTimeMillis()));
            edittext_jumgum_day.setText(getTime);
            edittext_sahu_jumgum_day.setText(getTime);

            edittext_herga_singo_num_new.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    edittext_herga_singo_num.setText(editable.toString());
                }
            });
        } else {
            btn_new.setVisibility(View.VISIBLE);
            linear_edit_only.setVisibility(View.VISIBLE);
            linear_new_only.setVisibility(View.GONE);
            textview_gps_cal.setVisibility(View.INVISIBLE);
            edittext_sido.setEnabled(true);
            edittext_sigungu.setEnabled(true);
            edittext_herga_singo_num.setEnabled(false);
            edittext_illyun_num.setEnabled(false);

            textview_fragment_title.setText("시설 점검");
            textview_photo_title.setText("사진 변경");
            textview_far_view.setText("원경 변경");
            textview_near_view.setText("근경 변경");
            textview_in_01.setText("내부1 변경");
            textview_in_02.setText("내부2 변경");
            textview_in_03.setText("내부3 변경");
            textview_in_04.setText("내부4 변경");
            textview_in_05.setText("내부5 변경");
            textview_in_06.setText("내부6 변경");
            btn_edit.setText("수정");

            int color = getResources().getColor(R.color.colorBlack3);
            int backColor = getResources().getColor(R.color.colorGray1);
            setTextViewColor(textview_title_jachi_danche_code, color, backColor);
            setTextViewColor(textview_title_herga_singo_num_new, color, backColor);
            setTextViewColor(textview_far_view, color, backColor);
            setTextViewColor(textview_near_view, color, backColor);
            setTextViewColor(textview_title_addr, color, backColor);
            setTextViewColor(textview_title_lat, color, backColor);
            setTextViewColor(textview_title_lon, color, backColor);
            setTextViewColor(textview_title_tmx, color, backColor);
            setTextViewColor(textview_title_tmy, color, backColor);
            setTextViewColor(textview_title_illyun_num, color, backColor);
            setTextViewColor(textview_title_gubun, color, backColor);
            setTextViewColor(textview_title_guanjung_youngdo, color, backColor);
            setTextViewColor(textview_title_jumgum_day, color, backColor);
            setTextViewColor(textview_title_sahu_jumgum_day, color, backColor);

            clearData();
        }
    }

    void setTextViewColor(TextView textview, int color, int backColor) {
        textview.setTextColor(color);
        //textview.setBackgroundColor(backColor);

        if(color == 0xffff0000) {
            String text = textview.getText().toString();
            if(text.indexOf("*") < 0) textview.setText(text + "*");
        } else {
            String text = textview.getText().toString();
            textview.setText(text.replace("*", ""));
        }
    }
}
