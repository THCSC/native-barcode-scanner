import { StyleSheet, Text, View } from 'react-native';

import * as NativeBarcodeScanner from 'native-barcode-scanner';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>{NativeBarcodeScanner.hello()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
