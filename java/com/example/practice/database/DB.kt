package com.example.practice.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context?, name: String?, version: Int? = 2)
    : SQLiteOpenHelper(context, name, null, version!!) {

    // 데이터베이스 테이블 추가
    private fun createTable(name: String?, columns: String?) {
        val db = this.writableDatabase;
        db?.execSQL("create table $name ($columns)");
    }
    private fun createTable(name: String?, columns: String?, db: SQLiteDatabase?) {
        db?.execSQL("create table $name ($columns)");
    }

    // 데이터베이스 삽입
    fun insert(table: String, data: HashMap<String, Any>) {
        val db = this.writableDatabase;
        val values = ContentValues().apply {
            data.forEach { (key, value) ->
                put(key, value.toString());
            };
        }

        db.insert(table, null, values);
        db.close();
    }
    fun update(table: String, data: HashMap<String, Any>, sql: String, sqlR: Array<String>) {
        val db = this.writableDatabase;
        val values = ContentValues().apply {
            data.forEach { (key, value) ->
                put(key, value.toString());
            };
        };

        db.update(table, values, sql, sqlR);
    }

    // 데이터베이스 찾기
    fun findAll(table: String, sql: String = ""): ArrayList<Table> {
        val db = this.readableDatabase;
        val list = ArrayList<Table>();

        var query = "SELECT * FROM `$table`";
        if (sql.isNotEmpty()) query += " WHERE $sql";

        val cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            var table = Table();
            cursor.columnNames.forEach {k ->
                table.data[k] = cursor.getString(cursor.getColumnIndexOrThrow(k));
            };

            list.add(table);
        }

        cursor.close();
        db.close();

        return list;
    }

    override fun onCreate(db: SQLiteDatabase?) {
        createTable("profile", "idx integer primary key, name text, color text", db);
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}


}