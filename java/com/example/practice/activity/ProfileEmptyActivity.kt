package com.example.practice.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.practice.R

class ProfileEmptyActivity: Activity() {

    override fun onPause() {
        super.onPause();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }
    override fun onResume() {
        super.onResume();
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_empty_activity);

        val addButton = findViewById<Button>(R.id.profile_add);
        addButton.setOnClickListener {
            val intent = Intent(this, ProfileMakeActivity::class.java)
            startActivity(intent);
        };
    }


}