import * as NativeBarcodeScanner from "native-barcode-scanner";
import { useEffect, useState } from "react";
import {
  NativeSyntheticEvent,
  Pressable,
  StyleSheet,
  Text,
  TextInput,
  TextInputChangeEventData,
  View,
} from "react-native";

export default function App() {
  const [barcodeData, setBarcodeData] = useState("");
  const [scannerState, setScannerState] =
    useState<NativeBarcodeScanner.ScannerState>(
      NativeBarcodeScanner.getScannerState()
    );
  const [notificationSound, setNotificationSound] =
    useState<NativeBarcodeScanner.AutoEnter>("on");
  const [scanInterval, setScanInterval] = useState(50);

  useEffect(() => {
    NativeBarcodeScanner.setScannerConfig({
      autoEnter: "off",
      notificationSound,
      scanMode: "api",
      scanInterval,
      encoding: "AUTO",
    });
  }, [notificationSound, scanInterval]);

  useEffect(() => {
    const subscription = NativeBarcodeScanner.addBarcodeListener((e) => {
      if (e.ok) {
        setBarcodeData((prev) => prev + e.barcode1);
      }
    });

    return () => subscription.remove();
  }, [setBarcodeData]);

  useEffect(() => {
    const subscription = NativeBarcodeScanner.addScannerListener((e) => {
      setScannerState(e.state);
    });

    return () => subscription.remove();
  }, [setScannerState]);

  const onChangeHandler = (
    event: NativeSyntheticEvent<TextInputChangeEventData>
  ) => {
    let value = parseInt(event.nativeEvent.text, 10);

    if (value < 50) value = 50;

    setScanInterval(value);
  };

  return (
    <View style={styles.container}>
      <Text>
        Scanner available: {NativeBarcodeScanner.isScannerAvailable() ? "true" : "false"}
      </Text>
      <Text>Scanner state: {scannerState}</Text>
      <Text>Barcode data: {barcodeData}</Text>
      <TextInput
        keyboardType="numeric"
        onChange={onChangeHandler}
        value={`${scanInterval}`}
      />
      <Pressable
        onPress={() =>
          setNotificationSound((prev) => (prev === "off" ? "on" : "off"))
        }
      >
        <Text>Toggle new line</Text>
      </Pressable>
      {scannerState === "off" && (
        <Pressable onPress={() => NativeBarcodeScanner.setScannerState("on")}>
          <Text>Enable scanner</Text>
        </Pressable>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
    gap: 10,
  },
});
