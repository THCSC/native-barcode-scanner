package skancode.modules.nativebarcodescanner

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.types.Enumerable

const val BARCODE_DATA_RECEIVED_EVENT_NAME = "onBarcodeDataReceived"
const val SCANNER_STATE_CHANGED = "onScannerStateChange"
class NativeBarcodeScannerModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("NativeBarcodeScanner")

//region lifecycle hooks
    OnCreate {
      setScannerState(Enabler.OFF)
      scannerConfig.context = context
    }

    OnDestroy {
      setScannerState(Enabler.ON)
    }

    OnStartObserving {
      registerReceiver()
    }

    OnStopObserving {
      unregisterReceiver()
    }

    OnActivityEntersBackground {
      setScannerState(Enabler.ON)
    }

    OnActivityEntersForeground {
      setScannerState(Enabler.OFF)
    }
    //endregion

    Property("autoEnter")
      .set<Enabler> { scannerConfig.autoEnter = it }

    Property("notificationSound")
      .set<Enabler> { scannerConfig.notificationSound = it }

    Property("notificationVibration")
      .set<Enabler> { scannerConfig.notificationVibration = it }

    Property("notificationLED")
      .set<Enabler> { scannerConfig.notificationLED = it }

    Property("scanMode")
      .set<ScanMode> { scannerConfig.scanMode = it }

    Property("encoding")
      .set<ScanEncoding> { scannerConfig.scanEncoding = it }

    Property("scanInterval")
      .set<Long> { scannerConfig.scanInterval = it }

    Events(BARCODE_DATA_RECEIVED_EVENT_NAME, SCANNER_STATE_CHANGED)

    Function("modelNumber") {
      android.os.Build.MODEL
    }

    Function("brand") {
      android.os.Build.BRAND
    }

    Function("getScanner") {
      getPreferences().getString("scannerState", "off")
    }

    Function("setScanner") { value: Enabler ->
      setScannerState(value)
      this@NativeBarcodeScannerModule.sendEvent(SCANNER_STATE_CHANGED, bundleOf(
        "state" to value.value
      ))
    }
  }
  private val context: Context
    get() = requireNotNull(appContext.reactContext)

  private val dataReceiver = BarcodeDataReceiver(this@NativeBarcodeScannerModule)
  private val scannerConfig = ScannerConfig()

  private fun getPreferences(): SharedPreferences {
    return context.getSharedPreferences(context.packageName + ".barcode", Context.MODE_PRIVATE)
  }

  private fun setScannerState(enabler: Enabler) {
    getPreferences().edit().putString("scannerState", enabler.value).commit()
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra("EXTRA_SCAN_POWER", enabler.ordinal)
    context.sendBroadcast(intent)
  }

  private fun registerReceiver() {
    val filter = IntentFilter("nlscan.action.SCANNER_RESULT")
    val flag = ContextCompat.RECEIVER_EXPORTED

    ContextCompat.registerReceiver(context, dataReceiver, filter, flag)
  }

  private fun unregisterReceiver() {
    context.unregisterReceiver(dataReceiver);
  }

}
