package com.liuhc.demo

import android.view.Menu
import com.liuhc.lib.ui.activity.AbstractToolbarActivity

class MainActivity : AbstractToolbarActivity() {

    override fun getContentViewResId(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun isFirstPage() = true
}
