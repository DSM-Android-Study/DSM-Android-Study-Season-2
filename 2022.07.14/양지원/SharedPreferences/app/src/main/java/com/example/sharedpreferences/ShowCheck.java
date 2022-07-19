package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ShowCheck {
    private final String fileName = "guideCheck";
    private final String firstStart ="firstStart"; //첫 로그인시 가이드 확인 키

    //처음 로그인시 가이드 다시보지 않기 유무 저장. flag == ture 체크박스 체크됨.
    public void setFirstLogin(Context context, Boolean flag){
        SharedPreferences prefs = context.getSharedPreferences (fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit ();
        editor.putBoolean (firstStart,flag); //데이터를 삽입한다.
        editor.apply ();
    }

    //처음 로그인시 가이드 다시보지 않기 유무 불러오기
    public Boolean getFirstLogin(Context context){
        SharedPreferences prefs = context.getSharedPreferences (fileName,Context.MODE_PRIVATE);
        return prefs.getBoolean (firstStart,false); //boolean은 디폴트값을 설정해야 한다.
    }
}
