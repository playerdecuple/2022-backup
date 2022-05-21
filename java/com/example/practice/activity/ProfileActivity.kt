package com.example.practice.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import com.example.practice.R
import com.example.practice.childObject.Profile
import com.example.practice.childObject.ProfileObject
import com.example.practice.database.DB
import com.example.practice.helper.ProfGridAdapter

class ProfileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        val profGridView = findViewById<GridView>(R.id.prof_grid);
        val profAdapter = ProfGridAdapter();

        val db = DB(this, "showtime.db");
        db.findAll("profile").forEach {
            val data = it.data;
            profAdapter.addItem(
                ProfileObject(data["name"], data["color"], data["idx"])
            );
        };



        profGridView.adapter = profAdapter;
        profGridView.setOnItemClickListener { adapterView, view, i, l ->

            val item: ProfileObject = profAdapter.getItem(i);

            val intent = Intent(this, MainActivity::class.java);
            intent.putExtra("idx", item.idx);
            startActivity(intent);

            finish();

        };


        val addButton = findViewById<Button>(R.id.profile_add);
        addButton.setOnClickListener {
            val intent = Intent(this, ProfileMakeActivity::class.java);
            startActivity(intent);
        };
    }

}