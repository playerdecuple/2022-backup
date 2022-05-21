package com.example.practice.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.practice.R
import com.example.practice.childObject.Profile
import com.example.practice.database.DB
import java.util.*

class ProfileMakeActivity() : Activity() {

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
        setContentView(R.layout.profile_create_acitivty);

        val profileImageShape: GradientDrawable =
            ContextCompat.getDrawable(this, R.drawable.profile_image) as GradientDrawable;
            // GradientDrawable 캐스팅 -> setColor 메서드 사용

        // 뷰
        val profileImage = findViewById<TextView>(R.id.profile_image);
        val profileName = findViewById<TextView>(R.id.name);
        val profileInput = findViewById<EditText>(R.id.profile_name);

        val submit = findViewById<Button>(R.id.profile_add);


        // 난수 색 지정
        val r = Random().nextInt(155) + 50;
        val g = Random().nextInt(155) + 50;
        val b = Random().nextInt(155) + 50; // 너무 밝거나 너무 어두운 색 제외
        
        profileImageShape.setColor(Color.argb(255, r, g, b));
        profileImage.setBackgroundDrawable(profileImageShape);


        // 이름 입력 핸들링
        var name = "";

        profileInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(seq: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str = seq.toString();

                if (str.isEmpty()) return;
                profileImage.text = str.substring(0, 1);
                profileName.text = str;

                name = str;
            }
        });
        submit.setOnClickListener {
            if (!name.trim().isNullOrEmpty()) {
                var prof = Profile();
                val db = DB(this, "showtime.db", 2);

                prof.set("name", name);
                prof.set("color", "$r,$g,$b");

                db.insert("profile", prof.data);

                if (!intent.hasExtra("back")) {
                    val intent = Intent(this, ProfileActivity::class.java);
                    Toast.makeText(applicationContext, "성공적으로 등록되었습니다.", Toast.LENGTH_LONG).show();

                    startActivity(intent);
                }

                finish();
            } else {
                Toast.makeText(applicationContext, "이름을 제대로 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        };

    }

}