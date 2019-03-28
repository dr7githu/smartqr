package com.egov.smartqr.common;

import com.egov.smartqr.vo.MemberVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by onlytree on 2018. 1. 21..
 */

public class Common {

    //App전역사용
    public static MemberVO memberVO = null;


    public static String sCode = "MF_CN_UNDERGROUNDWATER";
    public static class SfTeamCode {
        public String sfteamcode, sidoNm, sggNm;
        public SfTeamCode(String sfteamcode, String sidoNm, String sggNm) {
            this.sfteamcode = sfteamcode;
            this.sidoNm = sidoNm;
            this.sggNm = sggNm;
        }
    }
    public static class ValueText {
        public String value, text;
        public ValueText(String value, String text) {
            this.value = value;
            this.text = text;
        }
    }
    static String sf_team_code = "[{\"sfteamcode\":\"5580000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"계룡시\"},\n" +
            "    {\"sfteamcode\":\"4500000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"공주시\"},\n" +
            "    {\"sfteamcode\":\"4550000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"금산군\"},\n" +
            "    {\"sfteamcode\":\"4540000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"논산시\"},\n" +
            "    {\"sfteamcode\":\"5680000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"당진시\"},\n" +
            "    {\"sfteamcode\":\"4510000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"보령시\"},\n" +
            "    {\"sfteamcode\":\"4570000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"부여군\"},\n" +
            "    {\"sfteamcode\":\"4530000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"서산시\"},\n" +
            "    {\"sfteamcode\":\"4580000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"서천군\"},\n" +
            "    {\"sfteamcode\":\"4520000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"아산시\"},\n" +
            "    {\"sfteamcode\":\"4610000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"예산군\"},\n" +
            "    {\"sfteamcode\":\"4490000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"천안시\"},\n" +
            "    {\"sfteamcode\":\"5650000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"천안시 동남구\"},\n" +
            "    {\"sfteamcode\":\"5660000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"천안시 서북구\"},\n" +
            "    {\"sfteamcode\":\"4590000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"청양군\"},\n" +
            "    {\"sfteamcode\":\"4620000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"태안군\"},\n" +
            "    {\"sfteamcode\":\"4600000\",\"sidoNm\":\"충청남도\",\"sggNm\":\"홍성군\"}]";
    static ArrayList<SfTeamCode> arySfTeamCode = null;
    public static ArrayList<SfTeamCode> getArySfTeamCode() {
        if(arySfTeamCode == null) {
            arySfTeamCode = new ArrayList<SfTeamCode>();
            try {
                Type listType = new TypeToken<ArrayList<SfTeamCode>>(){}.getType();
                arySfTeamCode = new Gson().fromJson(sf_team_code, listType);
//                JSONArray jarray = new JSONArray(sf_team_code);
//                for (int i = 0; i < jarray.length(); i++) {
//                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
//                    arySfTeamCode.add(new SfTeamCode(jObject.getString("sfteamcode"), jObject.getString("sidoNm"), jObject.getString("sggNm")));
//                }
            } catch (Exception e) {}
        }
        return arySfTeamCode;
    }
    public static String sfteamcodeOfName(String name) {
        for(int i=0; i<getArySfTeamCode().size(); i++) {
            if(getArySfTeamCode().get(i).sggNm.equals(name)) {
                return getArySfTeamCode().get(i).sfteamcode;
            }
        }
        return "";
    }
    public static String nameOfSfteamcode(String sfteamcode) {
        for(int i=0; i<getArySfTeamCode().size(); i++) {
            if(getArySfTeamCode().get(i).sfteamcode.equals(sfteamcode)) {
                return getArySfTeamCode().get(i).sggNm;
            }
        }
        return "";
    }

    static String rwt_cap = "[{\"value\":\"30\",\"text\":\"30\"},\n" +
            "    {\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"60\",\"text\":\"60\"},\n" +
            "    {\"value\":\"70\",\"text\":\"70\"},\n" +
            "    {\"value\":\"80\",\"text\":\"80\"},\n" +
            "    {\"value\":\"90\",\"text\":\"90\"},\n" +
            "    {\"value\":\"100\",\"text\":\"100\"},\n" +
            "    {\"value\":\"150\",\"text\":\"150\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}]";
    static ArrayList<ValueText> aryRwtCap = null;
    public static ArrayList<ValueText> getAryRwtCap() {
        if(aryRwtCap == null) {
            aryRwtCap = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(rwt_cap);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryRwtCap.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryRwtCap;
    }

    static String frw_pln_qua = "[{\"value\":\"10\",\"text\":\"10\"},\n" +
            "    {\"value\":\"20\",\"text\":\"20\"},\n" +
            "    {\"value\":\"30\",\"text\":\"30\"},\n" +
            "    {\"value\":\"40\",\"text\":\"40\"},\n" +
            "    {\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"60\",\"text\":\"60\"},\n" +
            "    {\"value\":\"70\",\"text\":\"70\"},\n" +
            "    {\"value\":\"80\",\"text\":\"80\"},\n" +
            "    {\"value\":\"90\",\"text\":\"90\"},\n" +
            "    {\"value\":\"100\",\"text\":\"100\"},\n" +
            "    {\"value\":\"150\",\"text\":\"150\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}]\n" +
            "\t ]";
    static ArrayList<ValueText> aryFrwPlnQua = null;
    public static ArrayList<ValueText> getAryFrwPlnQua() {
        if(aryFrwPlnQua == null) {
            aryFrwPlnQua = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(frw_pln_qua);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryFrwPlnQua.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryFrwPlnQua;
    }
    
    static String dig_diam = "[{\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"150\",\"text\":\"150\"},\n" +
            "    {\"value\":\"200\",\"text\":\"200\"},\n" +
            "    {\"value\":\"250\",\"text\":\"250\"},\n" +
            "    {\"value\":\"300\",\"text\":\"300\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}]";
    static ArrayList<ValueText> aryDigDiam = null;
    public static ArrayList<ValueText> getAryDigDiam() {
        if (aryDigDiam == null) {
            aryDigDiam = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(dig_diam);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryDigDiam.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {
            }
        }
        return aryDigDiam;
    }
    
    static String dig_dhp = "[{\"value\":\"30\",\"text\":\"30\"},\n" +
            "    {\"value\":\"40\",\"text\":\"40\"},\n" +
            "    {\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"60\",\"text\":\"60\"},\n" +
            "    {\"value\":\"70\",\"text\":\"70\"},\n" +
            "    {\"value\":\"80\",\"text\":\"80\"},\n" +
            "    {\"value\":\"90\",\"text\":\"90\"},\n" +
            "    {\"value\":\"100\",\"text\":\"100\"},\n" +
            "    {\"value\":\"110\",\"text\":\"110\"},\n" +
            "    {\"value\":\"120\",\"text\":\"120\"},\n" +
            "    {\"value\":\"130\",\"text\":\"130\"},\n" +
            "    {\"value\":\"150\",\"text\":\"150\"},\n" +
            "    {\"value\":\"200\",\"text\":\"200\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}]";
    static ArrayList<ValueText> aryDigDhp = null;
    public static ArrayList<ValueText> getAryDigDhp() {
        if (aryDigDhp == null) {
            aryDigDhp = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(dig_dhp);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryDigDhp.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {
            }
        }
        return aryDigDhp;
    }
    
    static String pump_hrp = "[{\"value\":\"0.3\",\"text\":\"0.3\"},\n" +
            "    {\"value\":\"0.5\",\"text\":\"0.5\"},\n" +
            "    {\"value\":\"1\",\"text\":\"1\"},\n" +
            "    {\"value\":\"2\",\"text\":\"2\"},\n" +
            "    {\"value\":\"3\",\"text\":\"3\"},\n" +
            "    {\"value\":\"5\",\"text\":\"5\"},\n" +
            "    {\"value\":\"7.5\",\"text\":\"7.5\"},\n" +
            "    {\"value\":\"10\",\"text\":\"10\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPumpHrp = null;
    public static ArrayList<ValueText> getAryPumpHrp() {
        if(aryPumpHrp == null) {
            aryPumpHrp = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pump_hrp);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPumpHrp.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPumpHrp;
    }
    
    static String esb_dph = "[{\"value\":\"30\",\"text\":\"30\"},\n" +
            "    {\"value\":\"40\",\"text\":\"40\"},\n" +
            "    {\"value\":\"45\",\"text\":\"45\"},\n" +
            "    {\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"54\",\"text\":\"54\"},\n" +
            "    {\"value\":\"60\",\"text\":\"60\"},\n" +
            "    {\"value\":\"66\",\"text\":\"66\"},\n" +
            "    {\"value\":\"70\",\"text\":\"70\"},\n" +
            "    {\"value\":\"72\",\"text\":\"72\"},\n" +
            "    {\"value\":\"80\",\"text\":\"80\"},\n" +
            "    {\"value\":\"90\",\"text\":\"90\"},\n" +
            "    {\"value\":\"100\",\"text\":\"100\"},\n" +
            "    {\"value\":\"120\",\"text\":\"120\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryEsbDph = null;
    public static ArrayList<ValueText> getAryEsbDph() {
        if(aryEsbDph == null) {
            aryEsbDph = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(esb_dph);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryEsbDph.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryEsbDph;
    }
    
    static String pipe_diam = "[{\"value\":\"15\",\"text\":\"15\"},\n" +
            "    {\"value\":\"16\",\"text\":\"16\"},\n" +
            "    {\"value\":\"20\",\"text\":\"20\"},\n" +
            "    {\"value\":\"25\",\"text\":\"25\"},\n" +
            "    {\"value\":\"30\",\"text\":\"30\"},\n" +
            "    {\"value\":\"32\",\"text\":\"32\"},\n" +
            "    {\"value\":\"40\",\"text\":\"40\"},\n" +
            "    {\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"65\",\"text\":\"65\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPipeDiam = null;
    public static ArrayList<ValueText> getAryPipeDiam() {
        if(aryPipeDiam == null) {
            aryPipeDiam = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pipe_diam);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPipeDiam.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPipeDiam;
    }
    
    static String pump_vol = "[{\"value\":\"210\",\"text\":\"210\"},\n" +
            "    {\"value\":\"220\",\"text\":\"220\"},\n" +
            "    {\"value\":\"230\",\"text\":\"230\"},\n" +
            "    {\"value\":\"380\",\"text\":\"380\"},\n" +
            "    {\"value\":\"400\",\"text\":\"400\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPumpVol = null;
    public static ArrayList<ValueText> getAryPumpVol() {
        if(aryPumpVol == null) {
            aryPumpVol = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pump_vol);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPumpVol.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPumpVol;
    }
    
    static String pump_amp = "[{\"value\":\"5\",\"text\":\"5\"},\n" +
            "    {\"value\":\"6\",\"text\":\"6\"},\n" +
            "    {\"value\":\"8\",\"text\":\"8\"},\n" +
            "    {\"value\":\"9\",\"text\":\"9\"},\n" +
            "    {\"value\":\"10\",\"text\":\"10\"},\n" +
            "    {\"value\":\"12\",\"text\":\"12\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPumpAmp = null;
    public static ArrayList<ValueText> getAryPumpAmp() {
        if(aryPumpAmp == null) {
            aryPumpAmp = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pump_amp);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPumpAmp.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPumpAmp;
    }
    
    static String dig_diam_r = "[{\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"100\",\"text\":\"100\"},\n" +
            "    {\"value\":\"150\",\"text\":\"150\"},\n" +
            "    {\"value\":\"200\",\"text\":\"200\"},\n" +
            "    {\"value\":\"250\",\"text\":\"250\"},\n" +
            "    {\"value\":\"300\",\"text\":\"300\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryDigDiamR = null;
    public static ArrayList<ValueText> getAryDigDiamR() {
        if(aryDigDiamR == null) {
            aryDigDiamR = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(dig_diam_r);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryDigDiamR.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryDigDiamR;
    }
    
    static String pump_hrp_r = "[{\"value\":\"0.3\",\"text\":\"0.3\"},\n" +
            "    {\"value\":\"0.5\",\"text\":\"0.5\"},\n" +
            "    {\"value\":\"1\",\"text\":\"1\"},\n" +
            "    {\"value\":\"1.3\",\"text\":\"1.3\"},\n" +
            "    {\"value\":\"1.5\",\"text\":\"1.5\"},\n" +
            "    {\"value\":\"2\",\"text\":\"2\"},\n" +
            "    {\"value\":\"3\",\"text\":\"3\"},\n" +
            "    {\"value\":\"5\",\"text\":\"5\"},\n" +
            "    {\"value\":\"7.5\",\"text\":\"7.5\"},\n" +
            "    {\"value\":\"10\",\"text\":\"10\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPumpHrpR = null;
    public static ArrayList<ValueText> getAryPumpHrpR() {
        if(aryPumpHrpR == null) {
            aryPumpHrpR = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pump_hrp_r);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPumpHrpR.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPumpHrpR;
    }
    
    static String pipe_diam_r = "[{\"value\":\"15\",\"text\":\"15\"},\n" +
            "    {\"value\":\"20\",\"text\":\"20\"},\n" +
            "    {\"value\":\"25\",\"text\":\"25\"},\n" +
            "    {\"value\":\"30\",\"text\":\"30\"},\n" +
            "    {\"value\":\"32\",\"text\":\"32\"},\n" +
            "    {\"value\":\"40\",\"text\":\"40\"},\n" +
            "    {\"value\":\"50\",\"text\":\"50\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPipeDiamR = null;
    public static ArrayList<ValueText> getAryPipeDiamR() {
        if(aryPipeDiamR == null) {
            aryPipeDiamR = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pipe_diam_r);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPipeDiamR.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPipeDiamR;
    }
    
    static String pub_gbn = "[{\"value\":\"공공지하수시설\",\"text\":\"공공지하수시설\"},\n" +
            "    {\"value\":\"대용량지하수시설\",\"text\":\"대용량지하수시설\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPubGbn = null;
    public static ArrayList<ValueText> getAryPubGbn() {
        if(aryPubGbn == null) {
            aryPubGbn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pub_gbn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPubGbn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPubGbn;
    }
    
    static String uwater_srv = "[{\"value\":\"생활용\",\"text\":\"생활용\"},\n" +
            "    {\"value\":\"공업용\",\"text\":\"공업용\"},\n" +
            "    {\"value\":\"농업용\",\"text\":\"농업용\"},\n" +
            "    {\"value\":\"기타용\",\"text\":\"기타용\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryUwaterSrv = null;
    public static ArrayList<ValueText> getAryUwaterSrv() {
        if(aryUwaterSrv == null) {
            aryUwaterSrv = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(uwater_srv);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryUwaterSrv.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryUwaterSrv;
    }
    
    static String uwater_dul_srv = "[{\"value\":\"가정용\",\"text\":\"가정용\"},\n" +
            "    {\"value\":\"일반용\",\"text\":\"일반용\"},\n" +
            "    {\"value\":\"학교용\",\"text\":\"학교용\"},\n" +
            "    {\"value\":\"민방위용\",\"text\":\"민방위용\"},\n" +
            "    {\"value\":\"국군용\",\"text\":\"국군용\"},\n" +
            "    {\"value\":\"공동주택용\",\"text\":\"공동주택용\"},\n" +
            "    {\"value\":\"간이상수도\",\"text\":\"간이상수도\"},\n" +
            "    {\"value\":\"상수도\",\"text\":\"상수도\"},\n" +
            "    {\"value\":\"농업/생활겸용\",\"text\":\"농업/생활겸용\"},\n" +
            "    {\"value\":\"국가공단\",\"text\":\"국가공단\"},\n" +
            "    {\"value\":\"지방공단\",\"text\":\"지방공단\"},\n" +
            "    {\"value\":\"농공단지\",\"text\":\"농공단지\"},\n" +
            "    {\"value\":\"자유입지업체\",\"text\":\"자유입지업체\"},\n" +
            "    {\"value\":\"전작용\",\"text\":\"전작용\"},\n" +
            "    {\"value\":\"답작용\",\"text\":\"답작용\"},\n" +
            "    {\"value\":\"원예용\",\"text\":\"원예용\"},\n" +
            "    {\"value\":\"수산업\",\"text\":\"수산업\"},\n" +
            "    {\"value\":\"축산업\",\"text\":\"축산업\"},\n" +
            "    {\"value\":\"양어장용\",\"text\":\"양어장용\"},\n" +
            "    {\"value\":\"온천수\",\"text\":\"온천수\"},\n" +
            "    {\"value\":\"먹는샘물\",\"text\":\"먹는샘물\"},\n" +
            "    {\"value\":\"기타\",\"text\":\"기타\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryUwaterDulSrv = null;
    public static ArrayList<ValueText> getAryUwaterDulSrv() {
        if(aryUwaterDulSrv == null) {
            aryUwaterDulSrv = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(uwater_dul_srv);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryUwaterDulSrv.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryUwaterDulSrv;
    }
    
    static String pota_yn = "[{\"value\":\"음용\",\"text\":\"음용\"},\n" +
            "    {\"value\":\"비음용\",\"text\":\"비음용\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPotaYn = null;
    public static ArrayList<ValueText> getAryPotaYn() {
        if(aryPotaYn == null) {
            aryPotaYn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pota_yn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPotaYn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPotaYn;
    }
    
    static String jhho_form = "[{\"value\":\"관정\",\"text\":\"관정\"},\n" +
            "    {\"value\":\"재래식우물\",\"text\":\"재래식우물\"},\n" +
            "    {\"value\":\"확인불가\",\"text\":\"확인불가\"}\n" +
            //"    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryJhhoForm = null;
    public static ArrayList<ValueText> getAryJhhoForm() {
        if(aryJhhoForm == null) {
            aryJhhoForm = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(jhho_form);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryJhhoForm.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryJhhoForm;
    }
    
    static String auv_bdr_gbn = "[{\"value\":\"암반\",\"text\":\"암반\"},\n" +
            "    {\"value\":\"충적\",\"text\":\"충적\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryAuvBdrGbn = null;
    public static ArrayList<ValueText> getAryAuvBdrGbn() {
        if(aryAuvBdrGbn == null) {
            aryAuvBdrGbn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(auv_bdr_gbn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryAuvBdrGbn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryAuvBdrGbn;
    }
    
    static String pump_form = "[{\"value\":\"수중\",\"text\":\"수중\"},\n" +
            "    {\"value\":\"와건\",\"text\":\"와건\"},\n" +
            "    {\"value\":\"수중/와건\",\"text\":\"수중/와건\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryPumpForm = null;
    public static ArrayList<ValueText> getAryPumpForm() {
        if(aryPumpForm == null) {
            aryPumpForm = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(pump_form);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryPumpForm.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryPumpForm;
    }
    
    static String jhho_gbn = "[{\"value\":\"관정\",\"text\":\"관정\"},\n" +
            "    {\"value\":\"정밀\",\"text\":\"정밀\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryJhhoGbn = null;
    public static ArrayList<ValueText> getAryJhhoGbn() {
        if(aryJhhoGbn == null) {
            aryJhhoGbn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(jhho_gbn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryJhhoGbn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryJhhoGbn;
    }
    
    static String ins_yn = "[{\"value\":\"실공\",\"text\":\"실공\"},\n" +
            "    {\"value\":\"허공\",\"text\":\"허공\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryInsYn = null;
    public static ArrayList<ValueText> getAryInsYn() {
        if(aryInsYn == null) {
            aryInsYn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(ins_yn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryInsYn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryInsYn;
    }
    
    static String meter = "[{\"value\":\"확인불가\",\"text\":\"확인불가\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryMeter = null;
    public static ArrayList<ValueText> getAryMeter() {
        if(aryMeter == null) {
            aryMeter = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(meter);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryMeter.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryMeter;
    }
    
    static String rwt_pro_yn = "[{\"value\":\"양호\",\"text\":\"양호\"},\n" +
            "    {\"value\":\"부적정\",\"text\":\"부적정\"},\n" +
            "    {\"value\":\"확인불가\",\"text\":\"확인불가\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryRwtProYn = null;
    public static ArrayList<ValueText> getAryRwtProYn() {
        if(aryRwtProYn == null) {
            aryRwtProYn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(rwt_pro_yn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryRwtProYn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryRwtProYn;
    }
    
    static String deb_dis_yn = "[{\"value\":\"양호\",\"text\":\"양호\"},\n" +
            "    {\"value\":\"불량\",\"text\":\"불량\"},\n" +
            "    {\"value\":\"확인불가\",\"text\":\"확인불가\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryDebDisYn = null;
    public static ArrayList<ValueText> getAryDebDisYn() {
        if(aryDebDisYn == null) {
            aryDebDisYn = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(deb_dis_yn);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryDebDisYn.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryDebDisYn;
    }
    
    static String met_opr_rt = "[{\"value\":\"적정\",\"text\":\"적정\"},\n" +
            "    {\"value\":\"미작동\",\"text\":\"미작동\"},\n" +
            "    {\"value\":\"확인불가\",\"text\":\"확인불가\"},\n" +
            "    {\"value\":\"\",\"text\":\"직접입력\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryMetOprRt = null;
    public static ArrayList<ValueText> getAryMetOprRt() {
        if(aryMetOprRt == null) {
            aryMetOprRt = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(met_opr_rt);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryMetOprRt.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryMetOprRt;
    }
    
    static String lnho_raise_status = "[{\"value\":\"미조치\",\"text\":\"미조치\"},\n" +
            "    {\"value\":\"조치중\",\"text\":\"조치중\"},\n" +
            "    {\"value\":\"원상복구\",\"text\":\"원상복구\"},\n" +
            "    {\"value\":\"재이용\",\"text\":\"재이용\"}\n" +
            "\t ]";
    static ArrayList<ValueText> aryLnhoRaiseStatus = null;
    public static ArrayList<ValueText> getAryLnhoRaiseStatus() {
        if(aryLnhoRaiseStatus == null) {
            aryLnhoRaiseStatus = new ArrayList<ValueText>();
            try {
                JSONArray jarray = new JSONArray(lnho_raise_status);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    aryLnhoRaiseStatus.add(new ValueText(jObject.getString("value"), jObject.getString("text")));
                }
            } catch (Exception e) {}
        }
        return aryLnhoRaiseStatus;
    }
}
