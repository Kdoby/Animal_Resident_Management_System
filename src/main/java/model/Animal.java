package model;

import java.time.LocalDate;

public class Animal {

    private int animaId;
    private String name;
    private Personality personality;
    private String species;
    private String speakingHabit;
    private LocalDate birth;
    private Gender gender;

    public Animal(int animaId, String name, Personality personality, String species, String speakingHabit, LocalDate birth, Gender gender) {
        this.animaId = animaId;
        this.name = name;
        this.personality = personality;
        this.species = species;
        this.speakingHabit = speakingHabit;
        this.birth = birth;
        this.gender = gender;
    }

    public int getAnimaId() {
        return animaId;
    }

    public void setAnimaId(int animaId) {
        this.animaId = animaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSpeakingHabit() {
        return speakingHabit;
    }

    public void setSpeakingHabit(String speakingHabit) {
        this.speakingHabit = speakingHabit;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
