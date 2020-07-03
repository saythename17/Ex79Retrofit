package com.icandothisallday2020.ex79retrofit;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitService {
    //1. GET 방식으로 json 문자열을 읽어오는 추상메소드
    @GET("/Retrofit/board.json")
    Call<BoardItem> getBoard();//Annotation 을 부르는 이름

    //2. [@Path] : 경로의 이름을 1번처럼 고정하지 않고 파라미터로 전달받아 지정
    @GET("/{folder}/board.json")
    Call<BoardItem> getBoardByPath(@Path("folder") String folder);

    //3. [@Query]GET 방식으로 서버에 데이터 전달
    @GET("/Retrofit/getTest.php")
    Call<BoardItem> getMethodTest(@Query("name") String name, @Query("msg") String msg);

    //4.Get 방식으로 값을 전달하면서 경로의 파일명 지정 [@Query @Path]
    @GET("/Retrofit/{filename}")
    Call<BoardItem> getMethodTest2(@Path("filename") String fileName, @Query("name") String name,
                                    @Query("msg") String msg);

    //5.Get 방식으로 여러개의 값을 Map collection 한번에 전달하기 [@QueryMap]
    @GET("/Retrofit/getTest.php")
    Call<BoardItem> getMethodTest3(@QueryMap Map<String,String> datas);

    //*6.Post 방식으로 데이터 보내기 [@Body] - 객체를 전달하면 자동으로
    //   json 문자열로 변환(시리얼라이즈)하여 Body 데이터에 넣어 서버로 전송
    @POST("/Retrofit/postTest.php")
    Call<BoardItem> postTestMethod(@Body BoardItem item);

    //*7.Post 방식으로 여러개의 멤버값을 (Get 처럼) 별도로 보내고 싶을 때
    // [@Field]---@Query 와 유사 ※반드시 @FormUrlEncoded 와 함께쓰는 annotation
    @FormUrlEncoded
    @POST("/Retrofit/postTest2.php")
    Call<BoardItem> postTestMethod2(@Field("name") String name,@Field("msg") String msg);

    //8.응답받을 데이터가 jsonArray 일때
    @GET("/Retrofit/boardArray.json")
    Call<ArrayList<BoardItem>> getBoardArray();

    //9.baseUrl 을 무시하고 지정된 url 로 연결 [@Url]
    @GET
    Call<BoardItem> urlTest(@Url String url);

    //10.응답결과:json--(GSON 을 이용) BoardItem 객체로 받지 않고
    //json 이 아닌 String 으로 받기//parsing 을 안한결과로 받고 싶다면...
    //Helper 사용 불가
    @GET("/Retrofit/board.json")
    Call<String> getJsonString();


}
