package view;

import model.Animal;
import model.Gender;
import model.Personality;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AnimalView {

    Scanner scanner = new Scanner(System.in);

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String error) {
        System.out.println("[에러] " + error);
    }

    public void displaySuccess(String success) {
        System.out.println(success);
    }

    public void displayMainMenu() {

        System.out.println("=====================================");
        System.out.println("              Main Menu              ");
        System.out.println("=====================================");

        System.out.println("1. Create Animal");
        System.out.println("2. Update Animal");
        System.out.println("3. Delete Animal");
        System.out.println("4. Find An Animal");
        System.out.println("5. Find All Animals");
        System.out.println("6. Search Animal By Name");
        System.out.println("7. Show Animal Personality Statistics");
        System.out.println("0. Exit");
    }

    public void displayAnimalInfo(Animal animal) {

        if (animal == null) {
            System.out.println("Animal is null");
            return;
        }

        System.out.println("=====================================");
        System.out.println("     Selected Animal Information     ");
        System.out.println("=====================================");

        printAnimalDetail(animal);
    }

    public void displayAnimalList(List<Animal> animals) {

        if (animals.isEmpty()) {
            System.out.println("동물이 존재하지 않습니다.");
        }

        System.out.println("=====================================");
        System.out.println("             Animal List             ");
        System.out.println("=====================================");

        animals.forEach(this::printAnimalDetail);
    }

    public int readInt(String prompt) {

        // 올바른 값이 들어올 때까지 반복
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayError("숫자를 입력해주세요.");
            }
        }
    }

    public String readLine(String prompt) {

        // 빈 값이 들어오는 것을 방지
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            displayMessage("한 글자 이상 입력해주세요.");
        }
    }

    public Personality readPersonality(String prompt) {

        while (true) {

            System.out.print(prompt);
            String personality = scanner.nextLine();

            // String 타입의 Input을 ENUM 타입의 value와 비교하여 올바른 성격 값이 들어온건지 체크
            try {
                return Personality.valueOf(personality.toUpperCase());
            } catch (IllegalArgumentException e) {
                displayError("올바른 성격을 입력해주세요. (예: " + Arrays.toString(Personality.values()) + ").");
            }
        }
    }


    public Gender readGender(String prompt) {

        while (true) {
            System.out.print(prompt);
            String gender = scanner.nextLine();

            try {
                return Gender.valueOf(gender.toUpperCase());
            } catch (IllegalArgumentException e) {
                displayError("올바른 성별을 입력해주세요. (예: MALE / FEMALE).");
            }
        }
    }

    public LocalDate readBirth(String prompt) {

        // readInt의 로직과 똑같이 parsing 과정에서 발생하는 예외를 처리
        // 올바르지 않은 날짜 형식이 들어온 경우 예외를 던짐
        while (true) {
            System.out.print(prompt);
            String birth = scanner.nextLine();

            try {
                return LocalDate.parse(birth);
            } catch (DateTimeParseException e) {
                displayError("올바른 생년월일을 입력해주세요. (예: 2026-08-18)");
            }
        }
    }

    // 성격별 주민 수 통계 출력 (등록된 동물이 없는 성격도 0명으로 표시)
    public void displayPersonalityStatistics(Map<Personality, Long> statistics) {

        System.out.println("=====================================");
        System.out.println("      Personality Statistics         ");
        System.out.println("=====================================");

        for (Personality personality : Personality.values()) {
            // 등록된 동물이 없으면 default value 0으로 처리
            long count = statistics.getOrDefault(personality, 0L);
            System.out.printf("%-10s : %d명%n", personality, count);
        }
    }

    // 동물 상세 정보 출력
    private void printAnimalDetail(Animal animal) {

        System.out.println("name : " + animal.getName());
        System.out.println("personality : " + animal.getPersonality());
        System.out.println("species : " + animal.getSpecies());
        System.out.println("speaking habit : " + animal.getSpeakingHabit());
        System.out.println("birth : " + animal.getBirth());
        System.out.println("gender : " + animal.getGender());
        System.out.println(" ");
    }
}
