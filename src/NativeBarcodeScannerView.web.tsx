import * as React from 'react';

import { NativeBarcodeScannerViewProps } from './NativeBarcodeScanner.types';

export default function NativeBarcodeScannerView(props: NativeBarcodeScannerViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
