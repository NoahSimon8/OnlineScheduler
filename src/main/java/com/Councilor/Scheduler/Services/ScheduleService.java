

// To Do:

// Make terms before difficulty, and have it add more subjects if terms are full.

// Fill in schedule with lots of gaps, code for filling in full terms, code for difficulty, code for last bits of polish
// A section to fill out subjects not available: Ie, history as a freshman
package com.Councilor.Scheduler.Services;


import com.Councilor.Scheduler.Models.Students;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ScheduleService {

    HashMap<String,Integer[]> rankings = new HashMap<>();

    String[] schedule;

    int[][] sample = new int[3][5];

    scheduleStart start = new scheduleStart();

    public ScheduleService(){
        initializeRankings();

        requiredBased required = new requiredBased();
        scheduleSet set = new scheduleSet();
        rankBased rank = new rankBased();
        termEdits term = new termEdits();
        difficultyEdits difficulty = new difficultyEdits();
        scheduleComplete complete = new scheduleComplete();

        start.set_next_chain(rank);
        rank.set_next_chain(required);
        required.set_next_chain(set);
        set.set_next_chain(difficulty);
        difficulty.set_next_chain(term);
        term.set_next_chain(complete);


        for (int i = 0; i<3;i++) {
            for (int j = 0; j < 5; j++) {
                sample[i][j]=j+5*i;
            }
        }
    }

    private void initializeRankings(){
        int baseRank = 10;
//        first int is how many terms are required, second int is the priority rank
        rankings.put("Math", new Integer[] {baseRank,0});
        rankings.put("English",  new Integer[] {baseRank,0});
        rankings.put("History",  new Integer[] {baseRank,0});
        rankings.put("Foreign_Language",  new Integer[] {baseRank,0});
        rankings.put("PE",  new Integer[] {baseRank,0});
        rankings.put("Life_Science",  new Integer[] {baseRank,0});
        rankings.put("Physical_Science",  new Integer[] {baseRank,0});
        rankings.put("Elective",  new Integer[] {baseRank,0});
        rankings.put("Free",  new Integer[] {baseRank,0});
        rankings.put("Art",  new Integer[] {baseRank,0});
    }

    public int[][] getSchedule(testStudent student){
        System.out.println("SCHEDULE");


        start.calculate(student,rankings,schedule);

        return sample;
    }

}

// Sets up the empty schedule
class scheduleStart extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }
    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule ){

        //        Creates a new schedule, the size determined by the periods in a day and terms in a year
        schedule = new String[student.getTerm_length() * student.getNum_periods()];

        nextCalc.calculate(student, ranks, schedule);

    }
}


class rankBased extends ScheduleBase{

    ScheduleBase nextCalc;

    private HashMap<String, Integer> calHighGradRequirements = new HashMap<>();
    private HashMap<String, Integer> collegeRequirements = new HashMap<>();

    public rankBased(){
        calHighGradRequirements.put("English", 3);
        calHighGradRequirements.put("Math", 2);
        calHighGradRequirements.put("History", 3);
        calHighGradRequirements.put("Physical_Science", 1);
        calHighGradRequirements.put("Life_Science", 1);
        calHighGradRequirements.put("PE", 2);
        calHighGradRequirements.put("Foreign_Language", 1);
        calHighGradRequirements.put("Art", 1);

        collegeRequirements.put("English", 4);
        collegeRequirements.put("Math", 3);
        collegeRequirements.put("History", 2);
        collegeRequirements.put("Physical_Science", 1);
        collegeRequirements.put("Life_Science", 1);
        collegeRequirements.put("Foreign_Language", 2);
        collegeRequirements.put("Art", 1);

    }

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule ){


        // + rank for each amount of interest
        for (String subject : student.getInterest().keySet()){
            // replace subject rank with the rank + the interest in the subject
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + (student.getInterest().get(subject)),0});
        }

        // + some rank for how they effect graduation credits
        for (String subject :calHighGradRequirements.keySet()){
            // replace subject rank with the rank + the number of years required by California
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + calHighGradRequirements.get(subject),0});
        }

        // + some rank for how they effect College Applications
        for (String subject : collegeRequirements.keySet()){

            // replace subject rank with the rank + the number of years required for most colleges
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + collegeRequirements.get(subject),0});

        }


//        +1 rank for each term of a class in wanted classes
        String[] wanted = student.getWantedDropdownSubjects(); // wanted classes
        int count = 0;
        for (String subject : wanted){
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + student.getWantedClassesLength()[count],0});

            count++;
        }


        nextCalc.calculate(student, ranks, schedule);

    }
}


class requiredBased extends ScheduleBase
    {
    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule){

        // for each required subject
        for (int subjectCount = 0; subjectCount < student.getRequiredDropdownSubjects().length; subjectCount++) {

            // for each class needed in that subject
            for (int classCount = 0; classCount < student.getRequiredClassesLength()[subjectCount]; classCount++) {
                String subject = student.getRequiredDropdownSubjects()[subjectCount];
                // fill the next spot in the schedule with that class
                for (int index = 0; index < schedule.length; index++) {
                    if (schedule[index] == null) {
                        schedule[index] = subject;
                        ranks.put(subject, new Integer[] {ranks.get(subject)[0]-3, ranks.get(subject)[1]+1});

                        break;
                    }
                }

            }
        }



        nextCalc.calculate(student, ranks, schedule);

    }
}


class scheduleSet extends ScheduleBase
{
    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule){
        // kinda random for now, but basically for DN, it would select 9 classes for you before sending it to term edits

        int classMax = student.getNum_periods() * student.getTerm_length() - (student.getTerm_length()*2);
        int classCount = 0;
        for (int index = 0; index < schedule.length; index++) {
            if (schedule[index]!=null){
                classCount++;
            }
        }

//        This bit is going to have some ass time-complexity, but we'll see how that goes


        for (int i = classCount; i<classMax; i++){
            int maxRank=0;
            String maxKey = "";
            for (String key : ranks.keySet()){
                if (maxRank<ranks.get(key)[0]){
                    maxKey = key;
                    maxRank = ranks.get(key)[0];
                }
            }
            for (int index = 0; index < schedule.length; index++) {
                if (schedule[index] == null) {

                    schedule[index] = maxKey;
                    ranks.put(maxKey, new Integer[] {ranks.get(maxKey)[0]-3,ranks.get(maxKey)[1]+1});
                    break;
                }
            }

        }
        nextCalc.calculate(student, ranks, schedule);

    }
}



class difficultyEdits extends ScheduleBase{

    ScheduleBase nextCalc;
    private HashMap<String, Integer> difficulties = new HashMap<>();
    private HashMap<String, ArrayList<String>> classes = new HashMap<>();

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule ){

        classes.put("hard", new ArrayList<>());
        classes.put("easy", new ArrayList<>());


        for (int index = 0; index < schedule.length; index++) {
            if (schedule[index]!=null){

                //put in the dictionary each subject as key and 10-skill as value
                difficulties.put(schedule[index],5-student.getSkills().get(schedule[index]));
            }
        }

        difficultySet(difficulties,student.getDifficulty()/2, student, ranks);

        System.out.println("DIFFICULTIES: HARD");
        for (String c : classes.get("hard")){
            System.out.println(c);
        }
        System.out.println("DIFFICULTIES: EASY");
        for (String c : classes.get("easy")){
            System.out.println(c);
        }

        System.out.println("DIFFICULTIES OVER");

        nextCalc.calculate(student, ranks, schedule);
    }

    private void difficultySet(HashMap<String, Integer> difficulties,double desiredAverage,testStudent student,HashMap<String,Integer[]> ranks){
        // find the average difficulty
        double average = 0;
        int countSubject = 0;
        for ( String key : difficulties.keySet()){
            countSubject++;
            average+= difficulties.get(key);
        }
        average /= countSubject;

        // if too easy  by more than .5, make a class harder
        if (desiredAverage > average+0.5){

            int max=0;
            String maxKey = "Math";
            for (String key : difficulties.keySet()){
                // If larger than the current max, defined by the Rank + skill
                if (max< (ranks.get(key)[0]) + student.getSkills().get(maxKey)){
                    // but difficulty not yet edited
                    if (difficulties.get(maxKey)!=5-student.getSkills().get(maxKey)) {
                        maxKey = key;
                        max = ranks.get(key)[0];
                    }
                }
            }
            //sets difficulty to 5, and then puts the subject into the hard category
            difficulties.put(maxKey, 5);
            classes.get("hard").add(maxKey);
            // repeats the function
            difficultySet(difficulties,desiredAverage, student,ranks);
        }
// if too hard by more than .5, make a class easier
        if (desiredAverage < average-0.5){

            int min=0;
            String minKey = "Math";
            for (String key : difficulties.keySet()){
                // If smaller than the current min, defined by the Rank + skill
                if (min> (ranks.get(key)[0]) + student.getSkills().get(minKey)){
                    // but difficulty not yet edited
                    if (difficulties.get(minKey)!=5-student.getSkills().get(minKey)) {
                        minKey = key;
                        min = ranks.get(key)[0];
                    }
                }
            }
            //sets difficulty to 0, and then puts the subject into the easy category
            difficulties.put(minKey, 0);
            classes.get("easy").add(minKey);
            // repeats the function
            difficultySet(difficulties,desiredAverage, student,ranks);
        }


        return ;
    }
}

class termEdits extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule ){

        for (String rank:ranks.keySet()){
            System.out.print(rank);
            System.out.print(": ");
            System.out.print(ranks.get(rank)[1]);
            System.out.print("; ");

            System.out.println(ranks.get(rank)[0]);
        }



        nextCalc.calculate(student, ranks, schedule);

    }
}

class scheduleComplete extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(testStudent student,HashMap<String,Integer[]> ranks, String[] schedule ){
        for (int index = 0; index < schedule.length; index++) {
            System.out.println(schedule[index]);

        }
        System.out.println("");
        System.out.println("RANKS");

        for (String rank:ranks.keySet()){
            System.out.print(rank);
            System.out.print(": ");
            System.out.print(ranks.get(rank)[1]);
            System.out.print("; ");

            System.out.println(ranks.get(rank)[0]);
        }

        System.out.println("SCHEDULE RETURNED");
    }
}