package org.robotgame.serialization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileStateSaverTest {
    private FileStateSaver fileStateSaver;
    private Saveable saveableMock;

    @BeforeEach
    void setUp() {
        fileStateSaver = new FileStateSaver("test");
        saveableMock = Mockito.mock(Saveable.class);
        Mockito.when(saveableMock.getName()).thenReturn("testObject");
        State stateMock = Mockito.mock(State.class);
        Map<String, Object> storage = Mockito.mock(Map.class);
        Mockito.when(saveableMock.state()).thenReturn(stateMock);
        Mockito.when(stateMock.getStorage()).thenReturn(storage);
    }


    @Test
    void save_SingleObject_SuccessfullySaved() throws IOException {
        fileStateSaver.save(saveableMock);
        File file = new File(fileStateSaver.storeFile.getAbsolutePath());
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());
        assertTrue(file.length() > 0);
        fileStateSaver.deletedFile();
    }

    @Test
    void save_ListOfObjects_SuccessfullySaved() throws IOException {
        List<Saveable> saveables = new ArrayList<>();
        saveables.add(saveableMock);
        saveables.add(saveableMock);

        fileStateSaver.save(saveables);
        File file = new File(fileStateSaver.storeFile.getAbsolutePath());
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());
        assertTrue(file.length() > 0);
        fileStateSaver.deletedFile();
    }

    @Test
    void save_SingleObject_FileContentCorrect() throws IOException {
        Mockito.when(saveableMock.getName()).thenReturn("testObject");
        fileStateSaver.save(saveableMock);
        File file = new File(fileStateSaver.storeFile.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(file)) {
            int content;
            StringBuilder stringBuilder = new StringBuilder();
            while ((content = fis.read()) != -1) {
                stringBuilder.append((char) content);
            }
            String fileContent = stringBuilder.toString();
            assertEquals(fileContent, "{\"testObject\":{}}");
        }
        fileStateSaver.deletedFile();
    }
}
