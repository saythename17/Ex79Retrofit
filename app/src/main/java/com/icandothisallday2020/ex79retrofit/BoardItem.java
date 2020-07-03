package com.icandothisallday2020.ex79retrofit;

import com.google.gson.annotations.SerializedName;

public class BoardItem {
    String name,msg;//변환할 json 파일의 key 값과 변수명(&자료형)이 같아야 함

    /*만약 json 의 키값과 다른 변수명을 사용하고 싶다면 */
    @SerializedName("msg")//객체를 직렬화(객체는 파일로 저장할 수 있는 방법)
    String message;

    public BoardItem() {
    }

    public BoardItem(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }
}
