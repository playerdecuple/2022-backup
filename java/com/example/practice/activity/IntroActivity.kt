package com.example.practice.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.practice.R
import com.example.practice.database.DB

class IntroActivity: Activity() {

    override fun onPause() {
        super.onPause();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }
    override fun onResume() {
        super.onResume();
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)

        var animMain = AnimationUtils.loadAnimation(applicationContext, R.anim.intro_translation)
        var animSpawn = AnimationUtils.loadAnimation(applicationContext, R.anim.intro_spawn_tr)

        val showTimeLogo = findViewById<ImageView>(R.id.intro_logo_showtime)
        val spawnLogo = findViewById<ImageView>(R.id.spawn)

        showTimeLogo.startAnimation(animMain)
        spawnLogo.startAnimation(animSpawn)


        val db = DB(this, "showtime.db", 2);

        // 이동
        Handler().postDelayed({
            val profileExists: Boolean = db.findAll("profile").size > 0;

            var intent =
                if (profileExists) Intent(this, ProfileActivity::class.java)
                else Intent(this, ProfileEmptyActivity::class.java);

            println(if (profileExists) "Profile Exists." else "Profile Not Exists.");

            startActivity(intent);
            finish();
        }, 3000)
    }

}