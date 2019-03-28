package com.egov.smartqr.vo;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class SearchInfo {
    private String num;
    private String gubun;
    private String useage;
    private String useageDetail;

    public SearchInfo(String num, String gubun, String useage, String useageDetail) {
        this.num = num;
        this.gubun = gubun;
        this.useage = useage;
        this.useageDetail = useageDetail;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getUseage() {
        return useage;
    }

    public void setUseage(String useage) {
        this.useage = useage;
    }

    public String getUseageDetail() {
        return useageDetail;
    }

    public void setUseageDetail(String useageDetail) {
        this.useageDetail = useageDetail;
    }
}
