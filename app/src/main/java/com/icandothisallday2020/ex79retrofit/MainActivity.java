package com.icandothisallday2020.ex79retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv);
    }

    public void getjsonfromnet(View view) {
        //네트워크에서 읽어들인 json 을 객체로 생성
        //Retrofit2 이용-> 라이브러리로 HTTP 통신작업 시작

        //1. Retrofit 객체 생성 밒 기본설정
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://soon0.dothome.co.kr");//서버 기본주소
        builder.addConverterFactory(GsonConverterFactory.create());
        //└읽어온 json 을 GSON 을 이용하여 parsing 하기 위한 설정
        Retrofit retrofit=builder.build();

        //2.RetrofitService Interface 설계
        //원하는 기능의 추상메소드 설계 getBoard()
        
        //3.RetrofitService Interface 를 객체로 생성
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        //network 에 필요한 (InputStream 등...) 기능 알아서 만듬
        // Service 객체의 abstract method()의 기능을 알아서 retrofit 이 만들어냄
        
        //4.Service 객체의 원하는 기능메소드 실행->Call 객체 얻어옴
        Call<BoardItem> call=retrofitService.getBoard();
        //-----------------------여기까지 network 작업---------------------------
        
        
        
        //-----------------------Call 들이 network 여행을 통해 순서대로 실행되어 정보를 가져옴
        //(도착하는 순서!=실행순서)
        
        //5.원하는 기능의 network 작업을 수행하도록 call 객체를 큐에 삽입
        call.enqueue(new Callback<BoardItem>() {//가지고 돌아오면 실행되는 메소드
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                Toast.makeText(MainActivity.this, "응답", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    //응답객체로 부터 GSon Library 에 의해 자동으로 BoardItem 으로
                    //parsing 되어 있는 json 문자열의 데이터값 body 얻어오기
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }
                
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {
                Toast.makeText(MainActivity.this, "응답실패", Toast.LENGTH_SHORT).show();

            }
        });//stack : FILO / queue : FIFO
    }

    public void getjsonfromnetbypath(View view) {
        //1.Retrofit 객체 생성
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://soon0.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();

        //2.RetrofitService 인터페이스에 원하는 기능 추상메소드 설계
        //getBoardJsonByPath()

        //3.RetrofitService 객체를 생성
        RetrofitService service=retrofit.create(RetrofitService.class);

        //4.원하는 기능 메소드를 호출하여 network 작업을 하는 객체 리턴
        Call<BoardItem> call=service.getBoardByPath("Retrofit");//---경로명을 파라미터로 전달 : 전달한 폴더명을 통해 위치를 찾아감

        //5.실제 네트워크 작업 실행
        call.enqueue(new Callback<BoardItem>() {//enqueue 활주로
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }

            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void getMethod(View view) {
        //1.
        Retrofit retrofit=RetrofitHelper.getRetrofitInstatnce();
        //2.getMethodTest()
        //3.
        RetrofitService service=retrofit.create(RetrofitService.class);
        //4.
        Call<BoardItem> call=service.getMethodTest("robin","Nice to meet you");
        //5.
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void getMethodByPath(View view) {
        Retrofit retrofit=RetrofitHelper.getRetrofitInstatnce();
        RetrofitService service=retrofit.create(RetrofitService.class);
        Call<BoardItem> call=service.getMethodTest2("getTest.php","hong","good morning");
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()) {
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void getMethodByMap(View view) {
        Retrofit retrofit=RetrofitHelper.getRetrofitInstatnce();
        RetrofitService service=retrofit.create(RetrofitService.class);
        //server 에 전달할 데이터들을 Map Collection 에 저장
        Map<String,String> datas=new HashMap<>();
        datas.put("name","haein");
        datas.put("msg","good afternoon");
        Call<BoardItem> call=service.getMethodTest3(datas);
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void postMethod(View view) {
        RetrofitService service=RetrofitHelper.getRetrofitInstatnce().create(RetrofitService.class);
        //파라미터로 보낼 데이터(BoardItem)를 가진 객체
        BoardItem item=new BoardItem("trueman","Good Evening");
        Call<BoardItem> call=service.postTestMethod(item);
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void postMethodByMembers(View view) {
        RetrofitService service=RetrofitHelper.getRetrofitInstatnce().create(RetrofitService.class);
        Call<BoardItem> call=service.postTestMethod2("kei","goodnight");
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item=response.body();
                    tv.setText(item.name+","+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void getjsonArray(View view) {
        RetrofitService service=RetrofitHelper.getRetrofitInstatnce().create(RetrofitService.class);
        Call<ArrayList<BoardItem>> call=service.getBoardArray();
        call.enqueue(new Callback<ArrayList<BoardItem>>() {
            @Override
            public void onResponse(Call<ArrayList<BoardItem>> call, Response<ArrayList<BoardItem>> response) {
                if(response.isSuccessful()){
                    ArrayList<BoardItem> items=response.body();
                    for(BoardItem item: items){
                        tv.append("\n"+item.name+","+item.msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BoardItem>> call, Throwable t) {

            }
        });
    }

    public void urlTest(View view) {
        RetrofitService service=RetrofitHelper.getRetrofitInstatnce().create(RetrofitService.class);
        Call<BoardItem> call=service.urlTest("http://soon0.dothome.co.kr/Retrofit/board.json");
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                BoardItem item=response.body();
                tv.setText(item.name+","+item.msg);
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void getToString(View view) {//parsing 을 안한결과를 받고 싶을 때
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://soon0.dothome.co.kr");
        //결과를 String 으로 받기 위해서는 GsonConverter 로 받으면 안됨-※ScalarsConverter 사용
        //build.gradle 에 Library 추가 [converter-scalars 로 검색]
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit=builder.build();
        RetrofitService service=retrofit.create(RetrofitService.class);
        Call<String> call=service.getJsonString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s=response.body();
                tv.setText(s);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
