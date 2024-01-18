package skancode.modules.nativebarcodescanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import expo.modules.kotlin.modules.Module

class BarcodeDataReceiver(private val module: Module) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val result1 = intent?.getStringExtra("SCAN_BARCODE1")
        val result2 = intent?.getStringExtra("SCAN_BARCODE2")
        val barcodeType = intent?.getIntExtra("SCAN_BARCODE_TYPE", -1)
        val okStr = intent?.getStringExtra("SCAN_STATE")

        module.sendEvent("onBarcodeDataReceived", bundleOf(
            "barcode1" to result1,
            "barcode2" to result2,
            "barcodeType" to barcodeType,
            "ok" to (okStr == "ok")
        ))
    }
}