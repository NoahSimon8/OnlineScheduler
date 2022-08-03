package com.Councilor.Scheduler.Controllers;

import com.Councilor.Scheduler.Services.ScheduleService;
import com.Councilor.Scheduler.Models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Hashtable;

@Controller
public class studentsController {

    Hashtable<String, String[]> subjects = new Hashtable<>();
    ScheduleService scheduleService;

    @Autowired
    studentsController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
        initializeSubjects();
    }

    private void initializeSubjects(){
        subjects.put("Art", new String[] {"Ceramics", "Drawing and Painting", "Choir", "Band", "Orchestra", "Jazz Band"});
        subjects.put("Math", new String[] {"Integrated 1", "Integrated 2", "Integrated 3", "Algebra 1", "Algebra 2", "Algebra 3", "Geometry", "Trigonometry", "Pre-Calculus", "AP Calculus AB", "AP Calculus BC", "AP Statistics"});
        subjects.put("History", new String[] {"AP US", "AP European", "AP World", "US", "European", "World"});
        subjects.put("Foreign Language", new String[] {"Spanish 1", "Spanish 2", "Spanish 3", "Spanish 4", "AP Spanish Language", "AP Spanish Literature", "French 1", "French 2", "French 3","French 4","AP French",
                "German 1","German 2", "German 3", "German 4", "AP German", "Latin 1", "Latin 2", "Latin 3", "Latin 4", "AP Latin", "Japanese 1", "Japanese 2", "Japanese 3", "Japanese 4", "AP Japanese",
                "Chinese 1", "Chinese 2", "Chinese 3", "Chinese 4", "AP Chinese" });
        subjects.put("Life Science", new String[] {"Biology 1", "Biology 2", "Honors Biology", "AP Biology", "AP Environmental"});
        subjects.put("Physical Science", new String[] {"Chemistry 1", "Chemistry 2", "Honors Chemistry", "AP Chemistry", "Physics 1", "Physics 2", "Honors Physics", "Physics 1: Algebra Based", "AP Physics 2: Algebra Based", "AP Physics C: Mechanics", "AP Physics C: Electricity and Magnetism", "AP Environmental"});
        subjects.put("Health", new String[] {"Health"});
        subjects.put("PE", new String[] {"PE"});
        subjects.put("Elective", new String[] {"Elective"});


    }


    @GetMapping({"/", "/home"})
    public String homeGet(Model model){
        System.out.println("*************** Request Sent *************");
        model.addAttribute("my_var", "Hello Sir");

        model.addAttribute("subjects", subjects);
        return "home";
    }

    @PostMapping({"/","/home"})
    public String homePost(@ModelAttribute Students students, Model model){
        System.out.println("*************** Post Received *************");
        System.out.println(students);
        int[][] schedule = this.scheduleService.getSchedule();
        model.addAttribute("schedule",schedule);
        return "result";
    }

}
