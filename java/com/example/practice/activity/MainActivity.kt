package com.example.practice.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.practice.R
import com.example.practice.database.DB
import com.example.practice.database.Table
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    override fun onPause() {
        super.onPause();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }
    override fun onResume() {
        super.onResume();
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    var idx: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        val frHome = HomeFragment();

        idx = intent.getStringExtra("idx");

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_menu);
        val db = DB(this, "showtime.db");

        val user: Table = db.findAll("profile", "`idx` = $idx")[0];
        val name: String = user.get("name") as String;
        val colorStr: String = user.get("color") as String;
        val color = colorStr.split(",").map {it.toInt()};

        val img = findViewById<TextView>(R.id.profile_preview_image);
        val background: GradientDrawable =
                ContextCompat.getDrawable(this, R.drawable.profile_image) as GradientDrawable;

        background.setColor(Color.argb(255, color[0], color[1], color[2]));
        img.setBackgroundDrawable(background);
        img.text = name.substring(0, 1);


        supportActionBar?.hide();
        supportFragmentManager.beginTransaction().replace(R.id.container, frHome).commit();
        
        bottomBar.setOnItemSelectedListener {
            var fragment: Fragment? = null;

            fragment = when (it.itemId) {
                R.id.home -> frHome;
                R.id.release -> ReleaseFragment();
                R.id.download -> DownloadFragment();
                R.id.profile -> ProfileFragment(idx);
                else -> return@setOnItemSelectedListener false;
            }

            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            return@setOnItemSelectedListener true;
        }
    }

}