# 🐾 동물 관리 프로그램 (Animal Management System)

> Java 프로그래밍 심화 과정에서 배운 클래스와 객체, 컬렉션, 예외 처리, enum, 람다·Stream API를 하나의 콘솔 프로그램으로 묶은 CRUD 과제입니다.

<br>

## 1. 프로젝트 소개

닌텐도 게임 <동물의 숲>의 주민 정보를 관리하는 콘솔 프로그램입니다. 동물 주민을 **등록·조회·수정·삭제**할 수 있고, 이름·번호로 **검색**할 수 있습니다. 이름·성격·생년월일·성별 등 입력값이
잘못 들어와도 프로그램이 종료되지 않고 재입력을 받도록 만들었습니다.

```
=====================================
              Main Menu
=====================================
1. Create Animal
2. Update Animal
3. Delete Animal
4. Find An Animal
5. Find All Animals
6. Search Animal By Name
7. Show Animal Personality Statistics
0. Exit
```

<br>

## 2. 실행 방법

이 프로젝트는 별도의 `application` 플러그인 설정 없이 순수 `java` 플러그인만 사용하며, 모든 클래스가 `com.ohgiraffers.animalmanagementsystem` 패키지(그 하위 `model`/`view`/`controller`/`repository`/`exception`) 아래에 있습니다. IntelliJ 등 IDE에서 `Application.java`의 `main()`을 직접 실행하거나, 아래처럼 Gradle로 빌드 후 실행할 수 있습니다.

```bash
./gradlew build
java -cp build/classes/java/main com.ohgiraffers.animalmanagementsystem.Application
```

<br>

## 3. 프로젝트 구조

```
src/main/java/com/ohgiraffers/animalmanagementsystem/
├── Application.java                          ← 프로그램 시작점, 메뉴 흐름과 예외 처리 담당
├── model/
│   ├── Animal.java                           ← 동물 한 마리의 정보를 담는 클래스
│   ├── Personality.java                      ← 성격 enum (먹보, 운동광, 무뚝뚝 …)
│   └── Gender.java                           ← 성별 enum (MALE / FEMALE)
├── repository/
│   ├── AnimalRepository.java                 ← 저장소 인터페이스
│   └── AnimalRepositoryImpl.java             ← 동물 데이터 보관·검색 (ArrayList + Stream API)
├── controller/
│   └── AnimalController.java                 ← 저장소와 화면을 연결
├── view/
│   └── AnimalView.java                       ← 화면 출력과 키보드 입력 전담
└── exception/
    ├── AnimalNotFoundException.java          ← 존재하지 않는 동물을 조회/수정/삭제할 때 던지는 예외
    └── AnimalCapacityExceedException.java    ← 최대 정원(10마리) 초과 등록을 막는 예외
```

각 클래스가 맡은 일은 다음과 같이 나뉩니다.

| 클래스                    | 하는 일                                    | 하지 않는 일                    |
|------------------------|-----------------------------------------|----------------------------|
| `Animal`               | 동물 한 마리의 정보를 담는다                        | 화면 출력, 예외 처리               |
| `AnimalRepositoryImpl` | 동물을 보관하고 id·이름으로 찾아주며, 정원을 체크한다         | 화면 출력, 입력값 형식 검증           |
| `AnimalController`     | 저장소에 일을 시키고 결과 메시지를 View에 넘긴다           | 입력값을 직접 파싱하거나 예외를 잡는 일     |
| `AnimalView`           | 입력을 받고 출력한다 (형식 오류는 그 자리에서 재입력)         | 데이터를 저장하거나 비즈니스 규칙을 판단하는 일 |
| `Application`          | 객체를 연결하고 메뉴를 반복시키며, 비즈니스 예외를 한 곳에서 처리한다 | 그 외 모든 일                   |

<br>

## 4. 설계 포인트

### 4-1. 형식 오류는 View에서, 비즈니스 예외는 Application에서 한 번에

사용자가 성격/성별/생년월일을 잘못된 형식으로 입력하면(`readPersonality`, `readGender`, `readBirth`, `readInt`), `AnimalView`가 그 자리에서 재입력을 받도록
`while(true)` 반복문으로
처리합니다. 형식 오류는 프로그램 흐름을 끊을 만큼 예외적인 상황이 아니라, 입력을 받는 경계에서 바로 해소하면 되는 문제이기 때문입니다.

반면 "존재하지 않는 동물을 조회/삭제"하거나 "정원(10마리)을 초과해서 등록"하려는 것처럼 실제 **비즈니스 규칙**을 위반하는 경우에는 각각 `AnimalNotFoundException`,
`AnimalCapacityExceedException`을 던지고, `Application.main()`의 메뉴 분기 바깥에 있는 단 하나의 try-catch에서 이를 함께 잡아 사용자에게 안내합니다.

```java
}catch(AnimalNotFoundException |
AnimalCapacityExceedException e){
        animalView.

displayError(e.getMessage());
        }catch(
Exception e){
        System.out.

println("알 수 없는 에러가 발생했습니다. 다시 입력해주세요.");
}
```

이렇게 하면 `AnimalController`와 `AnimalRepositoryImpl`의 각 메소드는 자기 예외를 직접 잡지 않고 그냥 던지기만 하면 되고, 예외를 어떻게 보여줄지는 오직 한 곳(
`Application`)에서만
결정합니다.

### 4-2. 정원 체크를 입력받기 전에 먼저 한다

`save()` 시점에만 정원을 체크하면, 사용자가 이름·성격·생년월일 등 6개 항목을 다 입력한 뒤에야 "정원이 다 찼습니다"라는 예외를 만나게 됩니다. 그래서 `registerAnimal()`에서 입력을 받기
직전에 `animalController.checkAnimalCapacity()`를 먼저 호출해, 정원이 가득 찬 경우 아무 입력도 받지 않고 바로 예외를 던지도록 했습니다.
`AnimalRepositoryImpl.save()` 내부의
정원 체크는 저장 시점의 안전장치로 그대로 남겨두었습니다.

### 4-3. Controller가 얇은(thin) 이유

`AnimalController`는 `AnimalRepository`를 호출하고 성공 메시지를 `AnimalView`에 전달하는 역할만 합니다. 예외 처리와 입력 검증을 컨트롤러가 떠안지 않게 함으로써, 어떤
메소드든 "저장소 호출 → 성공 메시지"라는 동일한 패턴을 유지하도록 만들었습니다.

### 4-4. Stream API로 검색·필터 구현

`AnimalRepositoryImpl`의 `findById`, `searchByName`은 모두
`stream().filter(...).findFirst().orElseThrow(AnimalNotFoundException::new)` 패턴을
사용합니다. 조건에 맞는 동물이 없으면 자연스럽게 `AnimalNotFoundException`으로 이어지므로, 별도의 null 체크 코드가 필요 없습니다.

```java
public Animal searchByName(String name) {
    return animals.stream()
            .filter(animal -> animal.getName().equals(name))
            .findFirst()
            .orElseThrow(AnimalNotFoundException::new);
}
```

### 4-5. `Collectors.groupingBy`로 성격별 통계 구하기

```java
public Map<Personality, Long> findAnimalsGroupByPersonality() {
    return animals.stream()
            .collect(Collectors.groupingBy(Animal::getPersonality, Collectors.counting()));
}
```

`groupingBy(Animal::getPersonality, ...)`가 동물들을 성격별로 묶고, 다운스트림 컬렉터인 `Collectors.counting()`이 묶인 그룹마다 개수를 셉니다. 단, 이 결과
맵에는 **실제로 등록된 동물이
있는 성격만** key로 들어갑니다 — 등록된 동물이 하나도 없는 성격은 아예 key 자체가 없습니다.

그래서 화면 출력은 이 맵을 그대로 찍지 않고, `AnimalView.displayPersonalityStatistics()`에서 `Personality.values()` 전체를 순회하며
`getOrDefault(personality, 0L)`로
빈 성격도 0명으로 채워서 보여줍니다.

```java
public void displayPersonalityStatistics(Map<Personality, Long> statistics) {
    for (Personality personality : Personality.values()) {
        long count = statistics.getOrDefault(personality, 0L);
        System.out.printf("%-10s : %d명%n", personality, count);
    }
}
```

이 기능도 컨트롤러는 저장소 호출 결과를 View에 그대로 넘기기만 하고, 통계를 어떻게 보여줄지(빈 성격을 0명으로 채우는 것 포함)는 View가 전담합니다.

호출자가 반환받은 리스트에 `add`/`remove`를 해도 내부 `animals` 리스트의 구조는 안전하게 보호됩니다.

<br>

## 5. 실행 화면

프로그램을 실행하면 **초기 동물 9마리가 미리 등록된 상태**로 시작합니다 (정원은 10마리).

전체 조회 (`5. Find All Animals`):

```
=====================================
             Animal List
=====================================
name : 쭈니
personality : SMUG
species : 다람쥐
speaking habit : 어차피
birth : 2026-09-29
gender : MALE

name : 시베리아
personality : CRANKY
species : 늑대
speaking habit : 콜록콜록
birth : 2026-12-18
gender : MALE

... (총 9마리)
```

이름으로 검색 (`6. Search Animal By Name`, Stream `filter` + `findFirst` 사용):

```
Enter name you want to find: 쭈니
=====================================
     Selected Animal Information
=====================================
name : 쭈니
personality : SMUG
species : 다람쥐
speaking habit : 어차피
birth : 2026-09-29
gender : MALE
```

성격별 통계 조회 (`7. Show Animal Personality Statistics`, `Collectors.groupingBy` + `Collectors.counting()` 사용):

```
=====================================
      Personality Statistics
=====================================
LAZY       : 1명
JOCK       : 1명
CRANKY     : 1명
SMUG       : 2명
NORMAL     : 1명
PEPPY      : 2명
SNOOTY     : 1명
SISTERLY   : 0명
```

**잘못 입력해도 프로그램이 죽지 않습니다** (필수 요구사항 8번):

```
Please choose one of the following: 삼번
[에러] 숫자를 입력해주세요.

Enter Animal Personality: 행복함
[에러] 올바른 성격을 입력해주세요. (예: [LAZY, JOCK, CRANKY, SMUG, NORMAL, PEPPY, SNOOTY, SISTERLY]).

Enter Animal Birth (ex) yyyy-MM-dd): 2026/08/19
[에러] 올바른 생년월일을 입력해주세요. (예: 2026-08-18)

Enter animal ID: 999
[에러] 존재하지 않는 동물입니다.
```

정원(10마리) 초과 등록 시도 — 입력을 받기 전에 바로 막힙니다:

```
=====================================
            Animal Register
=====================================
[에러] 등록할 수 있는 최대 주민 수는 10마리입니다.
```

<br>

## 6. 과제 요구사항 체크리스트

### 6-1. 필수 요구사항

| # | 요구사항                          | 구현 위치                                                                                                            |
|---|-------------------------------|------------------------------------------------------------------------------------------------------------------|
| 1 | 콘솔 메뉴 반복, 종료 메뉴로 정상 종료        | `Application.main()`의 `while(true)` + `case 0`                                                                   |
| 2 | 모델 클래스 (필드 private, 접근은 메소드로) | `model/Animal.java`                                                                                              |
| 3 | 컬렉션(List)에 여러 건 데이터 관리        | `AnimalRepositoryImpl`의 `List<Animal> animals`                                                                   |
| 4 | CRUD 전부 동작                    | 등록 `registerAnimal`, 조회 `findAll`/`findById`/`searchByName`, 수정 `update`, 삭제 `delete`                            |
| 5 | enum 사용                       | `model/Personality.java`, `model/Gender.java`                                                                    |
| 6 | Stream API 검색·필터 2개 이상        | `findById`, `searchByName` (`filter` → `findFirst`), `findAnimalsGroupByPersonality` (`groupingBy` + `counting`) |
| 7 | 클래스 역할별 분리 (화면 출력 / 데이터 처리)   | `view` / `repository` / `controller` / `model` 패키지 분리                                                            |
| 8 | 잘못된 입력에도 프로그램이 종료되지 않음        | `AnimalView`의 `readInt`/`readLine`/`readPersonality`/`readGender`/`readBirth` 반복 검증 + `Application`의 전역 catch    |

### 6-2. 선택 요구사항 (구현한 것)

| 항목            | 구현 내용                                                                                                        |
|---------------|--------------------------------------------------------------------------------------------------------------|
| 나만의 예외 클래스 정의 | `AnimalNotFoundException`(존재하지 않는 동물), `AnimalCapacityExceedException`(정원 초과) 2종                             |
| 업무 규칙 검사      | "정원 10마리를 초과해서 등록할 수 없다" — 입력받기 전에 `checkAnimalCapacity()`로 선제 검증                                            |
| 통계 기능         | `Collectors.groupingBy` + `Collectors.counting()`으로 성격별 주민 수 통계 조회 (`7. Show Animal Personality Statistics`) |

<br>
