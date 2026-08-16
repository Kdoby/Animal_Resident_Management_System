package controller;

import model.Animal;
import repository.AnimalRepository;
import repository.AnimalRepositoryImpl;
import view.AnimalView;

public class AnimalController {

    private AnimalRepository animalRepository;
    private AnimalView view;

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

        try {
            animalRepository.save(animal);
            view.displaySuccess("Animal registered successfully!");
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }

    public void updateAnimal(int id, Animal animal) {

        try {
            animalRepository.update(id, animal);
            view.displaySuccess("Animal updated successfully!");
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }

    public void delete(int id) {

        try {
            animalRepository.delete(id);
            view.displaySuccess("Animal deleted successfully!");
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }
}
