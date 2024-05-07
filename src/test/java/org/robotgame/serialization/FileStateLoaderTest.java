package org.robotgame.serialization;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class FileStateLoaderTest {
    private static final String STORAGE_FILE_PATH = "profiles";
    @Test
    void load_FileExistsAndContainsValidData_ReturnsMapWithStates() throws IOException {
        String projectDir = new File("src/main/resources").getAbsolutePath();
        File storeFile = new File(projectDir, STORAGE_FILE_PATH+"/"+"test"+".temp");
        try (FileOutputStream fos = new FileOutputStream(storeFile)) {
            fos.write("{\"states\": {\"testObject\": {\"key\": \"value\"}}}".getBytes());
        }

        FileStateLoader fileStateLoader = new FileStateLoader(storeFile.getName().replaceFirst(".temp", ""));

        Map<String, State> loadedStates = fileStateLoader.load();

        assertNotNull(loadedStates);
        assertTrue(loadedStates.containsKey("testObject"));
        State testState = loadedStates.get("testObject");
        assertNotNull(testState);
        assertEquals("value", testState.getProperty("key"));

        storeFile.delete();
    }


    @Test
    void load_FileDoesNotExist_ReturnsNull() {
        FileStateLoader fileStateLoader = new FileStateLoader("nonexistentfile");

        Map<String, State> loadedStates = fileStateLoader.load();

        assertNull(loadedStates);
    }

    @Test
    void load_FileContainsInvalidData_ReturnsNull() throws IOException {
        File tempFile = File.createTempFile("test", ".temp");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write("invalid_data".getBytes());
        }

        FileStateLoader fileStateLoader = new FileStateLoader(tempFile.getName().replaceFirst(".temp", ""));

        Map<String, State> loadedStates = fileStateLoader.load();

        assertNull(loadedStates);

        tempFile.delete();
    }
}
