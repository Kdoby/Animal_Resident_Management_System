import controller.AnimalController;
import exception.AnimalCapacityExceedException;
import exception.AnimalNotFoundException;
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

        // 전역 에러핸들러 역할
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
                    case 6:
                        String name = animalView.readLine("Enter name you want to find: ");
                        animalController.getAnimalByName(name);
                        break;
                    case 0:
                        animalView.displayMessage("프로그램을 종료합니다.");
                        return;
                    default:
                        animalView.displayError("Invalid Choice");
                }
            } catch (AnimalNotFoundException | AnimalCapacityExceedException e) {
                animalView.displayError(e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 에러가 발생했습니다. 다시 입력해주세요.");
            }
        }
    }

    private static void registerAnimal(AnimalView animalView, AnimalController animalController) {

        System.out.println("=====================================");
        System.out.println("            Animal Register          ");
        System.out.println("=====================================");

        // 최대 수용 가능 인원 초과 여부 확인
        animalController.checkAnimalCapacity();

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

        System.out.println("=====================================");
        System.out.println("            Animal Update            ");
        System.out.println("=====================================");

        int id = animalView.readInt("Enter animal ID You want to update: ");

        // 기존 정보 조회
        animalController.getAnimalById(id);
        System.out.println("============= Please enter the changes ==============");

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

        System.out.println("=====================================");
        System.out.println("            Animal Delete            ");
        System.out.println("=====================================");

        int id = Integer.parseInt(animalView.readLine("Enter ID: "));

        // 현재 정보 조회
        animalController.getAnimalById(id);

        // 삭제 여부 다시 한 번 체크
        while (true) {
            String confirm = animalView.readLine("정말 삭제하시겠습니까? (Y / N)");

            if (confirm.equalsIgnoreCase("y")) {
                animalController.delete(id);
                return;
            } else if (confirm.equalsIgnoreCase("n")) {
                animalView.displayMessage("Deletion successfully canceled");
                return;
            }

            // 올바르지 않은 입력 값 예외 처리
            animalView.displayError("Y/N 중 하나를 선택하세요");
        }
    }
}
