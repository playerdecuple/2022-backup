package com.example.practice.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.practice.R
import com.example.practice.childObject.ProfileObject

class ProfGridAdapter: BaseAdapter() {
    private var items: ArrayList<ProfileObject> = ArrayList();
    private var context: Context? = null;


    fun addItem(prof: ProfileObject) = items.add(prof);


    override fun getCount(): Int = items.size;
    override fun getItem(p0: Int): ProfileObject = items[p0];
    override fun getItemId(p0: Int): Long = p0.toLong();

    override fun getView(pos: Int, cv: View?, parent: ViewGroup?): View {
        context = parent?.context;
        var prof: ProfileObject = items[pos];
        var convertView = cv;

        if (convertView == null) {
            val inf = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            convertView = inf.inflate(R.layout.profile_card, parent, false);
        }

        val profileImage = convertView!!.findViewById<TextView>(R.id.profile_image);
        val profileImageShape: GradientDrawable =
            ContextCompat.getDrawable(context!!, R.drawable.profile_image) as GradientDrawable;

        val profileName = convertView.findViewById<TextView>(R.id.name);
        val profileColor = prof.color.split(",").map { it.toInt() };

        profileImageShape.setColor(Color.argb(255, profileColor[0], profileColor[1], profileColor[2]));
        profileImage.setBackgroundDrawable(profileImageShape);
        profileImage.text = prof.name.substring(0, 1);
        profileName.text = prof.name;

        return convertView;
    }

}