/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class panelAvatar extends JPanel{
    private BufferedImage avatar;
    private URL urlAvatar;

    public panelAvatar(String url) {
        setPreferredSize(new Dimension(70,70));
        setBackground(Color.WHITE);
        try{
            urlAvatar = getClass().getResource(url);
            avatar = ImageIO.read(urlAvatar);
        } catch (IOException e){
            System.out.println("error al cargar la imagen del jugador" + e);
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (avatar != null){
            g.drawImage(avatar, 0, 0, this.getWidth(),this.getHeight() , this);
        } else {
            System.out.println("no se cargo la imagen desde el paintcomponents");
        }
    }
}
