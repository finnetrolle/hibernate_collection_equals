package ru.finnetrolle.examples.hibernate_collection_equals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.finnetrolle.examples.hibernate_collection_equals.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
