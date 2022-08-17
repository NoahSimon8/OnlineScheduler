package com.Councilor.Scheduler.Services;


import com.Councilor.Scheduler.Models.Students;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ScheduleService {

    HashMap<String,int[]> rankings = new HashMap<>();

    int[][] sample = new int[3][5];

    public ScheduleService(){
        initializeRankings();

        scheduleStart start = new scheduleStart();
        requiredBased required = new requiredBased();
        rankBased rank = new rankBased();
        termEdits term = new termEdits();
        difficultyEdits difficulty = new difficultyEdits();
        scheduleComplete complete = new scheduleComplete();

        start.set_next_chain(required);
        required.set_next_chain(rank);
        rank.set_next_chain(term);
        term.set_next_chain(difficulty);
        difficulty.set_next_chain(complete);

//        start.calculate();

        for (int i = 0; i<3;i++) {
            for (int j = 0; j < 5; j++) {
                sample[i][j]=j+5*i;
            }
        }
    }

    private void initializeRankings(){
        int baseRank = 10;
        rankings.put("Math", new int[]{0, baseRank});
        rankings.put("English", new int[]{0, baseRank});
        rankings.put("History", new int[]{0, baseRank});
        rankings.put("Foreign Language", new int[]{0, baseRank});
        rankings.put("PE", new int[]{0, baseRank});
        rankings.put("Life Science", new int[]{0, baseRank});
        rankings.put("Physical Science", new int[]{0, baseRank});
        rankings.put("Elective", new int[]{0, baseRank});
        rankings.put("Free", new int[]{0, baseRank});
    }

    public int[][] getSchedule(){
        System.out.println("SCHEDULE");


        return sample;
    }

}

class scheduleStart extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }
    @Override
    public void calculate(Students student,HashMap<String,int[]> ranks ){
        nextCalc.calculate(student, ranks);

    }
}

class requiredBased extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(Students student,HashMap<String,int[]> ranks ){


        nextCalc.calculate(student, ranks);

    }
}


class rankBased extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(Students student,HashMap<String,int[]> ranks ){


        nextCalc.calculate(student, ranks);

    }
}

class termEdits extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(Students student,HashMap<String,int[]> ranks ){


        nextCalc.calculate(student, ranks);

    }
}

class difficultyEdits extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(Students student,HashMap<String,int[]> ranks ){


        nextCalc.calculate(student, ranks);

    }
}


class scheduleComplete extends ScheduleBase{

    ScheduleBase nextCalc;

    @Override
    public void set_next_chain(ScheduleBase next) {
        nextCalc = next;
    }

    @Override
    public void calculate(Students student,HashMap<String,int[]> ranks ){


        System.out.println("DONE");
    }
}