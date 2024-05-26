package org.robotgame.controller;

import org.robotgame.controller.entities.*;
import org.robotgame.controller.entities.Robot;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameVisualizer extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Timer m_timer = initTimer();
    private final Robot robot;
    private final ArrayList<Enemies> enemies;
    private final Target target;
    private final Base base;
    private final CameraMap cameraMap;
    private final Resources resources;

    private Image backgroundImage;
    private Image resourceImage;

    private ArrayList<Image[]> robotImage;
    private ArrayList<Image[]> EnemiesImage;
    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer(int width, int height) {
        int startRobotX = 100;
        int startRobotY = 100;
        robot = new Robot(startRobotX, startRobotY, 0);
        target = new Target(startRobotX, startRobotY);
        base = new Base();
        cameraMap = new CameraMap(startRobotX, startRobotY, width, height,2000, 2000);
        resources = new Resources();
        enemies = new ArrayList<>();

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("map/map.jpg")));
            resourceImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/resources.jpg")));

            robotImage = new ArrayList<>();

            for (String i:"extracts standing movesLeft movesRight ".split(" ")){
                Image[] robotExtracts = new Image[10];
                for (int i1=0;i1<10;i1++) {
                    robotExtracts[i1] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("robot."+i+"/"+String.valueOf(i1)+".png")));
                }
                robotImage.add(robotExtracts);
            }

            EnemiesImage = new ArrayList<>();

            for (String i:"extracts standing movesLeft movesRight ".split(" ")){
                Image[] EnemiesExtracts = new Image[10];
                for (int i1=0;i1<10;i1++) {
                    EnemiesExtracts[i1] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("enemies."+i+"/"+String.valueOf(i1)+".png")));
                }
                EnemiesImage.add(EnemiesExtracts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 7);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 5);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (robot.getTank() < 200){
                    robot.fillTank(resources.giveResource(robot.getPositionX(), robot.getPositionY()));
                }
            }
        }, 0,50);

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.setAction(resources, target.getPositionX());
                robot.nextId();
            }
        },0,70);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Enemies enemy : enemies) {
                    enemy.setAction(base);
                    enemy.nextId();
                }
            }
        }, 0, 100);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Enemies enemy : enemies) {
                    if (Math.abs(base.getPositionX() - enemy.getPositionX()) <= 25 &&
                            Math.abs(base.getPositionY() - enemy.getPositionY()) <= 25) {
                        enemy.attackBase(base);
                    }
                }
            }
        }, 0, 80);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getX(), e.getY());
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                if (KeyEvent.getKeyText(e.getKeyCode()).equals("B") && !(base.getBaseBuilt()) && robot.getTank() >= 100){
                    robot.giveResource(100);
                    base.buildBase(robot.getPositionX(),robot.getPositionY());
                    spawnEnemies();
                    m_timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (Math.abs(base.getPositionX() - robot.getPositionX()) <= 25 &&
                                    Math.abs(base.getPositionY() - robot.getPositionY()) <= 25){
                                base.takeResources(robot.giveResource(5));
                            }
                        }
                    }, 0,100);
                };
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateScreenSize();
                repaint();
            }
        });

        setDoubleBuffered(true);
        setFocusable(true);
    }

    public void setTargetPosition(int x, int y) {
        x = (int) (cameraMap.getX() + x);
        y = (int) (cameraMap.getY() + y);

        if (this.getWidth() != 0 && this.getHeight() != 0) {
            x = (int) applyLimits(x, 0, cameraMap.getMapSizeX());
            y = (int) applyLimits(y, 0, cameraMap.getMapSizeY());
        }
        if (distance(x, y, target.getPositionX(), target.getPositionY()) < 5)
            return;

        target.setPositionX(x);
        target.setPositionY(y);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public void onModelUpdateEvent() {
        for (Enemies enemy : enemies) {
            double distanceToBase = distance(enemy.getPositionX(), enemy.getPositionY(), base.getPositionX(), base.getPositionY());
            if (distanceToBase < 20) {
                enemy.setVelocity(0);
                continue;
            }
            enemy.setVelocity(1);

            double angleToBase = angleTo(enemy.getPositionX(), enemy.getPositionY(), base.getPositionX(), base.getPositionY());

            double angleDifference;
            if (Math.abs(angleToBase - enemy.getDirection()) > Math.exp(-17)) {
                angleDifference = angleToBase - enemy.getDirection();
            } else {
                angleDifference = Math.exp(-16);
            }

            if (Math.abs(angleDifference) > Math.PI) {
                angleDifference -= Math.signum(angleDifference) * 2 * Math.PI;
            }
            double angularVelocity = enemy.getMaxAngularVelocity() * angleDifference;

            moveEnemy(enemy, angularVelocity, 5);
        }

        double distance = distance(target.getPositionX(), target.getPositionY(), robot.getPositionX(), robot.getPositionY());
        if (distance < 1) {
            robot.setVelocity(0);
            return;
        }
        robot.setVelocity(1);

        double angleToTarget = angleTo(robot.getPositionX(), robot.getPositionY(), target.getPositionX(), target.getPositionY());

        double angleDifference;
        if (Math.abs(angleToTarget - robot.getDirection()) > Math.exp(-17)) {
            angleDifference = angleToTarget - robot.getDirection();
        } else {
            angleDifference = Math.exp(-16);
        }

        if (Math.abs(angleDifference) > Math.PI) {
            angleDifference -= Math.signum(angleDifference) * 2 * Math.PI;
        }
        double angularVelocity = robot.getMaxAngularVelocity() * angleDifference;

        moveRobot(angularVelocity, 5);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double angularVelocity, double duration) {
        double velocity = robot.getMaxVelocity();
        double newX = robot.getPositionX() + velocity / angularVelocity *
                (Math.sin(robot.getDirection() + angularVelocity * duration) -
                        Math.sin(robot.getDirection()));
        if (!Double.isFinite(newX)) {
            newX = robot.getPositionX() + velocity * duration * Math.cos(robot.getDirection());
        }
        double newY = robot.getPositionY() - velocity / angularVelocity *
                (Math.cos(robot.getDirection() + angularVelocity * duration) -
                        Math.cos(robot.getDirection()));
        if (!Double.isFinite(newY)) {
            newY = robot.getPositionY() + velocity * duration * Math.sin(robot.getDirection());
        }
        robot.setPositionX(applyLimits(newX, 0, cameraMap.getMapSizeX()));
        robot.setPositionY(applyLimits(newY, 0, cameraMap.getMapSizeY()));

        double newDirection = asNormalizedRadians(robot.getDirection() + angularVelocity * duration);
        robot.setDirection(newDirection);

        cameraMap.updateCameraMapPosition(robot.getPositionX(), robot.getPositionY());
    }

    private void moveEnemy(Enemies enemy, double angularVelocity, double duration) {
        double velocity = enemy.getMaxVelocity();
        double newX = enemy.getPositionX() + velocity / angularVelocity *
                (Math.sin(enemy.getDirection() + angularVelocity * duration) -
                        Math.sin(enemy.getDirection()));
        if (!Double.isFinite(newX)) {
            newX = enemy.getPositionX() + velocity * duration * Math.cos(enemy.getDirection());
        }
        double newY = enemy.getPositionY() - velocity / angularVelocity *
                (Math.cos(enemy.getDirection() + angularVelocity * duration) -
                        Math.cos(enemy.getDirection()));
        if (!Double.isFinite(newY)) {
            newY = enemy.getPositionY() + velocity * duration * Math.sin(enemy.getDirection());
        }

        enemy.setPositionX(applyLimits(newX, 0, cameraMap.getMapSizeX()));
        enemy.setPositionY(applyLimits(newY, 0, cameraMap.getMapSizeY()));

        double newDirection = asNormalizedRadians(enemy.getDirection() + angularVelocity * duration);
        enemy.setDirection(newDirection);
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private void spawnEnemies() {
        for (int i = 0; i < 5; i++) {
            double positionX = Math.random() * cameraMap.getMapSizeX()-100;
            double positionY = Math.random() * cameraMap.getMapSizeY()-100;
            double direction = Math.random() * 2 * Math.PI;
            enemies.add(new Enemies(positionX, positionY, direction));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double cameraX = cameraMap.getX();
        double cameraY = cameraMap.getY();

        drawBackgroundImage(g2d, cameraX, cameraY);
        drawResources(g2d);
        drawRobot(g2d, robot.getPositionX() - cameraX, robot.getPositionY() - cameraY, robot.getDirection());
        drawTarget(g2d, target.getPositionX() - cameraX, target.getPositionY() - cameraY);
        if (base.getBaseBuilt()) {
            drawBase(g2d, base.getPositionX() - cameraX, base.getPositionY() - cameraY);
        }
        drawRobotResources(g2d);
        drawEnemies(g2d);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, double x, double y, double direction) {

        int robotCenterX = round(x);
        int robotCenterY = round(y);

        if (Objects.equals(robot.getAction(), "standing")) {
            g.drawImage(robotImage.get(1)[robot.getIdImage()], robotCenterX - 30, robotCenterY - 70, 70, 70, this);
        }
        if (Objects.equals(robot.getAction(), "extracts")) {
            g.drawImage(robotImage.get(0)[robot.getIdImage()], robotCenterX - 30, robotCenterY - 70, 70, 70, this);
        }
        if (Objects.equals(robot.getAction(), "movesLeft")) {
            g.drawImage(robotImage.get(2)[robot.getIdImage()], robotCenterX - 30, robotCenterY - 70, 70, 70, this);
        }
        if (Objects.equals(robot.getAction(), "movesRight")) {
            g.drawImage(robotImage.get(3)[robot.getIdImage()], robotCenterX - 30, robotCenterY - 70, 70, 70, this);
        }
    }

    private void drawTarget(Graphics2D g, double x, double y) {
        int targetCenterX = round(x);
        int targetCenterY = round(y);

        g.setColor(Color.RED);
        fillOval(g, targetCenterX, targetCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, targetCenterX, targetCenterY, 5, 5);
    }

    private void drawBase(Graphics2D g, double x, double y){
        int baseCenterX = round(x);
        int baseCenterY = round(y);

        if (base.getHealthPoint() == 0) {g.setColor(Color.GRAY);}
        else if (base.getHealthPoint()<50){g.setColor(Color.RED);}
        else {g.setColor(Color.ORANGE);}
        g.fill3DRect(baseCenterX-25, baseCenterY-25, 50, 50, true);
        g.setColor(Color.BLACK);
        g.draw3DRect(baseCenterX-25, baseCenterY-25, 50, 50, true);
        g.drawRect(baseCenterX-20, baseCenterY-20, 40, 5);
        g.setColor(Color.WHITE);
        g.fillRect(baseCenterX-20, baseCenterY-20, 40, 5);
        g.setColor(Color.GREEN);
        g.fillRect(baseCenterX-20, baseCenterY-20, (int)(40*(base.getHealthPoint()/100.0)), 5);

    }

    private void drawBackgroundImage(Graphics2D g, double cameraX, double cameraY) {
        int destX = 0; // Координата X верхнего левого угла области
        int destY = 0; // Координата Y верхнего левого угла области
        int destWidth = getWidth(); // Ширина области
        int destHeight = getHeight(); // Высота области

        int srcX = (int) cameraX; // Начальная координата X на изображении
        int srcY = (int) cameraY; // Начальная координата Y на изображении
        int srcWidth = getWidth(); // Ширина области на изображении
        int srcHeight = getHeight(); // Высота области на изображении

        g.drawImage(backgroundImage, destX, destY, destWidth, destHeight, srcX, srcY, srcX + srcWidth, srcY + srcHeight, this);
    }

    private void drawResources(Graphics2D g){
        ArrayList<ArrayList<Integer>> arrayResources = resources.getResources();
        int cameraX = (int)cameraMap.getX();
        int cameraY = (int)cameraMap.getY();
        for (ArrayList<Integer> arrayResource : arrayResources) {
            int resX = arrayResource.get(0);
            int resY = arrayResource.get(1);
            if (arrayResource.get(2) > 0) {
                g.drawImage(resourceImage, resX - cameraX, resY - cameraY, 100, 50, this);
            }
        }
    }

    private void drawRobotResources(Graphics2D g){
        int positionX = 500;
        int positionY = 500;
        int k = 0;
        int robotResources = robot.getTank();

        while (robotResources > 0) {
            int res = Math.min(robotResources, 100);

            g.setColor(Color.WHITE);
            g.fillRect(positionX-k, positionY, 10, 40);
            g.setColor(Color.GREEN);
            g.fillRect(positionX-k, positionY + (int) (40 * (1 - res / 100.0)), 10, (int) (40 * (res / 100.0)));
            g.setColor(Color.BLACK);
            g.drawRect(positionX-k, positionY, 10, 40);

            robotResources = robotResources - 100;
            k = k + 20;
        }
    }

    private void drawEnemies(Graphics2D g) {
        double cameraX = cameraMap.getX();
        double cameraY = cameraMap.getY();

        for (Enemies enemy : enemies) {
            int enemyCenterX = round(enemy.getPositionX() - cameraX);
            int enemyCenterY = round(enemy.getPositionY() - cameraY);

            if (Objects.equals(enemy.getAction(), "standing")) {
                g.drawImage(EnemiesImage.get(1)[enemy.getIdImage()], enemyCenterX - 30, enemyCenterY - 70, 70, 70, this);
            }
            if (Objects.equals(enemy.getAction(), "extracts")) {
                g.drawImage(EnemiesImage.get(0)[enemy.getIdImage()], enemyCenterX - 30, enemyCenterY - 70, 70, 70, this);
            }
            if (Objects.equals(enemy.getAction(), "movesLeft")) {
                g.drawImage(EnemiesImage.get(2)[enemy.getIdImage()], enemyCenterX - 30, enemyCenterY - 70, 70, 70, this);
            }
            if (Objects.equals(enemy.getAction(), "movesRight")) {
                g.drawImage(EnemiesImage.get(3)[enemy.getIdImage()], enemyCenterX - 30, enemyCenterY - 70, 70, 70, this);
            }
        }
    }


    public void updateScreenSize(){
        cameraMap.setScreenSize(getWidth(), getHeight());
    }

    public Point getTargetPoint() {
        return new Point(target.getPositionX(), target.getPositionY());
    }
    public Point getRobotPoint() {
        return new Point((int)robot.getPositionX(), (int)robot.getPositionY());
    }
    public Robot getRobot(){
        return robot;
    }
    public CameraMap getCameraMap(){
        return cameraMap;
    }
    public Base getBase(){
        return base;
    }
    public Resources getResources(){
        return resources;
    }
    public ArrayList<Enemies> getEnemies(){
        return enemies;
    }
}
