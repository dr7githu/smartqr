package com.egov.smartqr.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.egov.smartqr.MainActivity;
import com.egov.smartqr.R;
import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.vo.DetailViewVO;
import com.egov.smartqr.vo.SearchInfo;
import com.egov.smartqr.vo.SearchVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class DetailInfoFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_check) Button btn_check;
    @BindView(R.id.textview_title) TextView textview_title;
    //---현장사진---
    @BindView(R.id.imageview_far_view) ImageView imageview_far_view;
    @BindView(R.id.imageview_near_view) ImageView imageview_near_view;
    @BindView(R.id.imageview_in_01) ImageView imageview_in_01;
    @BindView(R.id.imageview_in_02) ImageView imageview_in_02;
    @BindView(R.id.imageview_in_03) ImageView imageview_in_03;
    @BindView(R.id.imageview_in_04) ImageView imageview_in_04;
    @BindView(R.id.imageview_in_05) ImageView imageview_in_05;
    @BindView(R.id.imageview_in_06) ImageView imageview_in_06;
    //---위치현황---
    @BindView(R.id.textview_addr) TextView textview_addr;
    @BindView(R.id.textview_latitude) TextView textview_latitude;
    @BindView(R.id.textview_longitude) TextView textview_longitude;
    @BindView(R.id.textview_tm_x) TextView textview_tm_x;
    @BindView(R.id.textview_tm_y) TextView textview_tm_y;
    @BindView(R.id.textview_pyogo) TextView textview_pyogo;
    //---관리현황---
    @BindView(R.id.textview_herga_hyungtae) TextView textview_herga_hyungtae;
    @BindView(R.id.textview_herga_singo_num) TextView textview_herga_singo_num;
    @BindView(R.id.textview_illyun_num) TextView textview_illyun_num;
    @BindView(R.id.textview_gubun) TextView textview_gubun;
    @BindView(R.id.textview_guanjung_youngdo) TextView textview_guanjung_youngdo;
    @BindView(R.id.textview_saebu_yongdo) TextView textview_saebu_yongdo;
    //---시설현황---
    @BindView(R.id.textview_umyong_yebu) TextView textview_umyong_yebu;
    @BindView(R.id.textview_jungho_hyungtae) TextView textview_jungho_hyungtae;
    @BindView(R.id.textview_chungjuk_amban) TextView textview_chungjuk_amban;
    @BindView(R.id.textview_yangsu_nungryuk) TextView textview_yangsu_nungryuk;
    @BindView(R.id.textview_chisu_plan_amount) TextView textview_chisu_plan_amount;
    @BindView(R.id.textview_gaebal_year) TextView textview_gaebal_year;
    @BindView(R.id.textview_umul_gugyung) TextView textview_umul_gugyung;
    @BindView(R.id.textview_umul_simdo) TextView textview_umul_simdo;
    @BindView(R.id.textview_maryuk) TextView textview_maryuk;
    @BindView(R.id.textview_sulchi_simdo) TextView textview_sulchi_simdo;
    @BindView(R.id.textview_tochulguan) TextView textview_tochulguan;
    @BindView(R.id.textview_pump_volt) TextView textview_pump_volt;
    @BindView(R.id.textview_pump_junryu) TextView textview_pump_junryu;
    @BindView(R.id.textview_pump_jongryu) TextView textview_pump_jongryu;
    @BindView(R.id.textview_sigong_upche) TextView textview_sigong_upche;
    @BindView(R.id.textview_yunjang_herga_year) TextView textview_yunjang_herga_year;
    //---점검내역---
    @BindView(R.id.textview_jumgum_day) TextView textview_jumgum_day;
    @BindView(R.id.textview_josa_yebu) TextView textview_josa_yebu;
    @BindView(R.id.textview_jumgumja_sosok) TextView textview_jumgumja_sosok;
    @BindView(R.id.textview_jumgumja_myung) TextView textview_jumgumja_myung;
    @BindView(R.id.textview_josa_bulga_sayu) TextView textview_josa_bulga_sayu;
    @BindView(R.id.textview_chukjung_gugyung) TextView textview_chukjung_gugyung;
    @BindView(R.id.textview_chukjung_maryuk) TextView textview_chukjung_maryuk;
    @BindView(R.id.textview_chukjung_tochulguan) TextView textview_chukjung_tochulguan;
    @BindView(R.id.textview_tochulryang) TextView textview_tochulryang;
    @BindView(R.id.textview_junryukryang_jun) TextView textview_junryukryang_jun;
    @BindView(R.id.textview_junryukryang_hu) TextView textview_junryukryang_hu;
    @BindView(R.id.textview_yuryang_geryang_jun) TextView textview_yuryang_geryang_jun;
    @BindView(R.id.textview_yuryang_geryang_hu) TextView textview_yuryang_geryang_hu;
    @BindView(R.id.textview_yangsuryang_jukjung_yebu) TextView textview_yangsuryang_jukjung_yebu;
    @BindView(R.id.textview_imuljil_baechul_yebu) TextView textview_imuljil_baechul_yebu;
    @BindView(R.id.textview_gyunyul) TextView textview_gyunyul;
    @BindView(R.id.textview_nusu) TextView textview_nusu;
    @BindView(R.id.textview_chimha) TextView textview_chimha;
    @BindView(R.id.textview_dubgae_pason) TextView textview_dubgae_pason;
    @BindView(R.id.textview_oyum_yuib) TextView textview_oyum_yuib;
    @BindView(R.id.textview_dubgae_busik) TextView textview_dubgae_busik;
    @BindView(R.id.textview_yuryangge) TextView textview_yuryangge;
    @BindView(R.id.textview_chulsu_jangchi) TextView textview_chulsu_jangchi;
    @BindView(R.id.textview_suyi_chukjunggan) TextView textview_suyi_chukjunggan;
    @BindView(R.id.textview_suyi_pump_jakdong_yebu) TextView textview_suyi_pump_jakdong_yebu;
    @BindView(R.id.textview_iyongryang_zosa_il_1cha) TextView textview_iyongryang_zosa_il_1cha;
    @BindView(R.id.textview_iyongryang_1cha) TextView textview_iyongryang_1cha;
    @BindView(R.id.textview_yuryang_gesuchi_1cha) TextView textview_yuryang_gesuchi_1cha;
    @BindView(R.id.textview_junryuk_gesuchi_1cha) TextView textview_junryuk_gesuchi_1cha;
    @BindView(R.id.textview_iyongryang_zosa_il_2cha) TextView textview_iyongryang_zosa_il_2cha;
    @BindView(R.id.textview_iyongryang_2cha) TextView textview_iyongryang_2cha;
    @BindView(R.id.textview_yuryang_gesuchi_2cha) TextView textview_yuryang_gesuchi_2cha;
    @BindView(R.id.textview_junryuk_gesuchi_2cha) TextView textview_junryuk_gesuchi_2cha;
    @BindView(R.id.textview_iyongryang_zosa_il_3cha) TextView textview_iyongryang_zosa_il_3cha;
    @BindView(R.id.textview_iyongryang_3cha) TextView textview_iyongryang_3cha;
    @BindView(R.id.textview_yuryang_gesuchi_3cha) TextView textview_yuryang_gesuchi_3cha;
    @BindView(R.id.textview_junryuk_gesuchi_3cha) TextView textview_junryuk_gesuchi_3cha;
    @BindView(R.id.textview_iyongryang_zosa_il_4cha) TextView textview_iyongryang_zosa_il_4cha;
    @BindView(R.id.textview_iyongryang_4cha) TextView textview_iyongryang_4cha;
    @BindView(R.id.textview_yuryang_gesuchi_4cha) TextView textview_yuryang_gesuchi_4cha;
    @BindView(R.id.textview_junryuk_gesuchi_4cha) TextView textview_junryuk_gesuchi_4cha;
    @BindView(R.id.textview_gechukgi_jakdong_yebu) TextView textview_gechukgi_jakdong_yebu;
    @BindView(R.id.textview_sulchi_sangtae) TextView textview_sulchi_sangtae;
    @BindView(R.id.textview_siliyongryang_snajung) TextView textview_siliyongryang_snajung;
    @BindView(R.id.textview_dongjak_sangtae) TextView textview_dongjak_sangtae;
    @BindView(R.id.textview_pegong_yebu) TextView textview_pegong_yebu;
    @BindView(R.id.textview_suryang_bujok) TextView textview_suryang_bujok;
    @BindView(R.id.textview_sujil_akhwa) TextView textview_sujil_akhwa;
    @BindView(R.id.textview_sangsudo_daeche) TextView textview_sangsudo_daeche;
    @BindView(R.id.textview_tojil_hyungjil_change) TextView textview_tojil_hyungjil_change;
    @BindView(R.id.textview_soyuju_change) TextView textview_soyuju_change;
    @BindView(R.id.textview_yongdo_change) TextView textview_yongdo_change;
    @BindView(R.id.textview_sayong_jungji) TextView textview_sayong_jungji;
    @BindView(R.id.textview_yumbun_zngga) TextView textview_yumbun_zngga;
    @BindView(R.id.textview_bulyonggong_sangtae) TextView textview_bulyonggong_sangtae;
    @BindView(R.id.textview_pegong_balsaengil) TextView textview_pegong_balsaengil;
    @BindView(R.id.textview_pegong_cheriil) TextView textview_pegong_cheriil;
    @BindView(R.id.textview_gita_sayu) TextView textview_gita_sayu;
    //---사후관리---
    @BindView(R.id.textview_sahu_jumgum_day) TextView textview_sahu_jumgum_day;
    @BindView(R.id.textview_sahu_munjejum) TextView textview_sahu_munjejum;


    public static DetailInfoFragment newInstance() {
        return new DetailInfoFragment();
    }

    String stc, pnn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_info, container, false);
        ButterKnife.bind(this, view);

        btn_check.setOnClickListener(this);

        //Argument
        stc = getArguments().getString("stc");
        pnn = getArguments().getString("pnn");

        reqSelectJihasuDetailView(stc, pnn);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_check) {
            ((MainActivity)getActivity()).screenMove(DetailEditFragment.class, stc, pnn);
        }
    }

    //시설물 상세조회
    void reqSelectJihasuDetailView(String stc, String pnn) {
        claerData();
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
//                            Type listType = new TypeToken<ArrayList<SearchVO>>(){}.getType();
//                            ArrayList<SearchVO> arySearchVO = new Gson().fromJson(re.getResultData(), listType);
                            Type type = new TypeToken<DetailViewVO>(){}.getType();
                            DetailViewVO data = new Gson().fromJson(re.getResultData(), type);

                            //Process...
                            setData(data);
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=selectJihasuDetailView;stc=%s;pnn=%s;gid=", stc, pnn));
        lib.request(intent);
    }

    void setData(DetailViewVO data) {
        textview_title.setText(StrUtil.hipen(data.getPub_gbn()) + "물 관리 현황");

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

        String addr = "";
        if(StrUtil.notEmpty(data.sido)){
            addr += data.sido + " ";
        }
        if(StrUtil.notEmpty(data.sgg)){
            addr += data.sgg + " ";
        }
        if(StrUtil.notEmpty(data.umd)){
            addr += data.umd + " ";
        }
        if(StrUtil.notEmpty(data.ri)){
            addr += data.ri + " ";
        }
        if(StrUtil.notEmpty(data.jibun)){
            addr += data.jibun + " ";
        }
        if(StrUtil.notEmpty(data.real_jibun)){
            addr += "(실지번:" + data.real_jibun + ")";
        }
        textview_addr.setText(addr);

        textview_latitude.setText(StrUtil.hipen(data.lttd_dg) + "° " + StrUtil.hipen(data.lttd_mint) + "' " + StrUtil.hipen(data.lttd_sc) + '"');
        textview_longitude.setText(StrUtil.hipen(data.litd_dg) + "° " + StrUtil.hipen(data.litd_mint) + "' " + StrUtil.hipen(data.litd_sc) + '"');
        textview_tm_x.setText(StrUtil.hipen(data.tmx));
        textview_tm_y.setText(StrUtil.hipen(data.tmy));
        textview_pyogo.setText(StrUtil.hipen(data.elev));

        textview_herga_hyungtae.setText(StrUtil.hipen(data.perm_nt_form));
        textview_herga_singo_num.setText(StrUtil.hipen(data.perm_nt_no));
        textview_illyun_num.setText(StrUtil.hipen(data.sno));
        textview_gubun.setText(StrUtil.hipen(data.pub_gbn));
        textview_guanjung_youngdo.setText(StrUtil.hipen(data.uwater_srv));
        textview_saebu_yongdo.setText(StrUtil.hipen(data.uwater_dul_srv));

        textview_umyong_yebu.setText(StrUtil.hipen(data.pota_yn));
        textview_jungho_hyungtae.setText(StrUtil.hipen(data.jhho_form));
        textview_chungjuk_amban.setText(StrUtil.hipen(data.auv_bdr_gbn));
        if(StrUtil.notEmpty(data.rwt_cap)){ textview_yangsu_nungryuk.setText(data.rwt_cap + " (㎥/d)"); }
        else textview_yangsu_nungryuk.setText("-");
        if(StrUtil.notEmpty(data.frw_pln_qua)){ textview_chisu_plan_amount.setText(data.frw_pln_qua + " (㎥/d)"); }
        else textview_chisu_plan_amount.setText("-");
        if(StrUtil.notEmpty(data.dvop_year)){ textview_gaebal_year.setText(data.dvop_year); }
        else textview_gaebal_year.setText("-");
        if(StrUtil.notEmpty(data.dig_diam)){ textview_umul_gugyung.setText(data.dig_diam + " (mm)"); }
        else textview_umul_gugyung.setText("-");
        if(StrUtil.notEmpty(data.dig_dph)){ textview_umul_simdo.setText(data.dig_dph + " (mm)"); }
        else textview_umul_simdo.setText("-");
        if(StrUtil.notEmpty(data.pump_hrp)){ textview_maryuk.setText(data.pump_hrp + " (HP)"); }
        else textview_maryuk.setText("-");
        if(StrUtil.notEmpty(data.esb_dph)){ textview_sulchi_simdo.setText(data.esb_dph + " (m)"); }
        else textview_sulchi_simdo.setText("-");
        if(StrUtil.notEmpty(data.pipe_diam)){ textview_tochulguan.setText(data.pipe_diam + " (mm)"); }
        else textview_tochulguan.setText("-");
        if(StrUtil.notEmpty(data.pump_vol)){ textview_pump_volt.setText(data.pump_vol + " (V)"); }
        else textview_pump_volt.setText("-");
        if(StrUtil.notEmpty(data.pump_amp)){ textview_pump_junryu.setText(data.pump_amp + " (A)"); }
        else textview_pump_junryu.setText("-");
        textview_pump_jongryu.setText(StrUtil.hipen(data.pump_form));
        textview_sigong_upche.setText(StrUtil.hipen(data.op_upch_nm));
        textview_yunjang_herga_year.setText(StrUtil.hipen(data.ext_chg_year));

        textview_jumgum_day.setText(StrUtil.hipen(data.ins_date));
        textview_josa_yebu.setText(StrUtil.hipen(data.ins_yn));
        textview_jumgumja_sosok.setText(StrUtil.hipen(data.ins_org));
        textview_jumgumja_myung.setText(StrUtil.hipen(data.ins_nm));
        textview_josa_bulga_sayu.setText(StrUtil.hipen(data.im_ins_cau));
        if(StrUtil.notEmpty(data.dig_diam_r)){ textview_chukjung_gugyung.setText(data.dig_diam_r + " (mm)"); }
        else textview_chukjung_gugyung.setText("-");
        if(StrUtil.notEmpty(data.pump_hrp_r)){ textview_chukjung_maryuk.setText(data.pump_hrp_r + " (H)"); }
        else textview_chukjung_maryuk.setText("-");
        if(StrUtil.notEmpty(data.pipe_diam_r)){ textview_chukjung_tochulguan.setText(data.pipe_diam_r + " (mm)"); }
        else textview_chukjung_tochulguan.setText("-");
        if(StrUtil.notEmpty(data.pipe_out)){ textview_tochulryang.setText(data.pipe_out + "(㎥/d)"); }
        else textview_tochulryang.setText("-");
        if(StrUtil.notEmpty(data.wattmeter_bef)){ textview_junryukryang_jun.setText(data.wattmeter_bef + " (W)"); }
        else textview_junryukryang_jun.setText("-");
        if(StrUtil.notEmpty(data.wattmeter_aft)){ textview_junryukryang_hu.setText(data.wattmeter_aft + " (W)"); }
        else textview_junryukryang_hu.setText("-");
        if(StrUtil.notEmpty(data.flowmeter_bef)){ textview_yuryang_geryang_jun.setText(data.flowmeter_bef + " (㎥/d)"); }
        else textview_yuryang_geryang_jun.setText("-");
        if(StrUtil.notEmpty(data.flowmeter_aft)){ textview_yuryang_geryang_hu.setText(data.flowmeter_aft + " (㎥/d)"); }
        else textview_yuryang_geryang_hu.setText("-");
        textview_yangsuryang_jukjung_yebu.setText(StrUtil.hipen(data.rwt_pro_yn));
        textview_imuljil_baechul_yebu.setText(StrUtil.hipen(data.deb_dis_yn));
        textview_gyunyul.setText(StrUtil.hipen(data.crack_ctn));
        textview_nusu.setText(StrUtil.hipen(data.wat_leak_ctn));
        textview_chimha.setText(StrUtil.hipen(data.sink_ctn));
        textview_dubgae_pason.setText(StrUtil.hipen(data.cv_dmg_ctn));
        textview_oyum_yuib.setText(StrUtil.hipen(data.poll_in_ctn));
        textview_dubgae_busik.setText(StrUtil.hipen(data.cv_crs_ctn));
        textview_yuryangge.setText(StrUtil.hipen(data.ogg_ctn));
        textview_chulsu_jangchi.setText(StrUtil.hipen(data.chsu_eqn_ctn));
        textview_suyi_chukjunggan.setText(StrUtil.hipen(data.wtmskw_ctn));
        textview_suyi_pump_jakdong_yebu.setText(StrUtil.hipen(data.pump_opr_yn));
        textview_iyongryang_zosa_il_1cha.setText(StrUtil.hipen(data.use_ymd_1));
        textview_iyongryang_1cha.setText(StrUtil.hipen(data.use_qua_pro_1));
        textview_yuryang_gesuchi_1cha.setText(StrUtil.hipen(data.ogg_val_1));
        textview_junryuk_gesuchi_1cha.setText(StrUtil.hipen(data.elec_val_1));
        textview_iyongryang_zosa_il_2cha.setText(StrUtil.hipen(data.use_ymd_2));
        textview_iyongryang_2cha.setText(StrUtil.hipen(data.use_qua_pro_2));
        textview_yuryang_gesuchi_2cha.setText(StrUtil.hipen(data.ogg_val_2));
        textview_junryuk_gesuchi_2cha.setText(StrUtil.hipen(data.elec_val_2));
        textview_iyongryang_zosa_il_3cha.setText(StrUtil.hipen(data.use_ymd_3));
        textview_iyongryang_3cha.setText(StrUtil.hipen(data.use_qua_pro_3));
        textview_yuryang_gesuchi_3cha.setText(StrUtil.hipen(data.ogg_val_3));
        textview_junryuk_gesuchi_3cha.setText(StrUtil.hipen(data.elec_val_3));
        textview_iyongryang_zosa_il_4cha.setText(StrUtil.hipen(data.use_ymd_4));
        textview_iyongryang_4cha.setText(StrUtil.hipen(data.use_qua_pro_4));
        textview_yuryang_gesuchi_4cha.setText(StrUtil.hipen(data.ogg_val_4));
        textview_junryuk_gesuchi_4cha.setText(StrUtil.hipen(data.elec_val_4));
        textview_gechukgi_jakdong_yebu.setText(StrUtil.hipen(data.met_opr_rt));
        textview_sulchi_sangtae.setText(StrUtil.hipen(data.ins_state));
        textview_siliyongryang_snajung.setText(StrUtil.hipen(data.use_qua));
        textview_dongjak_sangtae.setText(StrUtil.hipen(data.mot_state));
        textview_pegong_yebu.setText(StrUtil.hipen(data.lnho_raise_yn));
        textview_suryang_bujok.setText(StrUtil.hipen(data.lnho_raise_lq_yn));
        textview_sujil_akhwa.setText(StrUtil.hipen(data.lnho_raise_wd_yn));
        textview_sangsudo_daeche.setText(StrUtil.hipen(data.lnho_raise_we_yn));
        textview_tojil_hyungjil_change.setText(StrUtil.hipen(data.lnho_raise_sc_yn));
        textview_soyuju_change.setText(StrUtil.hipen(data.lnho_raise_hc_yn));
        textview_yongdo_change.setText(StrUtil.hipen(data.lnho_raise_au_yn));
        textview_sayong_jungji.setText(StrUtil.hipen(data.lnho_raise_su_yn));
        textview_yumbun_zngga.setText(StrUtil.hipen(data.lnho_raise_is_yn));
        textview_bulyonggong_sangtae.setText(StrUtil.hipen(data.lnho_raise_status));
        textview_pegong_balsaengil.setText(StrUtil.hipen(data.lnho_raise_ymd));
        textview_pegong_cheriil.setText(StrUtil.hipen(data.lnho_deal_ymd));
        textview_gita_sayu.setText(StrUtil.hipen(data.lnho_raise_cau_other));
        textview_sahu_jumgum_day.setText(StrUtil.hipen(data.ins_date_follow));
        textview_sahu_munjejum.setText(StrUtil.hipen(data.ins_prob_1));
        //$('#sf_team_code').val(data.sf_team_code);//자치단체코드
    }

    void claerData() {
        imageview_far_view.setImageResource(R.drawable.no_image);
        imageview_near_view.setImageResource(R.drawable.no_image);
        imageview_in_01.setImageResource(R.drawable.no_image);
        imageview_in_02.setImageResource(R.drawable.no_image);
        imageview_in_03.setImageResource(R.drawable.no_image);
        imageview_in_04.setImageResource(R.drawable.no_image);
        imageview_in_05.setImageResource(R.drawable.no_image);
        imageview_in_06.setImageResource(R.drawable.no_image);

        textview_title.setText("-물 관리 현황");
        textview_addr.setText("");
        textview_latitude.setText("");
        textview_longitude.setText("");
        textview_tm_x.setText("");
        textview_tm_y.setText("");
        textview_pyogo.setText("");

        textview_herga_hyungtae.setText("");
        textview_herga_singo_num.setText("");
        textview_illyun_num.setText("");
        textview_gubun.setText("");
        textview_guanjung_youngdo.setText("");
        textview_saebu_yongdo.setText("");

        textview_umyong_yebu.setText("");
        textview_jungho_hyungtae.setText("");
        textview_chungjuk_amban.setText("");
        textview_yangsu_nungryuk.setText("");
        textview_chisu_plan_amount.setText("");
        textview_gaebal_year.setText("");
        textview_umul_gugyung.setText("");
        textview_umul_simdo.setText("");
        textview_maryuk.setText("");
        textview_sulchi_simdo.setText("");
        textview_tochulguan.setText("");
        textview_pump_volt.setText("");
        textview_pump_junryu.setText("");
        textview_pump_jongryu.setText("");
        textview_sigong_upche.setText("");
        textview_yunjang_herga_year.setText("");

        textview_jumgum_day.setText("");
        textview_josa_yebu.setText("");
        textview_jumgumja_sosok.setText("");
        textview_jumgumja_myung.setText("");
        textview_josa_bulga_sayu.setText("");
        textview_chukjung_gugyung.setText("");
        textview_chukjung_maryuk.setText("");
        textview_chukjung_tochulguan.setText("");
        textview_tochulryang.setText("");
        textview_junryukryang_jun.setText("");
        textview_junryukryang_hu.setText("");
        textview_yuryang_geryang_jun.setText("");
        textview_yuryang_geryang_hu.setText("");
        textview_yangsuryang_jukjung_yebu.setText("");
        textview_imuljil_baechul_yebu.setText("");
        textview_gyunyul.setText("");
        textview_nusu.setText("");
        textview_chimha.setText("");
        textview_dubgae_pason.setText("");
        textview_oyum_yuib.setText("");
        textview_dubgae_busik.setText("");
        textview_yuryangge.setText("");
        textview_chulsu_jangchi.setText("");
        textview_suyi_chukjunggan.setText("");
        textview_suyi_pump_jakdong_yebu.setText("");
        textview_iyongryang_zosa_il_1cha.setText("");
        textview_iyongryang_1cha.setText("");
        textview_yuryang_gesuchi_1cha.setText("");
        textview_junryuk_gesuchi_1cha.setText("");
        textview_iyongryang_zosa_il_2cha.setText("");
        textview_iyongryang_2cha.setText("");
        textview_yuryang_gesuchi_2cha.setText("");
        textview_junryuk_gesuchi_2cha.setText("");
        textview_iyongryang_zosa_il_3cha.setText("");
        textview_iyongryang_3cha.setText("");
        textview_yuryang_gesuchi_3cha.setText("");
        textview_junryuk_gesuchi_3cha.setText("");
        textview_iyongryang_zosa_il_4cha.setText("");
        textview_iyongryang_4cha.setText("");
        textview_yuryang_gesuchi_4cha.setText("");
        textview_junryuk_gesuchi_4cha.setText("");
        textview_gechukgi_jakdong_yebu.setText("");
        textview_sulchi_sangtae.setText("");
        textview_siliyongryang_snajung.setText("");
        textview_dongjak_sangtae.setText("");
        textview_pegong_yebu.setText("");
        textview_suryang_bujok.setText("");
        textview_sujil_akhwa.setText("");
        textview_sangsudo_daeche.setText("");
        textview_tojil_hyungjil_change.setText("");
        textview_soyuju_change.setText("");
        textview_yongdo_change.setText("");
        textview_sayong_jungji.setText("");
        textview_yumbun_zngga.setText("");
        textview_bulyonggong_sangtae.setText("");
        textview_pegong_balsaengil.setText("");
        textview_pegong_cheriil.setText("");
        textview_gita_sayu.setText("");
        textview_sahu_jumgum_day.setText("");
        textview_sahu_munjejum.setText("");
    }
}
