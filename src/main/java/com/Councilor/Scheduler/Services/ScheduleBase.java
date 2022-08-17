package com.Councilor.Scheduler.Services;

import com.Councilor.Scheduler.Models.Students;

import java.util.HashMap;

public abstract class ScheduleBase {
    public abstract void set_next_chain(ScheduleBase next);
    public abstract void calculate(Students student, HashMap<String,int[]> ranks);
}
