package com.example.practice.childObject

class ProfileObject(name: Any?, color: Any?, idx: Any?) {

    var name: String = "";
    var color: String = "";
    var idx: String = "";


    init {
        this.name = name.toString();
        this.color = color.toString();
        this.idx = idx.toString();
    }

}