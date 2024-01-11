import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { NativeBarcodeScannerViewProps } from './NativeBarcodeScanner.types';

const NativeView: React.ComponentType<NativeBarcodeScannerViewProps> =
  requireNativeViewManager('NativeBarcodeScanner');

export default function NativeBarcodeScannerView(props: NativeBarcodeScannerViewProps) {
  return <NativeView {...props} />;
}
