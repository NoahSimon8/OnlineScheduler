package com.Councilor.Scheduler.Controllers;

import com.Councilor.Scheduler.Services.ScheduleService;
import com.Councilor.Scheduler.Services.testStudent;
import com.Councilor.Scheduler.Models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Hashtable;
//comment
@Controller
public class studentsController {

    Hashtable<String, String[]> subjects = new Hashtable<>();
    ScheduleService scheduleService;
    ArrayList<String> schedule;


    @Autowired
    studentsController(ScheduleService scheduleService){
        this.scheduleService = scheduleService; // why is it this.scheduleService?
        initializeSubjects();
    }

    private void initializeSubjects(){
        subjects.put("Select a subject", new String[] {"1"});
        subjects.put("Art", new String[] {"1","2","3","4","5"});
        subjects.put("Math", new String[] {"1","2","3","4","5"});
        subjects.put("History", new String[] {"1","2","3","4","5"});
        subjects.put("Foreign_Language", new String[] {"1","2","3","4","5"});
        subjects.put("Life_Science", new String[] {"1","2","3","4","5"});
        subjects.put("Physical_Science", new String[] {"1","2","3","4","5"});
        subjects.put("Health", new String[] {"1"});
        subjects.put("PE", new String[] {"1"});
        subjects.put("Elective", new String[] {"1","2","3","4","5"});


    }


    @GetMapping({"/","/home"})
    public String homeGet(){
        System.out.println("*************** Request Sent *************");

        return "home";
    }

//    For some reason, / and /home fixed the error
    @PostMapping(path="/result")
    public String surveyPost(@ModelAttribute Students student, Model model){
        System.out.println("*************** Post Received *************");
        // teststudent so i don't need to fill out the survey everyTime
        student.organize();

        schedule = this.scheduleService.getSchedule(student);


        model.addAttribute("schedule",schedule);
        return "result";
    }


    @GetMapping({"/survey"})
    public String surveyGet(Model model){

        model.addAttribute("subjects", subjects);
        return "survey";
    }

    @GetMapping({"/result"})
    public String resultGet(Model model){
        model.addAttribute("schedule",schedule);
        return "result";
    }
}
