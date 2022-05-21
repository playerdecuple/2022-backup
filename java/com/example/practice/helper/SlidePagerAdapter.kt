package com.example.practice.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var items: List<Fragment> = ArrayList();

    override fun getCount(): Int = items.size;
    override fun getItem(position: Int): Fragment = items[position]
}