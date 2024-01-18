package skancode.modules.nativebarcodescanner

import expo.modules.kotlin.types.Enumerable

enum class ScanMode(val value: String) : Enumerable {
    PADDING("0"),
    DIRECT("direct"),
    SIMULATE("simulate"),
    API("api")
}