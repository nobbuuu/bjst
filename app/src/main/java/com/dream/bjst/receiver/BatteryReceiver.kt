package com.dream.bjst.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tcl.base.utils.MmkvUtil

class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val level: Int? = intent?.getIntExtra("level", 0)
//        Log.i("Battery", "当前电池的剩余电量为：$level%")
        MmkvUtil.encode("batteryPct","$level%")
    }
}