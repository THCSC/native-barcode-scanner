// Import the native module. On web, it will be resolved to NativeBarcodeScanner.web.ts
// and on native platforms to NativeBarcodeScanner.ts
import { EventEmitter, Subscription } from "expo-modules-core";

import NativeBarcodeScannerModule from "./NativeBarcodeScannerModule";
export type Enabler = "off" | "on";

export type AutoEnter = Enabler;
export type ScanMode = "direct" | "simulate" | "api";
export type ScanEncoding =
  | "UTF-8"
  | "GBK"
  | "ISO-8859-1"
  | "AUTO"
  | "Other"
  | "windows-1251";

export type ScannerConfig = {
  autoEnter?: AutoEnter;
  scanMode?: ScanMode;
  scanInterval?: number;
  notificationSound?: Enabler;
  notificationVibration?: Enabler;
  notificationLED?: Enabler;
  encoding?: ScanEncoding;
};

export function setScannerConfig(config: ScannerConfig) {
  console.log(config);

  if (config.autoEnter) {
    NativeBarcodeScannerModule.autoEnter = config.autoEnter;
  }

  if (config.scanMode) {
    NativeBarcodeScannerModule.scanMode = config.scanMode;
  }

  if (config.scanInterval && config.scanInterval >= 50) {
    NativeBarcodeScannerModule.scanInterval = config.scanInterval;
  }

  if (config.notificationSound) {
    NativeBarcodeScannerModule.notificationSound = config.notificationSound;
  }

  if (config.notificationVibration) {
    NativeBarcodeScannerModule.notificationVibration =
      config.notificationVibration;
  }

  if (config.notificationLED) {
    NativeBarcodeScannerModule.notificationLED = config.notificationLED;
  }

  if (config.encoding) {
    NativeBarcodeScannerModule.encoding = config.encoding;
  }
}

export function isScannerAvailable(): boolean {
  return NativeBarcodeScannerModule.scannerAvailable();
}

export function modelNumber(): string {
  return NativeBarcodeScannerModule.modelNumber();
}

export function brand(): string {
  return NativeBarcodeScannerModule.brand();
}

export function getScannerState(): ScannerState {
  return NativeBarcodeScannerModule.getScanner();
}

export function setScannerState(value: ScannerState): void {
  NativeBarcodeScannerModule.setScanner(value);
}

const emitter = new EventEmitter(NativeBarcodeScannerModule);

export type BarcodeDataReceivedEvent = {
  barcode1: string;
  barcode2: string;
  barcodeType: number;
  ok: boolean;
};

export type ScannerState = Enabler;
export type ScannerStateChangeEvent = {
  state: ScannerState;
};

export function addBarcodeListener(
  listener: (event: BarcodeDataReceivedEvent) => void
): Subscription {
  return emitter.addListener<BarcodeDataReceivedEvent>(
    "onBarcodeDataReceived",
    listener
  );
}

export function addScannerListener(
  listener: (event: ScannerStateChangeEvent) => void
): Subscription {
  return emitter.addListener<ScannerStateChangeEvent>(
    "onScannerStateChange",
    listener
  );
}
