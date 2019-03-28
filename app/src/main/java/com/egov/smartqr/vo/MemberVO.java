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
public class MemberVO{
	public int gid;
	public String ins_nm;
	public String ins_org;
	public String ins_id;
	public String ins_pw;
	public String ins_phone;
	public String reg_date;
	public String mod_date;
	
	
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getIns_nm() {
		return ins_nm;
	}
	public void setIns_nm(String ins_nm) {
		this.ins_nm = ins_nm;
	}
	public String getIns_org() {
		return ins_org;
	}
	public void setIns_org(String ins_org) {
		this.ins_org = ins_org;
	}
	public String getIns_id() {
		return ins_id;
	}
	public void setIns_id(String ins_id) {
		this.ins_id = ins_id;
	}
	public String getIns_pw() {
		return ins_pw;
	}
	public void setIns_pw(String ins_pw) {
		this.ins_pw = ins_pw;
	}
	public String getIns_phone() {
		return ins_phone;
	}
	public void setIns_phone(String ins_phone) {
		this.ins_phone = ins_phone;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	
	
	
	
}
