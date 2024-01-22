package skancode.modules.nativebarcodescanner

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

const val BARCODE_DATA_RECEIVED_EVENT_NAME = "onBarcodeDataReceived"
const val SCANNER_STATE_CHANGED = "onScannerStateChange"
class NativeBarcodeScannerModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("NativeBarcodeScanner")

//region lifecycle hooks
    OnCreate {
      setScannerState(Enabler.OFF)
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
      .set<Enabler> { configureScanner(ScannerConfigKey.AUTO_ENTER, it.ordinal) }

    Property("notificationSound")
      .set<Enabler> { configureScanner(ScannerConfigKey.NOTIFICATION_SOUND, it.ordinal) }

    Property("notificationVibration")
      .set<Enabler> { configureScanner(ScannerConfigKey.NOTIFICATION_VIBRATION, it.ordinal) }

    Property("notificationLED")
      .set<Enabler> { configureScanner(ScannerConfigKey.NOTIFICATION_LED, it.ordinal) }

    Property("scanMode")
      .set<ScanMode> { configureScanner(ScannerConfigKey.SCAN_MODE, it.ordinal) }

    Property("encoding")
      .set<ScanEncoding> { configureScanner(ScannerConfigKey.SCAN_ENCODING, it.ordinal) }

    Property("scanInterval")
      .set<Long> { configureScanner(ScannerConfigKey.SCAN_INTERVAL, it) }

    Events(BARCODE_DATA_RECEIVED_EVENT_NAME, SCANNER_STATE_CHANGED)

    Function("scannerAvailable") {
      isScannerAvailable()
    }

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

  private fun getPreferences(): SharedPreferences {
    return context.getSharedPreferences(context.packageName + ".barcode", Context.MODE_PRIVATE)
  }

  private fun setScannerState(enabler: Enabler) {
    getPreferences().edit().putString("scannerState", enabler.value).apply()
    configureScanner(ScannerConfigKey.SCAN_POWER, enabler.ordinal)
  }

  private fun registerReceiver() {
    val filter = IntentFilter("nlscan.action.SCANNER_RESULT")
    val flag = ContextCompat.RECEIVER_EXPORTED

    ContextCompat.registerReceiver(context, dataReceiver, filter, flag)
  }

  private fun unregisterReceiver() {
    context.unregisterReceiver(dataReceiver)
  }

  private fun isScannerAvailable() : Boolean {
    return android.os.Build.BRAND.lowercase() == "newland"
  }

  private fun configureScanner(key: ScannerConfigKey, value: String) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }

  private fun configureScanner(key: ScannerConfigKey, value: Int) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }

  private fun configureScanner(key: ScannerConfigKey, value: Long) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }

  private fun configureScanner(key: ScannerConfigKey, value: Short) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }

  private fun configureScanner(key: ScannerConfigKey, value: Byte) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }

  private fun configureScanner(key: ScannerConfigKey, value: Float) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }

  private fun configureScanner(key: ScannerConfigKey, value: Double) {
    val intent = Intent("ACTION_BAR_SCANCFG")

    intent.putExtra(key.value, value)
    context.sendBroadcast(intent)
  }
}
