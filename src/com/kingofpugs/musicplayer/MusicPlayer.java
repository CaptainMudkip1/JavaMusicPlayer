package com.kingofpugs.musicplayer;

import jaco.mp3.player.MP3Player;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class MusicPlayer extends JWindow implements ActionListener {
    JFrame window = new JFrame ("Music Player");
    JLabel info = new JLabel("Made by CaptainMudkip1");
    JButton addButton = new JButton("Add Music");
    JButton playButton = new JButton("Play");
    JButton stopButton = new JButton("Stop");
    Font font = new Font("",Font.BOLD,20);
    JComboBox list = new JComboBox();
    JFileChooser browser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio files (*.wav, *.mp3)","wav", "mp3");
    int returnValue;
    String[] music = new String[10];
    int index = 0;
    File selectedFile;
    String Filename;
    String Extension;
    File sound;
    AudioInputStream ais;
    Clip clip;
    MP3Player mp3player;
    File mp3;


    MusicPlayer() {

        addButton.addActionListener(this);
        playButton.addActionListener(this);
        stopButton.addActionListener(this);

        window.setFont(font);

        info.setFont(new Font("",Font.ITALIC,12));
        window.add(info,BorderLayout.PAGE_END);


        window.add(addButton,BorderLayout.LINE_START);
        window.add(playButton,BorderLayout.CENTER);
        window.add(stopButton,BorderLayout.LINE_END);

        window.add(list,BorderLayout.PAGE_START);

        browser.setFileFilter(filter);

        window.setSize(400,200);
        window.setLocation(200,100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        java.net.URL url = ClassLoader.getSystemResource("com/kingofpugs/musicplayer/icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        window.setIconImage(img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton) {
            returnValue = browser.showOpenDialog(window);

            if(returnValue==browser.APPROVE_OPTION) {
                selectedFile = browser.getSelectedFile();
                music[index] = selectedFile.toString();
                Filename = FilenameUtils.getBaseName(selectedFile.getName());
                list.addItem(Filename);
                index++;
            }
        }
        else if(e.getSource()==playButton) {
            try{
                Extension = FilenameUtils.getExtension(music[list.getSelectedIndex()]).toLowerCase();

                if(Extension.equals("wav")) {
                    if(mp3player!=null) {
                        mp3player.stop();
                    }
                    if (clip == null) {
                        clip = AudioSystem.getClip();
                    }
                    clip.stop();
                    clip = AudioSystem.getClip();
                    sound = new File(music[list.getSelectedIndex()]);
                    ais = AudioSystem.getAudioInputStream(sound);
                    clip.open(ais);
                    clip.start();
                } else if(Extension.equals("mp3")) {
                    if(clip!=null) {
                        clip.stop();
                    }
                    if(mp3player!=null && !mp3player.isStopped()) {
                        mp3player.stop();
                    }
                    mp3 = new File(music[list.getSelectedIndex()]);
                    mp3player = new MP3Player(mp3);
                    mp3player.play();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: File format not supported.");
                }
            }catch(Exception err){JOptionPane.showMessageDialog(null, "Error: "+err);}
        }
        else if(e.getSource()==stopButton) {
            if(clip!=null) {
                clip.stop();
            }
            if(mp3player!=null) {
                mp3player.stop();
            }
        }
    }
}
