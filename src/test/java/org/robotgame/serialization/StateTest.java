package org.robotgame.serialization;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void constructor_NoParameters_ShouldCreateEmptyState() {
        State state = new State();
        assertTrue(state.getStorage().isEmpty());
    }

    @Test
    void constructor_WithParameters_ShouldInitializeStateWithGivenProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key1", "value1");
        properties.put("key2", 42);
        State state = new State(properties);
        assertEquals(2, state.getStorage().size());
        assertEquals("value1", state.getProperty("key1"));
        assertEquals(42, state.getProperty("key2"));
    }

    @Test
    void getProperty_ShouldReturnCorrectValueIfExists() {
        State state = new State();
        state.setProperty("key", "value");
        assertEquals("value", state.getProperty("key"));
    }

    @Test
    void getProperty_ShouldReturnNullIfPropertyDoesNotExist() {
        State state = new State();
        assertNull(state.getProperty("nonexistent"));
    }

    @Test
    void setProperty_ShouldSetPropertyValue() {
        State state = new State();
        state.setProperty("key", "value");
        assertEquals("value", state.getProperty("key"));
    }

    @Test
    void getStorage_ShouldReturnCorrectStorage() {
        State state = new State();
        state.setProperty("key", "value");
        Map<String, Object> storage = state.getStorage();
        assertEquals(1, storage.size());
        assertEquals("value", storage.get("key"));
    }
}

