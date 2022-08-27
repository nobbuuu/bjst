package com.dream.bjst.utils

import android.os.Handler
import android.widget.TextView

class SendCodeUtils(
    val sendTv: TextView? = null,
    val handler: Handler? = null,
    val endStr: String? = ""
) {

    private var countdown = 60
    private var mRunnable = object : Runnable {
        override fun run() {
            if (countdown >= 0) {
                sendTv?.text = countdown--.toString() + "S"
                sendTv?.isEnabled = false
                handler?.postDelayed(this, 1000)
            } else {
                sendTv?.isEnabled = true
                countdown = 60
                sendTv?.text = endStr
            }
        }
    }

    fun start(): SendCodeUtils {
        handler?.post(mRunnable)
        return this
    }

    fun onPause() {
        handler?.removeCallbacks(mRunnable)
    }
}