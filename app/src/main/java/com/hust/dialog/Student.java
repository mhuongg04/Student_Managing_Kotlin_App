package com.hust.dialog;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String mssv;
    private String sdt;
    private String email;

    public Student(String name, String mssv, String sdt, String email){
        this.name = name;
        this.mssv = mssv;
        this.sdt = sdt;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMSSV() {
        return mssv;
    }
    public void setMSSV(String mssv) {
        this.mssv = mssv;
    }

    public String getSDT() {
        return sdt;
    }
    public void setSDT(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
