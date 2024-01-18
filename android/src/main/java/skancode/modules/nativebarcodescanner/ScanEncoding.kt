package skancode.modules.nativebarcodescanner

import expo.modules.kotlin.types.Enumerable

enum class ScanEncoding(val value: String) : Enumerable {
    PADDING("0"),
    UTF_8("UTF-8"),
    GBK("GBK"),
    ISO_8859_1("ISO-8859-1"),
    AUTO("AUTO"),
    OTHER("Other"),
    WINDOWS_1251("windows-1251")
}