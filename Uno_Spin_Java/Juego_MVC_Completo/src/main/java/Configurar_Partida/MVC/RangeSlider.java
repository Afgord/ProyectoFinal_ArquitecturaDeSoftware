/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configurar_Partida.MVC;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 * Slider simple de dos controles para seleccionar un rango entero.
 * Diseñado para el rango numérico de cartas del CU1.
 */
public class RangeSlider extends JComponent {

    private int minValue = 0;
    private int maxValue = 9;

    private final int absoluteMin = 0;
    private final int absoluteMax = 9;

    private final int thumbRadius = 8;
    private final int trackHeight = 5;

    private boolean draggingMin = false;
    private boolean draggingMax = false;

    public RangeSlider() {
        setPreferredSize(new Dimension(380, 40));

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int xMin = valueToX(minValue);
                int xMax = valueToX(maxValue);

                if (Math.abs(e.getX() - xMin) <= thumbRadius + 4) {
                    draggingMin = true;
                } else if (Math.abs(e.getX() - xMax) <= thumbRadius + 4) {
                    draggingMax = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggingMin = false;
                draggingMax = false;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int value = xToValue(e.getX());

                if (draggingMin) {
                    setMinValue(value);
                } else if (draggingMax) {
                    setMaxValue(value);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMinValue(int value) {
        int nuevoValor = clamp(value, absoluteMin, maxValue);

        if (nuevoValor != minValue) {
            minValue = nuevoValor;
            firePropertyChange("range", null, null);
            repaint();
        }
    }

    public void setMaxValue(int value) {
        int nuevoValor = clamp(value, minValue, absoluteMax);

        if (nuevoValor != maxValue) {
            maxValue = nuevoValor;
            firePropertyChange("range", null, null);
            repaint();
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private int valueToX(int value) {
        int leftPadding = thumbRadius + 4;
        int usableWidth = getWidth() - (leftPadding * 2);

        double porcentaje = (double) (value - absoluteMin) / (absoluteMax - absoluteMin);
        return leftPadding + (int) Math.round(porcentaje * usableWidth);
    }

    private int xToValue(int x) {
        int leftPadding = thumbRadius + 4;
        int usableWidth = getWidth() - (leftPadding * 2);

        double porcentaje = (double) (x - leftPadding) / usableWidth;
        porcentaje = Math.max(0, Math.min(1, porcentaje));

        return (int) Math.round(absoluteMin + porcentaje * (absoluteMax - absoluteMin));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int yCentro = getHeight() / 2;

        int xInicio = valueToX(absoluteMin);
        int xFin = valueToX(absoluteMax);
        int xMin = valueToX(minValue);
        int xMax = valueToX(maxValue);

        // Barra base
        g2.setColor(new Color(225, 228, 235));
        g2.fillRoundRect(
                xInicio,
                yCentro - trackHeight / 2,
                xFin - xInicio,
                trackHeight,
                trackHeight,
                trackHeight
        );

        // Barra seleccionada
        GradientPaint gradiente = new GradientPaint(
                xMin, yCentro, new Color(40, 102, 245),
                xMax, yCentro, new Color(235, 0, 35)
        );
        g2.setPaint(gradiente);
        g2.fillRoundRect(
                xMin,
                yCentro - trackHeight / 2,
                xMax - xMin,
                trackHeight,
                trackHeight,
                trackHeight
        );

        // Punto mínimo
        g2.setColor(new Color(40, 102, 245));
        g2.fillOval(
                xMin - thumbRadius,
                yCentro - thumbRadius,
                thumbRadius * 2,
                thumbRadius * 2
        );

        // Punto máximo
        g2.setColor(new Color(235, 0, 35));
        g2.fillOval(
                xMax - thumbRadius,
                yCentro - thumbRadius,
                thumbRadius * 2,
                thumbRadius * 2
        );

        g2.dispose();
    }
}