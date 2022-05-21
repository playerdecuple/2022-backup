package com.example.practice.database

import com.example.practice.childObject.Profile

open class Table {

    var data: HashMap<String, Any> = HashMap();


    fun get(key: String): Any? {
        return data[key];
    }
    fun set(key: String, value: Any): Any {
        data[key] = value;
        return this;
    }

}