/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import Engine.DESEngine;
import Engine.DESQueue;
import Engine.IOBinary;
import KeyGenerator.KeyGenerator;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    JLabel sourceFileLabel, resultFileLabel, sourceFileName, resultFileName;
    JTextField keyField;
    ButtonGroup operationButton, modeButton;
    JRadioButton code, decode, console, file;//operationButton, modeButton; 
    JTextArea source, result;
    JScrollPane jSP1, jSP2;
    JButton run, exit, loadFile, saveFile;
    String sourceFilePath, resultFilePath;
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
        exit.setBounds(325, 670, 110, 25);
        exit.addActionListener(this);
        add(exit);

        // kontrolki dla pliku
        sourceFileLabel = new JLabel("plik wejsciowy:");
        sourceFileLabel.setBounds(50, 100, 100, 25);
        sourceFileLabel.setVisible(false);
        add(sourceFileLabel);

        resultFileLabel = new JLabel("plik wyjsciowy:");
        resultFileLabel.setBounds(50, 150, 100, 25);
        resultFileLabel.setVisible(false);
        add(resultFileLabel);

        sourceFileName = new JLabel();
        sourceFileName.setBounds(160, 100, 100, 25);
        sourceFileName.setVisible(false);
        add(sourceFileName);

        resultFileName = new JLabel();
        resultFileName.setBounds(160, 150, 100, 25);
        resultFileName.setVisible(false);
        add(resultFileName);

        loadFile = new JButton("wybierz plik");
        loadFile.setBounds(350, 100, 125, 25);
        loadFile.addActionListener(this);
        loadFile.setVisible(false);
        add(loadFile);

        saveFile = new JButton("wybierz plik");
        saveFile.setBounds(350, 150, 125, 25);
        saveFile.addActionListener(this);
        saveFile.setVisible(false);
        add(saveFile);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        revalidate();
        repaint();
    }

    private void Visibility(Boolean vis) { // true dla console, false dla file
        jSP1.setVisible(vis);
        jSP2.setVisible(vis);
        loadFile.setVisible(!vis);
        saveFile.setVisible(!vis);
        sourceFileLabel.setVisible(!vis);
        sourceFileName.setVisible(!vis);
        resultFileLabel.setVisible(!vis);
        resultFileName.setVisible(!vis);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new DES();       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == console) {
            Visibility(true);
        } else if (event == file) {
            Visibility(false);
        } else if (event == code) {
            flag = 1;
        } else if (event == decode) {
            flag = 2;
        } else if (event == run) {
            if (jSP1.isVisible()) {
                //DESEngine des = new DESEngine(keyField.getText());
                DESQueue des = new DESQueue();
                if (flag == 1) {
                    result.setText(des.Encode(source.getText(), keyField.getText()));
                } else {
                    result.setText(des.Decode(source.getText(), keyField.getText()));
                }
            } else {
                IOBinary io = new IOBinary();
                try {
                    io.openStreams(sourceFilePath, resultFilePath, keyField.getText());
                } catch (/*FileNotFoundException ex,*/ IOException ie) {
                    Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ie);
                    //System.err.println("lol");
                }
                if (flag == 1) {
                    try {
                        long t1 = System.currentTimeMillis();
                        io.ReadEncode();
                        long t2 = System.currentTimeMillis();
                        System.out.println(t2 - t1);
                    } catch (IOException ex) {
                        Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    try {
                        long t1 = System.currentTimeMillis();
                        io.ReadDecode();
                        long t2 = System.currentTimeMillis();
                        System.out.println(t2 - t1);
                    } catch (IOException ex) {
                        Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }                
            }
        } else if (event == loadFile) {
            Frame a = null;
            FileDialog fd = new FileDialog(a, "Wczytaj", FileDialog.LOAD);
            fd.setFile("*.txt;*.bin");
            fd.setVisible(true);
            String file = fd.getFile();
            String directory = fd.getDirectory();
            sourceFileName.setText(file);
            sourceFilePath = directory + file;
            resultFileName.setText(file.substring(0, file.lastIndexOf('.')) + "_result" + file.substring(file.lastIndexOf('.'), file.length()));
            resultFilePath = directory + resultFileName.getText();
        } else if (event == saveFile) {
            Frame a = null;
            FileDialog fd = new FileDialog(a, "Zapisz", FileDialog.SAVE);
            fd.setFile("*.txt;*.bin");
            fd.setVisible(true);
            resultFileName.setText(fd.getFile());
            resultFilePath = fd.getDirectory() + fd.getFile();
        } else if (event == exit) {
            System.exit(0);
        }
    }
}
