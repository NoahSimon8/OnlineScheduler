package com.Councilor.Scheduler.Services;

import com.Councilor.Scheduler.Models.Students;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ScheduleBase {
    public abstract void set_next_chain(ScheduleBase next);
    public abstract ArrayList<String> calculate(Students student, HashMap<String,Integer[]> ranks, String[] schedule);
}
