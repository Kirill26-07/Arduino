//package com.arduinoSerialJ;
//
//import org.junit.Rule;
//import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
//import org.junit.jupiter.api.Test;
//import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ArduinoTest {
//
//    private Arduino testInput = new Arduino();
//    private String [] testArray = new String[] {"1", "2", "3"};
//
//    @Rule
//    public final TextFromStandardInputStream systemInMock
//            = emptyStandardInputStream();
//
//    @Test
//    void arraySerialReadTest() {
//
//        systemInMock.provideLines("1", "2", "3");
//
//       assertArrayEquals(testArray, testInput.arraySerialRead(2));
//
//    }
//}