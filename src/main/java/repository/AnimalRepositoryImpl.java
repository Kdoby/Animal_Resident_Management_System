package repository;

import model.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository {

    private final List<Animal> animals = new ArrayList<>();

    private int nextAnimalId = 1;

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
               .orElse(null);
    }

    @Override
    public void save(Animal animal){

        animal.setAnimaId(nextAnimalId++);
        animals.add(animal);
    }

    @Override
    public void update(int id, Animal animal){

        Animal curAnimal = findById(id);

        curAnimal.setName(animal.getName() == null ? curAnimal.getName() : animal.getName());
        curAnimal.setPersonality(animal.getPersonality() == null ? curAnimal.getPersonality() : animal.getPersonality());
        curAnimal.setSpecies(animal.getSpecies() == null ? curAnimal.getSpecies() : animal.getSpecies());
        curAnimal.setSpeakingHabit(animal.getSpeakingHabit() == null ? curAnimal.getSpeakingHabit() : animal.getSpeakingHabit());
        curAnimal.setBirth(animal.getBirth() == null ? curAnimal.getBirth() : animal.getBirth());
        curAnimal.setGender(animal.getGender() == null ? curAnimal.getGender() : animal.getGender());
    }

    @Override
    public void delete(int id){

        animals.removeIf(animal -> animal.getAnimaId() == id);
    }
}
