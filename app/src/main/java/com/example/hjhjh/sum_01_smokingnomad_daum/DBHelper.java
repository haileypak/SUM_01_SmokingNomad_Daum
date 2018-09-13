package com.example.hjhjh.sum_01_smokingnomad_daum;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

public class DBHelper extends SQLiteOpenHelper{

    //DBHelper 생성자로 관리할 DB이름과 버전 정보 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    // DB생성시 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        //새로운 테이블 생성
        /* 테이블 정보 작성*/
        db.execSQL("CREATE TABLE SMOKINGAREA (seq INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, " +
                "latitude DOUBLE, longtitude DOUBLE, address TEXT)");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String title, Double latitude, Double longtitude, String address){
        //읽고 쓰기가 가능하게 DB열기
        SQLiteDatabase db = getWritableDatabase();
        //DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SMOKINGAREA VALUES(null, '"+title+"',"+latitude+","+longtitude+",'"+address+"');");
        db.close();
    }

    public void update(int seq, String title, Double latitude, Double longtitude, String address){
        SQLiteDatabase db = getWritableDatabase();
        //입력한 항목과 일치하는 행의 정보 수정
        db.execSQL("UPDATE SMOKINGAREA SET title="+title+",latitude="+latitude+",longtitude="+longtitude+",address="+address+" WHERE seq='"+seq+"';");
        db.close();
    }

    public void delete(int seq){
        SQLiteDatabase db = getWritableDatabase();
        //입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SMOKINGAREA WHERE seq='"+seq+"';");
        db.close();
    }

    public String getResult(){
        //읽기가 가능하게 DB열기
        SQLiteDatabase db=getReadableDatabase();
        String result="";

        //DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SMOKINGAREA",null);
        while(cursor.moveToNext()){
            result += cursor.getInt(0)
                    +"/"
                    + cursor.getString(1)
                    +"/"
                    +cursor.getDouble(2)
                    +"/"
                    +cursor.getDouble(3)
                    +"/"
                    +cursor.getString(4);

        }

        return result;
    }
}