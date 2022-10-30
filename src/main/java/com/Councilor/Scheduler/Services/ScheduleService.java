

// To Do:


// Add a rank based class instead of a free period if there is extra room

package com.Councilor.Scheduler.Services;


import com.Councilor.Scheduler.Models.Students;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ScheduleService {

    HashMap<String,Integer[]> rankings = new HashMap<>();

    String[] schedule;

    ArrayList<String> output;

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
//        3rd int is difficulty (0 for easy, 1 for normal, 2 for hard), 2nd int is how many terms are required, 1st  int is the priority rank
        rankings.put("Math", new Integer[] {baseRank,0,1});
        rankings.put("English",  new Integer[] {baseRank,0,1});
        rankings.put("History",  new Integer[] {baseRank,0,1});
        rankings.put("Foreign_Language",  new Integer[] {baseRank,0,1});
        rankings.put("PE",  new Integer[] {baseRank,0,1});
        rankings.put("Life_Science",  new Integer[] {baseRank,0,1});
        rankings.put("Physical_Science",  new Integer[] {baseRank,0,1});
        rankings.put("Elective",  new Integer[] {baseRank,0,1});
        rankings.put("Free",  new Integer[] {baseRank,0,1});
        rankings.put("Art",  new Integer[] {baseRank,0,1});
    }

    public ArrayList<String> getSchedule(Students student){
        System.out.println("SCHEDULE");


        output = start.calculate(student,rankings,schedule);

        return output;
//        return schedule
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
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule ){

        //        Creates a new schedule, the size determined by the periods in a day and terms in a year
        schedule = new String[student.getTerm_length() * student.getNum_periods()];

        return nextCalc.calculate(student, ranks, schedule);

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
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule ){


        // + rank for each amount of interest
        for (String subject : student.getInterest().keySet()){
            // replace subject rank with the rank + the interest in the subject
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + (student.getInterest().get(subject)),0,1});
        }

        // + some rank for how they effect graduation credits
        for (String subject :calHighGradRequirements.keySet()){
            // replace subject rank with the rank + the number of years required by California
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + calHighGradRequirements.get(subject),0,1});
        }

        // + some rank for how they effect College Applications
        for (String subject : collegeRequirements.keySet()){

            // replace subject rank with the rank + the number of years required for most colleges
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + collegeRequirements.get(subject),0,1});

        }


//        +1 rank for each term of a class in wanted classes
        String[] wanted = student.getWantedDropdownSubjects(); // wanted classes
        int count = 0;
        for (String subject : wanted){
            if (subject.equals("Select a Subject")){
                continue;
            }
//            System.out.println(subject);
            ranks.put(subject, new Integer[] {ranks.get(subject)[0] + student.getWantedClassesLength()[count],0,1});

            count++;
        }


        return nextCalc.calculate(student, ranks, schedule);

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
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule){

        // for each required subject
        int subjectCount=0;
        for (String subject : student.getRequiredDropdownSubjects()){
            // for each class needed in that subject
//            System.out.println(subject);
            if (subject.equals("Select a Subject")){
                continue;
            }
            for (int classCount = 0; classCount < student.getRequiredClassesLength()[subjectCount]; classCount++) {


                // fill the next spot in the schedule with that class
                for (int index = 0; index < schedule.length; index++) {
                    if (schedule[index] == null) {
                        schedule[index] = subject;
                        ranks.put(subject, new Integer[] {ranks.get(subject)[0]-3, ranks.get(subject)[1]+1,1});

                        break;
                    }
                }

            }
            subjectCount++;
        }



        return nextCalc.calculate(student, ranks, schedule);

    }
}

// puts in actual classes based on ranks
class scheduleSet extends ScheduleBase
{
    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule){
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
                    ranks.put(maxKey, new Integer[] {ranks.get(maxKey)[0]-3,ranks.get(maxKey)[1]+1,1});
                    break;
                }
            }

        }
        return nextCalc.calculate(student, ranks, schedule);

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
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule ){

        classes.put("hard", new ArrayList<>());
        classes.put("easy", new ArrayList<>());


        for (int index = 0; index < schedule.length; index++) {
            if (schedule[index]!=null){

                //put in the dictionary each subject as key and 10-skill as value
                difficulties.put(schedule[index],5-student.getSkills().get(schedule[index]));
            }
        }

        difficultySet(difficulties,student.getDifficulty()/2, student, ranks,0);
//
//        System.out.println("DIFFICULTIES: HARD");
//        for (String c : classes.get("hard")){
//            System.out.println(c);
//        }
//        System.out.println("DIFFICULTIES: EASY");
//        for (String c : classes.get("easy")){
//            System.out.println(c);
//        }


        for (String c : classes.get("hard")){
            ranks.put(c, new Integer[] {ranks.get(c)[0],ranks.get(c)[1],2 });
        }
        for (String c : classes.get("easy")){
            ranks.put(c, new Integer[] {ranks.get(c)[0],ranks.get(c)[1],0 });
        }

//        System.out.println("DIFFICULTIES OVER");

        return nextCalc.calculate(student, ranks, schedule);
    }

    private void difficultySet(HashMap<String, Integer> difficulties,double desiredAverage,Students student,HashMap<String,Integer[]> ranks, int repititions){
        // find the average difficulty
        double average = 0;
        int countSubject = 0;
        for ( String key : difficulties.keySet()){
            countSubject++;
            average+= difficulties.get(key);
        }
        average /= countSubject;

        // if too easy  by more than .5, make a class harder
        if (desiredAverage > average+0.5+(repititions*0.01)){

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
            difficultySet(difficulties,desiredAverage, student,ranks,repititions+1);
        }
// if too hard by more than .5, make a class easier
        if (desiredAverage < average-0.5-(repititions*0.01)){

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
            difficultySet(difficulties,desiredAverage, student,ranks,repititions+1);
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
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule ){


        int yearCredit = student.getTerms_per_year_credit();
        int apLength = student.getTerms_per_AP();;

//        System.out.println("TERM BASED");

        // for each class
        for (String key : schedule){

            if (key == null){
                break;
            }
            // depending on if it is hard vs normal or easy, give it normal or AP length
            // This will first give all classes the normal length minimum
            // if less classes than needed and the last thing is the schedule is still null, aka not full
            while (ranks.get(key)[1]<yearCredit && schedule[student.getNum_periods()* student.getTerm_length()-1]==null){
//                System.out.print("ADDING REGULAR: ");
//                System.out.println(key);
                // add the class
                for (int index = 0; index < schedule.length; index++) {
                    if (schedule[index] == null) {
                        schedule[index] = key;
                        ranks.put(key, new Integer[] {ranks.get(key)[0]-3,ranks.get(key)[1]+1,ranks.get(key)[2]});
                        break;
                    }
                }

            }

            // if hard
            if (ranks.get(key)[2]==2){

                // add enough classes to fill the ap length
                while (ranks.get(key)[1]<apLength && schedule[student.getNum_periods()* student.getTerm_length()-1]==null){
                    // add the class
                    for (int index = 0; index < schedule.length; index++) {
                        if (schedule[index] == null) {
                            schedule[index] = key;
                            ranks.put(key, new Integer[] {ranks.get(key)[0]-3,ranks.get(key)[1]+1,ranks.get(key)[2]});
                            break;
                        }
                    }
                }
            }
        }
        // add enough Free periods to fill the schedule length
        while (schedule[student.getNum_periods()* student.getTerm_length()-1]==null){

            // add the class
            for (int index = 0; index < schedule.length; index++) {
                if (schedule[index] == null) {
                    schedule[index] = "Free";
                    ranks.put("Free", new Integer[] {ranks.get("Free")[0]-3,ranks.get("Free")[1]+1,ranks.get("Free")[2]});
                    break;
                }
            }
        }
        return nextCalc.calculate(student, ranks, schedule);
    }



}


class scheduleComplete extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public ArrayList<String> calculate(Students student,HashMap<String,Integer[]> ranks, String[] schedule ){
        System.out.println("SCHEDULE");
        for (int index = 0; index < schedule.length; index++) {
            int diff = ranks.get(schedule[index])[2];
            if (diff ==2){
                schedule[index]+= " - Hard";
            }
            if (diff ==0){
                schedule[index]+= " - Easy";
            }

//            System.out.println(schedule[index]);

        }
//        System.out.println("");
        System.out.println("RANKS");

        for (String rank:ranks.keySet()){
            System.out.print(rank);
            System.out.print(": ");
            System.out.print(ranks.get(rank)[0]);
            System.out.print("; ");
            System.out.print(ranks.get(rank)[1]);
            System.out.print("; ");
            System.out.println(ranks.get(rank)[2]);
        }

        ArrayList<String> finalSubjectsOutput =  new ArrayList<>();
        int numFree = schedule.length;
        for (String key : ranks.keySet()){
            if (key.equals("Free")){
                continue;
            }
            numFree-=ranks.get(key)[1];
        }


        int numPeriod = 0;
        int spaceAt;
        String subj;
        for (String c : schedule){
            spaceAt = c.indexOf(" ");
            if (spaceAt!=-1) {
                subj = c.substring(0, spaceAt);
            }
            else{
                subj=c;
            }
            if (subj.equals("Free")){
                numPeriod = numFree;

            }
            else {
                numPeriod = ranks.get(subj)[1];
            }

            if (finalSubjectsOutput.contains(numPeriod + " " + c + " Period(s)" )){
                continue;
            }
            finalSubjectsOutput.add(numPeriod + " " + c + " Period(s)");

        }

        for (String c : finalSubjectsOutput){
            System.out.println(c);
        }


        System.out.println("SCHEDULE RETURNED");
        return finalSubjectsOutput;
    }
}