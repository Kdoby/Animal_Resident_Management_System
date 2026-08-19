package repository;

import model.Animal;

import java.util.List;

// 아키텍처 확장성을 고려하여 repository는 인터페이스로 생성
public interface AnimalRepository {

    // 모든 주민 목록 조회
    List<Animal> findAll();

    // id로 특정 주민 정보만 조회
    Animal findById(int id);

    // 이름으로 특정 주민 정보 조회
    Animal searchByName(String name);

    // 주민 등록
    void save(Animal animal);

    // 주민 정보 수정
    void update(int id, Animal animal);

    // 주민 삭제
    void delete(int id);

    // 주민 수용 가능 인원을 초과하였는지 체크
    void isFull();
}
