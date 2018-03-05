package com.arduinoSerialJ;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

public class Arduino {

    private SerialPort comPort;
    private String portDescription;
    private int baud_rate;

    public Arduino() {
        //empty constructor if port undecided
    }
    public Arduino(final String portDescription) {
        //make sure to set baud rate after
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    public Arduino(final String portDescription, final int baud_rate) {
        //preferred constructor
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
        this.baud_rate = baud_rate;
        comPort.setBaudRate(this.baud_rate);
    }

    public boolean openConnection(){
        if(comPort.openPort()){

            try {
                Thread.sleep(100);
            } catch(Exception e){
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

    public void closeConnection() {
        comPort.closePort();
    }

    public void setPortDescription(final String portDescription){
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    public void setBaudRate(final int baud_rate){
        this.baud_rate = baud_rate;
        comPort.setBaudRate(this.baud_rate);
    }

    public String getPortDescription(){
        return portDescription;
    }

    public SerialPort getSerialPort(){
        return comPort;
    }


    public String serialRead(){
        //will be an infinite loop if incoming data is not bound
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        StringBuilder out = new StringBuilder();
        Scanner in = new Scanner(comPort.getInputStream());

        try
        {
            while(in.hasNext()) {
                out.append(in.next()).append("\n");
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public String serialRead(final int limit){
        //in case of unlimited incoming data, set a limit for number of readings
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        StringBuilder out = new StringBuilder();

        int count = 0;
        Scanner in = new Scanner(comPort.getInputStream());

        try
        {
            while(count <= limit && in.hasNext()) {
                out.append(in.next()).append("\n");
                count++;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public void serialWrite(final String s){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(Exception e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.print(s);
        pout.flush();

    }
    public void serialWrite(final String s, final int noOfChars, final int delay){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(Exception e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());

        for(int i = 0; i < s.length(); i += noOfChars){
            pout.write(s.substring(i,i+noOfChars));
            pout.flush();
            System.out.println(s.substring(i, i + noOfChars));

            try{
                Thread.sleep(delay);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        pout.write(noOfChars);
        pout.flush();

    }

    public void serialWrite(final char c){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(Exception e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());pout.write(c);
        pout.flush();
    }

    public void serialWrite(final char c, final int delay){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try{
            Thread.sleep(5);
        } catch(Exception e){
            e.printStackTrace();
        }

        PrintWriter pout = new PrintWriter(comPort.getOutputStream());pout.write(c);
        pout.flush();

        try{
            Thread.sleep(delay);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
