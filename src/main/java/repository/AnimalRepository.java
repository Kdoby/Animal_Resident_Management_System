package repository;

import model.Animal;

import java.util.List;

public interface AnimalRepository {

    List<Animal> findAll();

    Animal findById(int id);

    Animal searchByName(String name);

    void save(Animal animal);

    void update(int id, Animal animal);

    void delete(int id);

    void isFull();
}
