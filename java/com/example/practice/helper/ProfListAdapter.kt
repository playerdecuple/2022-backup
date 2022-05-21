package com.example.practice.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.practice.R
import com.example.practice.activity.MainActivity
import com.example.practice.childObject.ProfileObject

class ProfListAdapter(private var nowUserIdx: String?, private var act: FragmentActivity?) : BaseAdapter() {

    private var items: ArrayList<ProfileObject> = ArrayList();
    private var context: Context? = null;


    override fun getCount(): Int = items.size;
    override fun getItem(pos: Int): ProfileObject = items[pos];
    override fun getItemId(pos: Int): Long = pos.toLong();

    fun addItem(prof: ProfileObject) = items.add(prof);


    override fun getView(pos: Int, cv: View?, parent: ViewGroup?): View {
        context = parent?.context;
        var prof: ProfileObject = items[pos];
        var conView = cv;

        if (conView == null) {
            val inf = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            conView = inf.inflate(R.layout.profile_preview, parent, false);
        }

        val profImg = conView!!.findViewById<TextView>(R.id.profile_image);
        val profBg: GradientDrawable =
            ContextCompat.getDrawable(context!!, R.drawable.profile_image) as GradientDrawable;
        val profName = prof.name.substring(0, 1);
        val profColor = prof.color.split(",").map {it.toInt()};

        profBg.setStroke(0, Color.argb(0, 0, 0, 0));
        if (prof.idx == nowUserIdx) {
            profBg.setStroke(10, Color.argb(255, 76, 224, 175));
        }

        profBg.setColor(Color.argb(255, profColor[0], profColor[1], profColor[2]));
        profImg.setBackgroundDrawable(profBg);
        profImg.text = profName;

        profImg?.setOnClickListener {
            val user = this.getItem(pos);
            println(user);
            val intent = Intent(this.context, MainActivity::class.java);

            intent.putExtra("idx", user.idx);
            this.context?.startActivity(intent);

            act?.finish();
        };

        return conView;
    }

}