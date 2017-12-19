package com.liuhc.demo

import android.view.Menu
import com.liuhc.lib.ui.activity.AbstractBackActivity

class MainActivity : AbstractBackActivity() {

    override fun getContextViewResId(): Int = R.layout.activity_main

    override fun doInitView() {
        super.doInitView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun isFirstPage() = true
}
