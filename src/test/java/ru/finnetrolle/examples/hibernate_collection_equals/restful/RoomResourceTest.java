package ru.finnetrolle.examples.hibernate_collection_equals.restful;

import org.junit.Test;
import ru.finnetrolle.examples.hibernate_collection_equals.model.Room;
import ru.finnetrolle.examples.hibernate_collection_equals.model.Student;

import static org.junit.Assert.*;

public class RoomResourceTest {

    private Room createRoom() {
        Room room = new Room("room");
        room.addStudent(new Student("some", "one"));
        room.addStudent(new Student("any", "body"));
        return room;
    }

    @Test
    public void testIsExists() throws Exception {
        Room roomA = createRoom();
        Room roomB = createRoom();
        assertEquals(roomA, roomB);
    }

    @Test
    public void testIsExistDeep() throws Exception {
        Room roomA = createRoom();
        Room roomB = createRoom();
        assertTrue(roomA.deepEquals(roomB));
    }

    @Test
    public void testIsExistFail() throws Exception {
        Room roomA = createRoom();
        Room roomB = createRoom();
        roomB.getStudents().forEach(s -> s.setName("other"));
        assertNotEquals(roomA, roomB);
    }

    @Test
    public void testIsExistDeepFail() throws Exception {
        Room roomA = createRoom();
        Room roomB = createRoom();
        roomB.getStudents().forEach(s -> s.setName("other"));
        assertFalse(roomA.deepEquals(roomB));
    }
}