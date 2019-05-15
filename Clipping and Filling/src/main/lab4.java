package main;

import javax.swing.JFrame;

public class lab4{

    public static void main(String[] args) {
        int width = 800;
        int height = 600;
        JFrame frame = new JFrame("Direct draw demo");

        Clipping panel = new Clipping(width, height);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.requestFocus();
          
        
    }
}