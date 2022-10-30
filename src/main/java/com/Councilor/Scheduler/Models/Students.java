package com.Councilor.Scheduler.Models;

import lombok.Data;

import java.util.HashMap;
@Data
public class Students {


    private String first_name ;
    private String last_name ;
    private String gradeStr;
    private String term_lengthStr ;
    private String terms_per_year_creditStr;
    private String terms_per_APStr;
    private String num_periodsStr ;
    private String difficultyStr;



    private String[] requiredDropdownSubjects ;
    private String[] wantedDropdownSubjects;
    private String[] wantedDropdownClassesStr;
    private String[] requiredClassesLengthStr ;
    private String[] wantedClassesLengthStr;


    private String radioArtSkills;
    private String radioArtInterest;
    private String radioMathSkills;
    private String radioMathInterest;
    private String radioEnglishSkills;
    private String radioEnglishInterest;
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

    private void fillNullsWithDefaults(){
        if (first_name == null || first_name == ""){first_name ="McStudent";}
        if (last_name  == null || last_name == ""){last_name = "Student";}
        if (gradeStr  == null || gradeStr == ""){gradeStr = "9" ;}
        if (term_lengthStr  == null){term_lengthStr = "2" ;}
        if (terms_per_year_creditStr  == null){terms_per_year_creditStr = "2";}
        if (terms_per_APStr  == null){terms_per_APStr = "2";}
        if (num_periodsStr  == null){num_periodsStr = "6";}
        if (difficultyStr  == null){difficultyStr = "";}


        if (requiredDropdownSubjects  == null){requiredDropdownSubjects = new String[] {};}
        if (wantedDropdownSubjects  == null){wantedDropdownSubjects = new String[] {};}
        if (wantedDropdownClassesStr  == null){wantedDropdownClassesStr = new String[] {};}
        if (requiredClassesLengthStr  == null){requiredClassesLengthStr = new String[] {};}
        int count = 0;
        for (String num : requiredClassesLengthStr){
             if (num==""){
                 requiredClassesLengthStr[count]="0";
            }
             count++;
        }
        if (wantedClassesLengthStr  == null){wantedClassesLengthStr = new String[] {};}
        count = 0;
        for (String num : wantedClassesLengthStr){
            if (num==""){
                wantedClassesLengthStr[count]="0";
            }
            count++;
        }


        if (radioArtSkills  == null){radioArtSkills = "3";}
        if (radioArtInterest  == null){radioArtInterest = "3";}
        if (radioMathSkills  == null){radioMathSkills = "3";}
        if (radioMathInterest  == null){radioMathInterest = "3";}
        if (radioEnglishSkills  == null){radioEnglishSkills = "3";}
        if (radioEnglishInterest  == null){radioEnglishInterest = "3";}
        if (radioForeign_LanguageSkills  == null){radioForeign_LanguageSkills = "3";}
        if (radioForeign_LanguageInterest  == null){radioForeign_LanguageInterest = "3";}
        if (radioHistorySkills  == null){radioHistorySkills = "3";}
        if (radioHistoryInterest  == null){radioHistoryInterest = "3";}
        if (radioLife_ScienceSkills  == null){radioLife_ScienceSkills = "3";}
        if (radioLife_ScienceInterest  == null){radioLife_ScienceInterest = "3";}
        if (radioPhysical_ScienceSkills  == null){radioPhysical_ScienceSkills = "3";}
        if (radioPhysical_ScienceInterest  == null){radioPhysical_ScienceInterest = "3";}
        if (radioHealthSkills  == null){radioHealthSkills = "3";}
        if (radioHealthInterest  == null){radioHealthInterest = "3";}
        if (radioPESkills  == null){radioPESkills = "3";}
        if (radioPEInterest  == null){radioPEInterest = "3";}
        if (radioElectiveSkills  == null){radioElectiveSkills = "3";}
        if (radioElectiveInterest  == null){radioElectiveInterest = "3";}


    }

    public void organize(){
        fillNullsWithDefaults();
        System.out.println("ORGANIZE: name");
        System.out.println(first_name);

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
