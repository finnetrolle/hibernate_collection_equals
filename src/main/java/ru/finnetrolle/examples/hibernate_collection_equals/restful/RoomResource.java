package ru.finnetrolle.examples.hibernate_collection_equals.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.finnetrolle.examples.hibernate_collection_equals.model.Room;
import ru.finnetrolle.examples.hibernate_collection_equals.repository.RoomRepository;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping("room")
public class RoomResource {

    @Autowired
    private RoomRepository repository;

    @Transactional
    @RequestMapping(method = GET)
    public @ResponseBody
    List<Room> getRooms() {
        return repository.findAll();
    }

    @Transactional
    @RequestMapping(method = POST)
    public @ResponseBody
    Room addRoom(@RequestBody Room room) {
        if (room.getId() != null) {
            throw new IllegalArgumentException("New room must not have id");
        }
        room.getStudents().forEach(s -> s.setRoom(room)); // this section set student2room dependency
        return repository.save(room);
    }

    @Transactional
    @RequestMapping(value = "/exists", method = POST)
    public @ResponseBody
    String isExists(@RequestBody Room room) {
        List<Room> rooms = repository.findAll();
        return (rooms.stream().filter(r -> r.equals(room)).count() == 0) ? "Room not exist" : "Room exist";
    }

    @Transactional
    @RequestMapping(value = "/exists_deep", method = POST)
    public @ResponseBody
    String isExistsDeep(@RequestBody Room room) {
        List<Room> rooms = repository.findAll();
        return (rooms.stream().filter(r -> r.deepEquals(room)).count() == 0)
                ? "Room not exist"
                : "Room exist";
    }
}
