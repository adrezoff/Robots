package org.robotgame.serialization;

import java.util.Map;

/**
 * Абстрактный класс для загрузки состояния окон.
 */
abstract public class StateLoader {

    /**
     * Загрузка состояния окон.
     * @return отображение объект окна -> его состояние
     */
    public abstract Map<String, State> load();
}
