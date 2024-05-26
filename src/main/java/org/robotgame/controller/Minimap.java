package org.robotgame.controller;

import org.robotgame.controller.entities.Enemies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Minimap extends JPanel {
    private final Timer m_timer = initTimer();
    private GameVisualizer gameVisualizer;
    private Image backgroundImage;
    private Image resourceImage;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public Minimap(GameVisualizer gameVisualizer) {
        this.gameVisualizer = gameVisualizer;

        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("map/map.jpg"));
            resourceImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/resources.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 7);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                repaint();
            }
        });

        setDoubleBuffered(true);
        setFocusable(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        double x = gameVisualizer.getRobot().getPositionX();
        double y = gameVisualizer.getRobot().getPositionY();

        drawRobot(g2d, x, y);
        drawResources(g2d);
        drawEnemies(g2d);


        if (gameVisualizer.getBase().getBaseBuilt()) {
            x = gameVisualizer.getBase().getPositionX();
            y = gameVisualizer.getBase().getPositionY();

            drawBase(g2d, x, y);
        }
    }


    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 5, centerY - diam2 / 5, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, double x, double y){
        double scale = (double) getWidth() / gameVisualizer.getCameraMap().getMapSizeX();

        int robotCenterX = (int) (x * scale);
        int robotCenterY = (int) (y * scale);

        g.setColor(Color.GREEN);
        fillOval(g, robotCenterX, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 5, 5);
    }

    private void drawBase(Graphics2D g, double x, double y){
        double scale = (double) getWidth() / gameVisualizer.getCameraMap().getMapSizeX();

        int baseCenterX = (int) (x * scale);
        int baseCenterY = (int) (y * scale);

        double hpBase = gameVisualizer.getBase().getHealthPoint();

        g.setColor(Color.WHITE);
        g.fillRect(baseCenterX-10, baseCenterY-5, 25, 2);
        if (hpBase==0) {g.setColor(Color.RED);}
        else if(hpBase<50){g.setColor(Color.ORANGE);}
        else {g.setColor(Color.GREEN);}
        g.fillRect(baseCenterX, baseCenterY, 5, 5);
        g.fillRect(baseCenterX-10, baseCenterY-5, (int)(25*(hpBase/100.0)), 2);
        g.setColor(Color.BLACK);
        g.drawRect(baseCenterX, baseCenterY, 5, 5);
        g.drawRect(baseCenterX-10, baseCenterY-5, 25, 2);
    }

    private void drawResources(Graphics2D g){
        double scale = (double) getWidth() / gameVisualizer.getCameraMap().getMapSizeX();

        ArrayList<ArrayList<Integer>> arrayResources = gameVisualizer.getResources().getResources();

        for (ArrayList<Integer> arrayResource : arrayResources) {
            int resX = (int)(arrayResource.get(0) * scale);
            int resY = (int)(arrayResource.get(1) * scale);
            if (Math.abs(arrayResource.get(2)-100) < 100){
                g.drawImage(resourceImage, resX, resY, 10, 5, this);
            }
        }
    }
    private void drawEnemies(Graphics2D g){
        double scale = (double) getWidth() / gameVisualizer.getCameraMap().getMapSizeX();

        ArrayList<Enemies> enemies = gameVisualizer.getEnemies();
        if (!enemies.isEmpty()) {
            for (Enemies enemy : enemies) {
                int enemyCenterX = (int) (enemy.getPositionX() * scale);
                int enemyCenterY = (int) (enemy.getPositionY() * scale);

                g.setColor(Color.RED);
                fillOval(g, enemyCenterX, enemyCenterY, 5, 5);
                g.setColor(Color.BLACK);
                drawOval(g, enemyCenterX, enemyCenterY, 5, 5);
            }
        }
    }
}
