package com.Councilor.Scheduler.Models;
import lombok.Data;

@Data
public class Students {

    private String first_name;
    private String last_name;
    private String grade;
    private String term_length;
    private String num_periods;
    private String difficulty;

    private String[] requiredDropdownSubjects;
    private String[] wantedDropdownSubjects;
    private String[] wantedDropdownClasses;
    private String[] requiredClassesLength;
    private String[] wantedClassesLength;


    private String radioArtSkills;
    private String radioArtInterest;
    private String radioMathSkills;
    private String radioMathInterest;
    private String radioForeign_LanguageSkills;
    private String radioForeign_LanguageInterest;
    private String radioHistorySkills;
    private String radioHistoryInterest;
    private String radioLife_ScienceSkills;
    private String radioLife_ScienceInterest;
    private String radioPhysical_ScienceSkills;
    private String radioPhysical_ScienceInterest;
    private String radioHealthSkills;
    private String radioHealthInterest;
    private String radioPESkills;
    private String radioPEInterest;
    private String radioElectiveSkills;
    private String radioElectiveInterest;





}
