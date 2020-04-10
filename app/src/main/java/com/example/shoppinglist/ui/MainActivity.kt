package com.example.shoppinglist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.adapters.MyViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var myViewPagerAdapter: MyViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewPagerAdapter =
            MyViewPagerAdapter(
                supportFragmentManager
            )
        main_activity_view_pager.adapter = myViewPagerAdapter
        main_activity_tab_layout.setupWithViewPager(main_activity_view_pager)
    }
}
