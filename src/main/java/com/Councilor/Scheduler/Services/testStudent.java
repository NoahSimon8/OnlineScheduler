package com.Councilor.Scheduler.Services;
import lombok.Data;
import java.util.HashMap;


//@Data
public class testStudent{

//    CHANGING THESE NAMES WILL MAKE IT NOT CONNECT TO THE WEBSITE LATER



    private String first_name = "Noah";
    private String last_name = "Simon";
    private String gradeStr = "10";
    private String term_lengthStr = "2";
    private String terms_per_year_creditStr= "2";
    private String terms_per_APStr = "2";
    private String num_periodsStr = "6";
    private String difficultyStr= "8";



    private String[] requiredDropdownSubjects = {"PE", "English"};
    private String[] wantedDropdownSubjects = {"Math", "Physical_Science"};
    private String[] wantedDropdownClassesStr= {"5", "5"}; // a difficulty measurement
    private String[] requiredClassesLengthStr = {"2", "2"}; // terms
    private String[] wantedClassesLengthStr= {"2","2"}; // terms


    private String radioArtSkills = "0";
    private String radioArtInterest = "0";
    private String radioMathSkills = "4";
    private String radioMathInterest = "4";
    private String radioEnglishSkills = "4";
    private String radioEnglishInterest = "3";
    private String radioForeign_LanguageSkills = "2";
    private String radioForeign_LanguageInterest = "1";
    private String radioHistorySkills = "3";
    private String radioHistoryInterest = "2";
    private String radioLife_ScienceSkills = "4";
    private String radioLife_ScienceInterest = "3";
    private String radioPhysical_ScienceSkills = "4";
    private String radioPhysical_ScienceInterest = "4";
    private String radioHealthSkills = "3";
    private String radioHealthInterest = "0";
    private String radioPESkills = "3";
    private String radioPEInterest = "1";
    private String radioElectiveSkills = "3";
    private String radioElectiveInterest = "2";


    private HashMap<String,Integer> skills = new HashMap<>();
    private HashMap<String,Integer> interest = new HashMap<>();

    private int[] wantedClassesDifficulty;
    private int[] wantedClassesLength;
    private int[] requiredClassesLength;

    private int grade;

    private int term_length;
    private int terms_per_year_credit;
    private int terms_per_AP;
    private int num_periods;
    private int difficulty;

    public void organize(){
        grade = Integer.parseInt(gradeStr);
        term_length = Integer.parseInt(term_lengthStr);
        terms_per_year_credit = Integer.parseInt(terms_per_year_creditStr);
        terms_per_AP = Integer.parseInt(terms_per_APStr);
        num_periods = Integer.parseInt(num_periodsStr);
        difficulty = Integer.parseInt(difficultyStr);

        wantedClassesDifficulty = new int[wantedDropdownClassesStr.length];
        for (int i = 0; i<wantedClassesDifficulty.length; i++) {
            wantedClassesDifficulty[i] = Integer.parseInt(wantedDropdownClassesStr[i]);
        }

        wantedClassesLength = new int[wantedClassesLengthStr.length];
        for (int i = 0; i<wantedClassesLength.length; i++) {
            wantedClassesLength[i] = Integer.parseInt(wantedClassesLengthStr[i]);
        }

        requiredClassesLength = new int[requiredClassesLengthStr.length];
        for (int i = 0; i<requiredClassesLength.length; i++) {
            requiredClassesLength[i] = Integer.parseInt(requiredClassesLengthStr[i]);
        }
// Art  Math  Foreign_Language  History  English  PE  Elective  Physical_Science  Life_Science
        skills.put("Art", Integer.valueOf(radioArtSkills));
        skills.put("Math", Integer.valueOf(radioMathSkills));
        skills.put("Foreign_Language", Integer.valueOf(radioForeign_LanguageSkills));
        skills.put("History", Integer.valueOf(radioHistorySkills));
        skills.put("English", Integer.valueOf(radioEnglishSkills));
        skills.put("PE", Integer.valueOf(radioPESkills));
        skills.put("Elective", Integer.valueOf(radioElectiveSkills));
        skills.put("Physical_Science", Integer.valueOf(radioPhysical_ScienceSkills));
        skills.put("Life_Science", Integer.valueOf(radioLife_ScienceSkills));


        interest.put("Art", Integer.valueOf(radioArtInterest));
        interest.put("Math", Integer.valueOf(radioMathInterest));
        interest.put("Foreign_Language", Integer.valueOf(radioForeign_LanguageInterest));
        interest.put("History", Integer.valueOf(radioHistoryInterest));
        interest.put("English", Integer.valueOf(radioEnglishInterest));
        interest.put("PE", Integer.valueOf(radioPEInterest));
        interest.put("Elective", Integer.valueOf(radioElectiveInterest));
        interest.put("Physical_Science", Integer.valueOf(radioPhysical_ScienceInterest));
        interest.put("Life_Science", Integer.valueOf(radioLife_ScienceInterest));

    }



    public int getGrade() {
        return grade;
    }

    public int getTerm_length() {
        return term_length;
    }

    public int getTerms_per_year_credit() {
        return terms_per_year_credit;
    }

    public int getTerms_per_AP() {
        return terms_per_AP;
    }

    public int getNum_periods() {
        return num_periods;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public HashMap<String, Integer> getSkills() {
        return skills;
    }

    public HashMap<String, Integer> getInterest() {
        return interest;
    }

    public int[] getWantedClassesDifficulty() {
        return wantedClassesDifficulty;
    }

    public int[] getWantedClassesLength() {
        return wantedClassesLength;
    }

    public int[] getRequiredClassesLength() {
        return requiredClassesLength;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String[] getWantedDropdownSubjects() {
        return wantedDropdownSubjects;
    }

    public String[] getWantedDropdownClassesStr() {
        return wantedDropdownClassesStr;
    }

    public String[] getRequiredDropdownSubjects() {
        return requiredDropdownSubjects;
    }
}
