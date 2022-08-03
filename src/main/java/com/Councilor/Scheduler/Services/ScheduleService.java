package com.Councilor.Scheduler.Services;


import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ScheduleService {

    HashMap<String,int[]> rankings = new HashMap<>();

    int[][] sample = new int[3][5];

    public ScheduleService(){
        initializeRankings();
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
