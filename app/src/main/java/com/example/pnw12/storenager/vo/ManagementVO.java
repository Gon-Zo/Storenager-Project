package com.example.pnw12.storenager.vo;

public class ManagementVO {
    private String img, num, day, no, type, name;

    public ManagementVO(){

    }
    public ManagementVO(String name , String day , String num){
        this.name=name;
        this.day = day;
        this.num = num;
    }
    public String getDay() {
        return day;
    }

    public String getImg() {
        return img;
    }

    public String getNo() {
        return no;
    }

    public String getNum() {
        return num;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setType(String type) {
        this.type = type;
    }
}
