import controller.AnimalController;
import exception.AnimalNotFoundException;
import exception.InvalidInputException;
import model.Animal;
import model.Gender;
import model.Personality;
import view.AnimalView;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        AnimalView animalView = new AnimalView();
        AnimalController animalController = new AnimalController(animalView);

        System.out.println("Welcome to Animal Management");


        while (true) {

            animalView.displayMainMenu();
            int choice = animalView.readInt("Please choose one of the following: ");

            try {
                switch (choice) {
                    case 1:
                        registerAnimal(animalView, animalController);
                        break;
                    case 2:
                        updateAnimal(animalView, animalController);
                        break;
                    case 3:
                        deleteAnimal(animalView, animalController);
                        break;
                    case 4:
                        int id = animalView.readInt("Enter animal ID: ");
                        animalController.getAnimalById(id);
                        break;
                    case 5:
                        animalController.getAllAnimals();
                        break;
                    case 0:
                        animalView.displayMessage("프로그램을 종료합니다.");
                        return;
                    default:
                        animalView.displayError("Invalid Choice");
                }
            } catch (InvalidInputException | AnimalNotFoundException e) {
                animalView.displayError(e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 에러가 발생했습니다. 다시 입력해주세요.");
            }
        }
    }

    private static void registerAnimal(AnimalView animalView, AnimalController animalController) {

        String name = animalView.readLine("Enter name: ");
        Personality personality = animalView.readPersonality("Enter Animal Personality: ");
        String species = animalView.readLine("Enter Animal Species: ");
        String speakingHabit = animalView.readLine("Enter Animal SpeakingHabit: ");
        LocalDate birth = animalView.readBirth("Enter Animal Birth (ex) yyyy-MM-dd): ");
        Gender gender = animalView.readGender("Enter Animal Gender (MALE / FEMALE): ");

        animalController.registerAnimal(new Animal(
                name,
                personality,
                species,
                speakingHabit,
                birth,
                gender
        ));
    }

    private static void updateAnimal(AnimalView animalView, AnimalController animalController) {

        int id = animalView.readInt("Enter ID: ");
        String name = animalView.readLine("Enter name: ");
        Personality personality = animalView.readPersonality("Enter Animal Personality: ");
        String species = animalView.readLine("Enter Animal Species: ");
        String speakingHabit = animalView.readLine("Enter Animal SpeakingHabit: ");
        LocalDate birth = animalView.readBirth("Enter Animal Birth (ex) yyyy-MM-dd): ");
        Gender gender = animalView.readGender("Enter Animal Gender (MALE / FEMALE): ");

        animalController.updateAnimal(id, new Animal(
                name,
                personality,
                species,
                speakingHabit,
                birth,
                gender
        ));
    }

    public static void deleteAnimal(AnimalView animalView, AnimalController animalController) {
        int id = Integer.parseInt(animalView.readLine("Enter ID: "));

        animalController.delete(id);
    }
}
