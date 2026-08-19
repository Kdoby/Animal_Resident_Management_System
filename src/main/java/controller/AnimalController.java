package controller;

import exception.AnimalNotFoundException;
import model.Animal;
import repository.AnimalRepository;
import repository.AnimalRepositoryImpl;
import view.AnimalView;

public class AnimalController {

    private final AnimalRepository animalRepository;
    private final AnimalView view;

    public AnimalController(AnimalView view) {

        animalRepository = new AnimalRepositoryImpl();
        this.view = view;
    }

    public void getAllAnimals() {

        view.displayAnimalList(animalRepository.findAll());
    }

    public void getAnimalById(int id) {

        Animal animal = animalRepository.findById(id);
        view.displayAnimalInfo(animal);
    }

    public void registerAnimal(Animal animal) {

        animalRepository.save(animal);
        view.displaySuccess("Animal registered successfully!");
    }

    public void updateAnimal(int id, Animal animal) {

        animalRepository.update(id, animal);
        view.displaySuccess("Animal updated successfully!");
    }

    public void delete(int id) {

        animalRepository.delete(id);
        view.displaySuccess("Animal deleted successfully!");
    }

    public void checkAnimalCapacity() {
        animalRepository.isFull();
    }
}
