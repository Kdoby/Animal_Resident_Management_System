package repository;

import exception.AnimalCapacityExceedException;
import exception.AnimalNotFoundException;
import model.Animal;
import model.Gender;
import model.Personality;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository {

    private final List<Animal> animals = new ArrayList<>();

    private int nextAnimalId = 1;

    public void initialize() {
        save(new Animal(
                "쭈니", Personality.SMUG, "다람쥐", "어차피", LocalDate.of(2026, 9, 29), Gender.MALE)
        );
        save(new Animal(
                "시베리아", Personality.CRANKY, "늑대", "콜록콜록", LocalDate.of(2026, 12, 18), Gender.MALE)
        );
        save(new Animal(
                "잭슨", Personality.SMUG, "고양이", "우쭐", LocalDate.of(2026, 10, 1), Gender.MALE)
        );
        save(new Animal(
                "메이첼", Personality.NORMAL, "아기곰", "저기요", LocalDate.of(2026, 6, 15), Gender.FEMALE)
        );
        save(new Animal(
                "모니카", Personality.PEPPY, "늑대", "아하핫", LocalDate.of(2026, 8, 31), Gender.FEMALE)
        );
        save(new Animal(
                "미첼", Personality.LAZY, "토끼", "동", LocalDate.of(2026, 5, 19), Gender.MALE)
        );
        save(new Animal(
                "미애", Personality.SNOOTY, "아기곰", "어머머", LocalDate.of(2026, 3, 10), Gender.FEMALE)
        );
        save(new Animal(
                "오즈먼드", Personality.JOCK, "코알라", "우와", LocalDate.of(2026, 10, 12), Gender.MALE)
        );
        save(new Animal(
                "요비", Personality.PEPPY, "사슴", "아무튼", LocalDate.of(2026, 10, 31), Gender.FEMALE)
        );
//        save(new Animal(
//                "애플", Personality.PEPPY, "햄스터", "큐룽", LocalDate.of(2026, 9, 24), Gender.FEMALE)
//        );
    }

    public AnimalRepositoryImpl() {
        initialize();
    }

    // 전체 조회
    @Override
    public List<Animal> findAll() {

        return new ArrayList<>(animals);
    }

    // 단일 조회
    @Override
    public Animal findById(int id) {

        return animals.stream()
                .filter(animal -> animal.getAnimaId() == id)
                .findFirst()
                .orElseThrow(AnimalNotFoundException::new);
    }

    @Override
    public void save(Animal animal) {

        if (animals.size() >= 10) {
            throw new AnimalCapacityExceedException();
        }

        animal.setAnimaId(nextAnimalId++);
        animals.add(animal);
    }

    @Override
    public void update(int id, Animal animal) {

        Animal curAnimal = findById(id);

        curAnimal.setName(animal.getName() == null ? curAnimal.getName() : animal.getName());
        curAnimal.setPersonality(animal.getPersonality() == null ? curAnimal.getPersonality() : animal.getPersonality());
        curAnimal.setSpecies(animal.getSpecies() == null ? curAnimal.getSpecies() : animal.getSpecies());
        curAnimal.setSpeakingHabit(animal.getSpeakingHabit() == null ? curAnimal.getSpeakingHabit() : animal.getSpeakingHabit());
        curAnimal.setBirth(animal.getBirth() == null ? curAnimal.getBirth() : animal.getBirth());
        curAnimal.setGender(animal.getGender() == null ? curAnimal.getGender() : animal.getGender());
    }

    @Override
    public void delete(int id) {

        Animal delAnimal = findById(id);

        animals.remove(delAnimal);
    }

    @Override
    public void isFull() {

        if (animals.size() >= 10) {
            throw new AnimalCapacityExceedException();
        }
    }
}
