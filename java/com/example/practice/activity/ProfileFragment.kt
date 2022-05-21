package com.example.practice.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.practice.R
import com.example.practice.childObject.ProfileObject
import com.example.practice.database.DB
import com.example.practice.helper.ProfListAdapter

class ProfileFragment(private var userIdx: String?): Fragment() {

    private var container: ViewGroup? = null;
    private var frView: View? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false);
        this.container = container;
        this.frView = view;

        drawProfiles(container, view);
        handle(view);
        return view;
    }

    private fun drawProfiles(container: ViewGroup?, view: View?) {
        val db = DB(container?.context, "showtime.db");
        val users = db.findAll("profile");

        users.sortBy {
            var a = if (it.get("idx")!! == userIdx) -1 else 0;
            a;
        };

        val profList = ProfListAdapter(userIdx, this.activity);
        val profListView = view?.findViewById<GridView>(R.id.profile_list);

        profListView?.setOnTouchListener { view, motionEvent ->
            profListView.parent.requestDisallowInterceptTouchEvent(true);
            false;
        }

        users.forEach { table ->
            val prof = ProfileObject(table.get("name"), table.get("color"), table.get("idx"));
            profList.addItem(prof);
        };

        profListView?.adapter = profList;
    }

    fun handle(view: View?) {
        val profCreate = view?.findViewById<Button>(R.id.profile_add);
        val profModify = view?.findViewById<Button>(R.id.profile_modify);

        profCreate?.setOnClickListener {
            val intent = Intent(this.context, ProfileMakeActivity::class.java);
            intent.putExtra("back", true);
            startActivityForResult(intent, 3000);
        };
        profModify?.setOnClickListener {
            val intent = Intent(this.context, ProfileModifyActivity::class.java);
            intent.putExtra("idx", userIdx);
            startActivityForResult(intent, 3000);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        drawProfiles(container, frView);
    }

}