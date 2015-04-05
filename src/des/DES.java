/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import Engine.DESEngine;
import Engine.DESQueue;
import KeyGenerator.KeyGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Konrad
 */
public class DES extends JFrame implements ActionListener {

    JLabel operationLabel, modeLabel, keyLabel; // operationLabel - kodowanie, dekodowanie; modeLabel - konsola, plik
    /*String[] modeStrings = {"kodowanie",
        "dekodowanie"};*/
    JTextField keyField;
    ButtonGroup operationButton, modeButton;
    JRadioButton code, decode, console, file;//operationButton, modeButton; 
    JTextArea source, result;
    JScrollPane jSP1, jSP2;
    JButton run, exit;
    int flag = 1; // 1 - kodowanie, 2 - dekodowanie

    public DES() {
        setVisible(true);
        setLayout(null);
        setSize(600, 750);
        
        keyLabel = new JLabel("klucz:");
        keyLabel.setBounds(50, 10, 50, 30);
        add(keyLabel);
        
        keyField = new JTextField();
        keyField.setBounds(90, 15, 200, 25);
        add(keyField);

        operationLabel = new JLabel("Tryb:");
        operationLabel.setBounds(50, 40, 50, 20);
        add(operationLabel);
        
        code = new JRadioButton("kodowanie");
        code.setBounds(90, 40, 90, 20);
        code.addActionListener(this);
        code.setSelected(true);        
        add(code);
        decode = new JRadioButton("dekodowanie");
        decode.setBounds(190, 40, 110, 20);
        decode.addActionListener(this);
        add(decode);
        
        operationButton = new ButtonGroup();
        operationButton.add(code);
        operationButton.add(decode);

        modeLabel = new JLabel("Tryb:");
        modeLabel.setBounds(50, 70, 100, 20);
        add(modeLabel);        

        console = new JRadioButton("konsola");
        console.setBounds(90, 70, 75, 20);
        console.addActionListener(this);
        console.setSelected(true);
        add(console);
        file = new JRadioButton("plik");
        file.setBounds(190, 70, 50, 20);
        file.addActionListener(this);
        add(file);

        modeButton = new ButtonGroup();
        modeButton.add(console);
        modeButton.add(file);

        source = new JTextArea();
        jSP1 = new JScrollPane(source);

        jSP1.setBounds(50, 100, 485, 250);
        add(jSP1);

        result = new JTextArea();
        result.setEditable(false);
        jSP2 = new JScrollPane(result);

        jSP2.setBounds(50, 400, 485, 250);
        add(jSP2);
        
        run = new JButton("wykonaj");
        run.setBounds(150, 670, 100, 25);
        run.addActionListener(this);
        add(run);
        
        exit = new JButton("wyjscie");
        exit.setBounds(325, 670, 100, 25);
        exit.addActionListener(this);
        add(exit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        revalidate();
        repaint();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new DES();
        //String key = "133457799BBCDFF1";
        //ASCIItoBIN asc = new ASCIItoBIN();
        //System.out.println(asc.ASCIItoBIN("X"));

        /*String key = "3b3898371520f75e";
        long t1, t2;
        
        t1 = System.currentTimeMillis();
        DESQueue dq = new DESQueue();
        dq.Stream(key);
        for (int i = 0; i < 40000; i++) {
          dq.EncodeFromFile("ABCDEFGH");
        }
        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        

        t1 = System.currentTimeMillis();
        KeyGenerator kgba = new KeyGenerator(key);
        DESEngine eba = new DESEngine(kgba);
                
        for (int i = 0; i < 40000; i++) {
          eba.Encode("ABCDEFGH");
        }
        //System.out.println(eba.Encode("ABCDEFGH"));
        //System.out.println(eba.Decode("58608465B4472AB1"));
        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);*/
        

        //DataInputStream read = new DataInputStream(new FileInputStream("test.bin"));
        /* System.out.println(read.available());
         System.out.println(read.read() + " " + read.read() + read.read() + read.read() + read.read() + read.read() + read.read() + read.read());
         System.out.println(read.read() + " " + read.read() + read.read() + read.read() + read.read() + read.read() + read.read() + read.read());
         System.out.println(read.read() + " " + read.read() + read.read() + read.read() + read.read() + read.read() + read.read() + read.read());
         System.out.println(read.read() + " " + read.read() + read.read() + read.read() + read.read() + read.read() + read.read() + read.read());
         System.out.println(read.read() + " " + read.read() + read.read() + read.read() + read.read() + read.read() + read.read() + read.read());
         */
        /*PrintWriter pw = new PrintWriter(System.out, true);
        pw.println(read.available());
        int temp, z = 0;
        byte by;
        for (int i = 0; i < read.available(); i++) {
            //temp = read.read();
            temp = read.read();
            if (temp != 0) {
                pw.println(temp);
                z++;
            }
            //pw.println(read.readByte());
        }
        pw.println("Wartosc z: " + z);*/

        //readBin();
        //System.out.println(read.read());
    }

    public static void readBin() {
        String fileName = "test2.bin";

        try {
            // Use this for reading the data.
            byte[] buffer = new byte[1000];

            FileInputStream inputStream
                    = new FileInputStream(fileName);

            // read fills buffer with data and returns
            // the number of bytes read (which of course
            // may be less than the buffer size, but
            // it will never be more).
            int total = 0;
            int nRead = 0;
            while ((nRead = inputStream.read(buffer)) != -1) {
                // Convert to String so we can display it.
                // Of course you wouldn't want to do this with
                // a 'real' binary file.
                System.out.println(new String(buffer));
                total += nRead;
            }

            // Always close files.
            inputStream.close();

            System.out.println("Read " + total + " bytes");
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == console) {
            jSP1.setVisible(true);
            jSP2.setVisible(true);
            //System.out.println(flag);
        } else if (event == file) {
            jSP1.setVisible(false);
            jSP2.setVisible(false);
            //System.out.println(flag);
        }else if (event == code) {
            flag = 1;
        }else if (event == decode) {
            flag = 2;
        }else if (event == run) {
            //DESEngine des = new DESEngine(keyField.getText());
            DESQueue des = new DESQueue();
            if(flag == 1){
                result.setText(des.Encode(source.getText(), keyField.getText()));
            }else{
                result.setText(des.Decode(source.getText(), keyField.getText()));
            }
        }else if (event == exit) {
            System.exit(0);
        }
    }
}
