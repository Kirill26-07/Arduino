/**
 * This library create for connect your Java application with Arduino through Serial connection.
 */

package com.arduinoSerialJ;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

public class Arduino<T> {

    private SerialPort comPort;
    private String portDescription;
    private int baud_rate;

    /**
     * Empty constructor to be used only if port is unknown and it is imperative to initialise object.
     * If this constructor is called, then calling setPortDescription(String portDescription) is essential to set the serial port.
     */
    public Arduino() {
        //empty constructor if port undecided
    }

    /**
     * Parameterised constructor that initialises the arduino object and sets the communication port to that which has been specified.
     *
     * @param portDescription your serial port through Arduino is connected with your computer
     *                        It may write how:
     *                        For Windows  "COM52";
     *                        For Linux and MAC  /dev/cu.usbmodem1411
     */
    public Arduino(final String portDescription) {
        //make sure to set baud rate after
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    /**
     * Parameterised constructor that initialises the arduino object and sets the communication port to that which has been specified.
     * It also sets the baud rate for the serial communication.
     * This is the recommended constructor.
     *
     * @param portDescription your serial port through Arduino is connected with your computer
     *                        It may write how:
     *                        For Windows  "COM52";
     *                        For Linux and MAC  /dev/cu.usbmodem1411
     * @param baud_rate sets the baud rate for the serial communication.
     *                  For Example 9600. You must use baud rate similar of your arduino sketch.
     */
    public Arduino(final String portDescription, final int baud_rate) {
        //preferred constructor
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
        this.baud_rate = baud_rate;
        comPort.setBaudRate(this.baud_rate);
    }

    /**
     * Opens the connection if portDescription has been initialised.
     * Also displays an error message to the user when connection was unsuccessful.
     * Make sure to call this before anything else or exceptions will be thrown.
     *
     * @return boolean depending on whether the connection was successful.
     */
    public boolean openConnection(){
        if(comPort.openPort()){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            AlertBox alert = new AlertBox(new Dimension(400,100),"Error Connecting", "Try Another port");
            alert.display();
            return false;
        }
    }

    /**
     * Closes connection to serial port.
     */
    public void closeConnection() {
        comPort.closePort();
    }

    /**
     * Setter method to change serial port to which the object is attached.
     *
     * @param portDescription port description.
     */
    public void setPortDescription(final String portDescription){
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    /**
     * Sets the baud rate for serial
     *
     * @param baud_rate baud of rate. You must use baud rate similar of your arduino sketch.
     */
    public void setBaudRate(final int baud_rate){
        this.baud_rate = baud_rate;
        comPort.setBaudRate(this.baud_rate);
    }

    /**
     * Getter method.
     * @return returning the String containng the port description.
     */
    public String getPortDescription(){
        return portDescription;
    }

    /**
     *
     * @return returns an object of type SerialPort with the current Serial Port.
     */
    public SerialPort getSerialPort(){
        return comPort;
    }

    /**
     * Runs until there is no more data available in the serial to be read.
     * This may be an infinite loop depending on availability of data.
     *
     * @return all of the data as a string.
     */
    public String serialRead() {
        //will be an infinite loop if incoming data is not bound
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        StringBuilder out = new StringBuilder();
        Scanner in = new Scanner(comPort.getInputStream());


        while (in.hasNext()) {
            out.append(in.next()).append("\n");
        }
        in.close();
        return out.toString();
    }

    /**
     * Returns a string containing as many readings as the value of limit.
     * Recommended for reading
     *
     * @param limit limits of bytes witch do you wont to read from serial port
     * @return input bytes
     */
    public String serialRead(final int limit){
        //in case of unlimited incoming data, set a limit for number of readings
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        StringBuilder out = new StringBuilder();

        int count = 0;
        Scanner in = new Scanner(comPort.getInputStream());

        while(count <= limit && in.hasNext()) {
            out.append(in.next()).append("\n");
            count++;
        }
        in.close();
        return out.toString();
    }

    /**
     * Returns a string array containing as many readings as the value of limit.
     * This method can be use, wen you wont to have data package with split data.
     *
     * @param limit limits of bytes witch do you wont to read from serial port
     * @return input bytes in String array
     */
    public String[] arraySerialRead(final int limit){
        //in case of unlimited incoming data, set a limit for number of readings
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        String[] out = new String[limit + 1];

        int count = 0;
        Scanner in = new Scanner(comPort.getInputStream());

        while(count <= limit && in.hasNext()) {
            out[count] += (in.next());
            count++;
        }
        in.close();
        return out;
    }

    /**
     * Returns a string array containing as many readings as the value of limit.
     * This method can be use, wen you wont to have data package with split data.
     *
     * @param limit limits of bytes witch do you wont to read from serial port
     * @return input bytes in byte array
     */
    public byte[] byteSerialRead(final int limit){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        byte[] out = new byte[limit + 1];

        int count = 0;
        Scanner in = new Scanner(comPort.getInputStream());


        while(count <= limit && in.hasNext()) {
            out[count] = in.nextByte();
            count++;
        }
        in.close();
        return out;
    }

    /**
     * Writes the contents of the entire string to the serial at once. Written as string to serial.
     *
     * @param s output String value
     */
    public void serialWrite(final T s){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.print(s);
        pout.flush();
    }

    /**
     * Writes the contents of the strings to the serial gradually.
     * It writes the string in incremental steps with 'noOfChars' characters each time, with a pause of 'delay' milliseconds between each write.
     * Written as string to serial.
     * Recommended to write String.
     *
     * @param s output String value.
     * @param noOfChars incremental steps.
     * @param delay pause in milliseconds between each write.
     */
    public void stringSerialWrite(final String s, final int noOfChars, final int delay){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());

        for(int i = 0; i < s.length(); i += noOfChars){
            pout.write(s.substring(i,i+noOfChars));
            pout.flush();
            System.out.println(s.substring(i, i + noOfChars));

            try{
                Thread.sleep(delay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        pout.write(noOfChars);
        pout.flush();

    }

    /**
     * Writes the individual char to the serial in data type char.
     *
     * @param c output char value
     */
    public void charSerialWrite(final char c){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.write(c);
        pout.flush();
    }

    /**
     * Writes the individual char to the serial in datatype char and pauses the thread for delay milliseconds after.
     * Recommended to write char.
     *
     * @param c output char value.
     * @param delay pause in milliseconds between each write.
     */
    public void charSerialWrite(final char c, final int delay){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());pout.write(c);
        pout.flush();

        try{
            Thread.sleep(delay);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
