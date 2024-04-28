package org.robotgame.serialization;

import java.util.List;

/**
 * Абстрактный класс для сохранения состояния окон.
 */
abstract  public class StateSaver {

    /**
     * Сохранение состояния окна.
     * @param obj объект, который хотим сохранить
     */
    abstract public void save(Saveable obj);

    /**
     * Сохранение состояний нескольких окон.
     * @param objs объекты окон, которые хотим сохранить
     */
    abstract public void save(List<Saveable> objs);
}
