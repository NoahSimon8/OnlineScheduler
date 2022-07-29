package com.Councilor.Scheduler.Services;

public abstract class ScheduleBase {
    public abstract void set_next_chain(ScheduleBase next);
    public abstract void calculate();
}
