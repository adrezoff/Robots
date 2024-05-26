package org.robotgame.controller.entities;

/**
 * Класс CameraMap представляет собой камеру, которая следит за положением робота на игровой карте.
 * Он отвечает за определение видимой области карты и обновление позиции камеры в зависимости от положения робота.
 */
public class CameraMap {
    private double x;
    private double y;
    private double screenWidth;
    private double screenHeight;
    private double mapSizeX;
    private double mapSizeY;

    /**
     * Конструктор CameraMap инициализирует параметры камеры и задает начальную позицию.
     *
     * @param startX начальная координата X камеры.
     * @param startY начальная координата Y камеры.
     * @param screenWidth ширина видимой области экрана.
     * @param screenHeight высота видимой области экрана.
     * @param mapSizeX ширина всей карты.
     * @param mapSizeY высота всей карты.
     */
    public CameraMap(double startX, double startY, double screenWidth, double screenHeight,
                     double mapSizeX, double mapSizeY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        updateCameraMapPosition(startX, startY);
    }

    /**
     * Возвращает текущую координату X камеры.
     *
     * @return координата X камеры.
     */
    public double getX() {
        return x;
    }

    /**
     * Устанавливает координату X камеры.
     *
     * @param x новая координата X камеры.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Возвращает текущую координату Y камеры.
     *
     * @return координата Y камеры.
     */
    public double getY() {
        return y;
    }

    /**
     * Устанавливает координату Y камеры.
     *
     * @param y новая координата Y камеры.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Устанавливает ширину карты.
     *
     * @param mapSizeX новая ширина карты.
     */
    public void setMapSizeX(double mapSizeX) {
        this.mapSizeX = mapSizeX;
    }

    /**
     * Возвращает ширину карты.
     *
     * @return ширина карты.
     */
    public double getMapSizeX() {
        return mapSizeX;
    }

    /**
     * Устанавливает высоту карты.
     *
     * @param mapSizeY новая высота карты.
     */
    public void setMapSizeY(double mapSizeY) {
        this.mapSizeY = mapSizeY;
    }

    /**
     * Возвращает высоту карты.
     *
     * @return высота карты.
     */
    public double getMapSizeY() {
        return mapSizeY;
    }

    /**
     * Устанавливает размеры видимой области экрана.
     *
     * @param screenWidth новая ширина видимой области экрана.
     * @param screenHeight новая высота видимой области экрана.
     */
    public void setScreenSize(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Обновляет позицию камеры в зависимости от текущего положения робота.
     *
     * @param robotX координата X робота.
     * @param robotY координата Y робота.
     */
    public void updateCameraMapPosition(double robotX, double robotY) {
        x = applyLimits(robotX - this.screenWidth / 2, 0, mapSizeX - this.screenWidth);
        y = applyLimits(robotY - this.screenHeight / 2, 0, mapSizeY - this.screenHeight);
    }

    /**
     * Применяет ограничения к значению, чтобы оно находилось в пределах заданного диапазона.
     *
     * @param value значение, к которому применяются ограничения.
     * @param min минимально допустимое значение.
     * @param max максимально допустимое значение.
     * @return значение, ограниченное заданным диапазоном.
     */
    private double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    /**
     * Возвращает ширину видимой области экрана.
     *
     * @return ширина видимой области экрана.
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Возвращает высоту видимой области экрана.
     *
     * @return высота видимой области экрана.
     */
    public double getScreenHeight() {
        return screenHeight;
    }
}
