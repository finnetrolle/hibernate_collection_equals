package ru.finnetrolle.examples.hibernate_collection_equals.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_title")
    private String title;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        student.setRoom(this);
        this.getStudents().add(student);
    }

    public void removeStudent(Student student) {
        this.getStudents().remove(student);
        student.setRoom(null);
    }

    public boolean deepEquals(Room room) {
        if (this == room) return true;
        // проверяем размеры коллекций
        if (this.getStudents().size() != room.getStudents().size()) return false;
        // сравниваем каждый i-ый элемент коллекции с каждым i-ым элементом другой коллекции
        for (int i = 0; i < room.getStudents().size(); ++i) {
            // для сравнения студентов можно использовать equals
            if (!room.getStudents().get(i).equals(this.getStudents().get(i))) {
                return false;
            }
        }
        // сравниваем остальные поля комнаты
        return Objects.equals(title, room.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(title, room.title) &&
                Objects.equals(students, room.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, students);
    }

    public Room(String title) {
        this.title = title;
    }

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
