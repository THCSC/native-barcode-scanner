import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to NativeBarcodeScanner.web.ts
// and on native platforms to NativeBarcodeScanner.ts
import NativeBarcodeScannerModule from './NativeBarcodeScannerModule';
import NativeBarcodeScannerView from './NativeBarcodeScannerView';
import { ChangeEventPayload, NativeBarcodeScannerViewProps } from './NativeBarcodeScanner.types';

// Get the native constant value.
export const PI = NativeBarcodeScannerModule.PI;

export function hello(): string {
  return NativeBarcodeScannerModule.hello();
}

export async function setValueAsync(value: string) {
  return await NativeBarcodeScannerModule.setValueAsync(value);
}

const emitter = new EventEmitter(NativeBarcodeScannerModule ?? NativeModulesProxy.NativeBarcodeScanner);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { NativeBarcodeScannerView, NativeBarcodeScannerViewProps, ChangeEventPayload };
