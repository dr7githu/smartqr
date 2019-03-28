package com.egov.smartqr.vo;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class EditSearchInfo {
    private String num;
    private String addr;

    public EditSearchInfo(String num, String addr) {
        this.num = num;
        this.addr = addr;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
