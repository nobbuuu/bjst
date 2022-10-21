package com.kredit.cash.loan.app.liveness

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.utils.StatusBarUtils

/**
 * Created by Win on 2022/10/19
 */
class LivenessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_liveness)
        val mFragment = LivenessFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, mFragment)
        fragmentTransaction.commit()
        StatusBarUtils.adjustWindow(this)
    }
}