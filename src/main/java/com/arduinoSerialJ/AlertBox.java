package com.arduinoSerialJ;

import javax.swing.*;
import java.awt.*;

public class AlertBox {

    private JFrame alertWindow;

    AlertBox(final Dimension obj, final String title, final String message) {
        alertWindow = new JFrame();
        alertWindow.setTitle(title);
        alertWindow.setSize(obj);
        alertWindow.setPreferredSize(obj);
        alertWindow.setLayout(new BorderLayout());
        alertWindow.setLocationRelativeTo(null);

        JLabel lblMessage = new JLabel(message, SwingConstants.CENTER);
        JButton btnOk = new JButton("Ok");

        btnOk.addActionListener(e -> {
            alertWindow.setVisible(false);
            alertWindow.dispose();
        });

        alertWindow.add(btnOk, BorderLayout.SOUTH);
        alertWindow.add(lblMessage, BorderLayout.CENTER);
    }

    public void display(){
        alertWindow.setVisible(true);
    }

}
