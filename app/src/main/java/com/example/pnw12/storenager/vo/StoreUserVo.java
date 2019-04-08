package com.example.pnw12.storenager.vo;

public class StoreUserVo {

    static int userNo = 0;

    public static int getUserNo() {
        return userNo;
    }

    public static void setUserNo(int userNo) {
        StoreUserVo.userNo = userNo;
    }

}//StoreUserVo end
