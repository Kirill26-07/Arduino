# Arduino [![](https://jitpack.io/v/Kirill26-07/Arduino.svg)](https://jitpack.io/#Kirill26-07/Arduino)
This library provide communication between your Java application and Arduino controller thought serial port.

#### Maven dependences
If you use Maven, you can add dependences through [Jitpack](https://jitpack.io/#Kirill26-07/Arduino),
Or add .jar in your project.

### How to use?

```java
public class TestSerial {

private static final String SERIAL_PORT = "/dev/cu.usbmodem1411"; // Create constant, which include you serial port you use to connect your Arduino controller
private static final int SERIAL_SPEED = 9600; // Create constant, which include max connection speed

private static Arduino arduinoTest = new Arduino(SERIAL_PORT, SERIAL_SPEED); // Create object 

 public static void main(String[] args) throws InterruptedException {
 
  boolean connected = arduinoTest.openConnection();
  System.out.println("Connection success: " + connected);
  Thread.sleep(1000);
  byte[] inputData = arduinoTest.bytesSerialRead(2);
  }
}
```
