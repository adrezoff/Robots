package org.robotgame.serialization;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Класс для сохранения состояния объекта в файл.
 * Реализует абстрактный класс StateSaver.
 */
public class FileStateSaver extends StateSaver {
    private static final String STORAGE_FILE_PATH = "profiles";

    final File storeFile;
    /**
     * Конструктор по умолчанию.
     * Определяет путь к файлу хранения состояния.
     */
    public FileStateSaver(String id) {
        String projectDir = new File("src/main/resources").getAbsolutePath();
        storeFile = new File(projectDir, STORAGE_FILE_PATH+"/"+id+".temp");
    }

    /**
     * Сохранение состояния объекта в файл.
     * Расположение файла известно заранее. Член storeFile.
     *
     * @param obj объект, который хотим сохранить в файл
     */
    @Override
    public void save(Saveable obj) {
        try {
            storeFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        storeFile.setWritable(true);

        final JSONObject jsonState = new JSONObject(obj.state().getStorage());
        final JSONObject jsonObj = new JSONObject();

        jsonObj.put(obj.getName(), jsonState);

        try (FileOutputStream fos = new FileOutputStream(storeFile)) {
            fos.write(jsonObj.toString(0).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохранение состояний объектов в файл.
     * Расположение файла известно заранее. Член storeFile.
     *
     * @param objs список объектов на сохранение
     */
    public void save(List<Saveable> objs) {
        try {
            storeFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        storeFile.setWritable(true);

        JSONObject json = new JSONObject();

        JSONObject statesJson = new JSONObject();
        for (Saveable obj : objs) {
            JSONObject jsonState = new JSONObject(obj.state().getStorage());
            statesJson.put(obj.getName(), jsonState);
        }

        json.put("states", statesJson);

        try (FileOutputStream fos = new FileOutputStream(storeFile)) {
            fos.write(json.toString(0).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void deletedFile(){
        try {
            storeFile.delete();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
