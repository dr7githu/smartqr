/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.egov.smartqr.vo;


/**
 * @Class Name : SampleVO.java
 * @Description : SampleVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class ThemeVO{
	
	public String sf_team_code;
	public String perm_nt_no;
	public String type;
	public String sido; 
	public String sgg; 
	public String umd; 
	public String ri; 
	public String bunji; 
	public String ho; 
	public String lttd_dg;
	public String lttd_mint;
	public String lttd_sc;
	public String litd_dg;
	public String litd_mint;
	public String litd_sc;
	public String perm_nt_form; 	//관정형태
	public String uwater_srv_nm; 	//용도
	public String uwater_dtl_srv; 	//세부용도
	public String pota_yn; 	//음용여부
	public String dig_diam; 	//굴착직경 (mm)
	public String dvop_ymd; 	//개발일자
	public String dph; 		//설치심도 (m)
	public String frw_pln_qua; //취수계획량 (㎥/일)
	public String rwt_cap;		//양수능력(㎥/일) 
	public String pump_hrp; 	//동력장치마력 (마력)
	public String pipe_diam; 	//토출관 직경 (mm)
	public String pub_pri_gbn;	//공공/사설 여부 
	public String obsv_name;	//관측소명 
	public String mgr_org;		//관리기관 
	public String pyogo;		//표고 
	public String insdate;		//설치일자 
	public String guldep;		//굴착심도 
	public String guldia;		//굴착구경 
	public String dcode;		//징겨코드 
	public String tmx;			//tmx 좌표 
	public String tmy;			//tmy 좌표
	
	
	public String getSf_team_code() {
		return sf_team_code;
	}
	public void setSf_team_code(String sf_team_code) {
		this.sf_team_code = sf_team_code;
	}
	public String getPerm_nt_no() {
		return perm_nt_no;
	}
	public void setPerm_nt_no(String perm_nt_no) {
		this.perm_nt_no = perm_nt_no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSgg() {
		return sgg;
	}
	public void setSgg(String sgg) {
		this.sgg = sgg;
	}
	public String getUmd() {
		return umd;
	}
	public void setUmd(String umd) {
		this.umd = umd;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	}
	public String getBunji() {
		return bunji;
	}
	public void setBunji(String bunji) {
		this.bunji = bunji;
	}
	public String getHo() {
		return ho;
	}
	public void setHo(String ho) {
		this.ho = ho;
	}
	public String getLttd_dg() {
		return lttd_dg;
	}
	public void setLttd_dg(String lttd_dg) {
		this.lttd_dg = lttd_dg;
	}
	public String getLttd_mint() {
		return lttd_mint;
	}
	public void setLttd_mint(String lttd_mint) {
		this.lttd_mint = lttd_mint;
	}
	public String getLttd_sc() {
		return lttd_sc;
	}
	public void setLttd_sc(String lttd_sc) {
		this.lttd_sc = lttd_sc;
	}
	public String getLitd_dg() {
		return litd_dg;
	}
	public void setLitd_dg(String litd_dg) {
		this.litd_dg = litd_dg;
	}
	public String getLitd_mint() {
		return litd_mint;
	}
	public void setLitd_mint(String litd_mint) {
		this.litd_mint = litd_mint;
	}
	public String getLitd_sc() {
		return litd_sc;
	}
	public void setLitd_sc(String litd_sc) {
		this.litd_sc = litd_sc;
	}
	public String getPerm_nt_form() {
		return perm_nt_form;
	}
	public void setPerm_nt_form(String perm_nt_form) {
		this.perm_nt_form = perm_nt_form;
	}
	public String getUwater_srv_nm() {
		return uwater_srv_nm;
	}
	public void setUwater_srv_nm(String uwater_srv_nm) {
		this.uwater_srv_nm = uwater_srv_nm;
	}
	public String getUwater_dtl_srv() {
		return uwater_dtl_srv;
	}
	public void setUwater_dtl_srv(String uwater_dtl_srv) {
		this.uwater_dtl_srv = uwater_dtl_srv;
	}
	public String getPota_yn() {
		return pota_yn;
	}
	public void setPota_yn(String pota_yn) {
		this.pota_yn = pota_yn;
	}
	public String getDig_diam() {
		return dig_diam;
	}
	public void setDig_diam(String dig_diam) {
		this.dig_diam = dig_diam;
	}
	public String getDvop_ymd() {
		return dvop_ymd;
	}
	public void setDvop_ymd(String dvop_ymd) {
		this.dvop_ymd = dvop_ymd;
	}
	public String getDph() {
		return dph;
	}
	public void setDph(String dph) {
		this.dph = dph;
	}
	public String getFrw_pln_qua() {
		return frw_pln_qua;
	}
	public void setFrw_pln_qua(String frw_pln_qua) {
		this.frw_pln_qua = frw_pln_qua;
	}
	public String getRwt_cap() {
		return rwt_cap;
	}
	public void setRwt_cap(String rwt_cap) {
		this.rwt_cap = rwt_cap;
	}
	public String getPump_hrp() {
		return pump_hrp;
	}
	public void setPump_hrp(String pump_hrp) {
		this.pump_hrp = pump_hrp;
	}
	public String getPipe_diam() {
		return pipe_diam;
	}
	public void setPipe_diam(String pipe_diam) {
		this.pipe_diam = pipe_diam;
	}
	public String getPub_pri_gbn() {
		return pub_pri_gbn;
	}
	public void setPub_pri_gbn(String pub_pri_gbn) {
		this.pub_pri_gbn = pub_pri_gbn;
	}
	public String getObsv_name() {
		return obsv_name;
	}
	public void setObsv_name(String obsv_name) {
		this.obsv_name = obsv_name;
	}
	public String getMgr_org() {
		return mgr_org;
	}
	public void setMgr_org(String mgr_org) {
		this.mgr_org = mgr_org;
	}
	public String getPyogo() {
		return pyogo;
	}
	public void setPyogo(String pyogo) {
		this.pyogo = pyogo;
	}
	public String getInsdate() {
		return insdate;
	}
	public void setInsdate(String insdate) {
		this.insdate = insdate;
	}
	public String getGuldep() {
		return guldep;
	}
	public void setGuldep(String guldep) {
		this.guldep = guldep;
	}
	public String getGuldai() {
		return guldia;
	}
	public void setGuldai(String guldia) {
		this.guldia = guldia;
	}
	public String getDcode() {
		return dcode;
	}
	public void setDcode(String dcode) {
		this.dcode = dcode;
	}
	public String getTmx() {
		return tmx;
	}
	public void setTmx(String tmx) {
		this.tmx = tmx;
	}
	public String getTmy() {
		return tmy;
	}
	public void setTmy(String tmy) {
		this.tmy = tmy;
	}
	
	
}
