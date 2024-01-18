package skancode.modules.nativebarcodescanner

import android.content.Context
import android.content.Intent

class ScannerConfig() {
    var context: Context? = null
    var autoEnter: Enabler = Enabler.OFF
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("EXTRA_SCAN_AUTOENT", value.ordinal)
            context?.sendBroadcast(intent)
        }
    var notificationSound: Enabler = Enabler.OFF
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("EXTRA_SCAN_NOTY_SND", value.ordinal)
            context?.sendBroadcast(intent)
        }
    var notificationVibration: Enabler = Enabler.OFF
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("EXTRA_SCAN_NOTY_VIB", value.ordinal)
            context?.sendBroadcast(intent)
        }
    var notificationLED: Enabler = Enabler.OFF
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("EXTRA_SCAN_NOTY_LED", value.ordinal)
            context?.sendBroadcast(intent)
        }
    var scanMode: ScanMode = ScanMode.API
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("EXTRA_SCAN_MODE", value.ordinal)
            context?.sendBroadcast(intent)
        }

    var scanInterval: Long = 50
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("SCAN_INTERVAL", value)
            context?.sendBroadcast(intent)
        }

    var scanEncoding: ScanEncoding = ScanEncoding.GBK
        set(value) {
            field = value
            val intent = Intent("ACTION_BAR_SCANCFG")

            intent.putExtra("SCAN_ENCODE", value.ordinal)
            context?.sendBroadcast(intent)
        }
}