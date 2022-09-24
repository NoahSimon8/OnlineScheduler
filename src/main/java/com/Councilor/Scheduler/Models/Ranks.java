package com.Councilor.Scheduler.Models;

import java.util.HashMap;

public class Ranks {

    public HashMap<String,Integer> rankings = new HashMap<>();
    private String topSubject;

    public Ranks(){
        int baseRank = 10;
//        first int is how many terms are required, second int is the priority rank
        rankings.put("Math", baseRank);
        rankings.put("English", baseRank);
        rankings.put("History", baseRank);
        rankings.put("Foreign_Language", baseRank);
        rankings.put("PE", baseRank);
        rankings.put("Life_Science", baseRank);
        rankings.put("Physical_Science", baseRank);
        rankings.put("Elective", baseRank);
        rankings.put("Free", baseRank);
        rankings.put("Art", baseRank);
    }

    public String selectTopSubject(){
        rankings.put(topSubject,rankings.get(topSubject)-3);
        String prevTopSubject = topSubject;

        int maxRank=0;
        String maxKey = "";
        for (String key : rankings.keySet()){
            if (maxRank<rankings.get(key)){
                maxKey = key;
                maxRank = rankings.get(key);
            }
        }
        topSubject = maxKey;
        return prevTopSubject;
    }

}
