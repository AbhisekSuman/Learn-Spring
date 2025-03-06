package com.kodnest.SpringTask.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class Future {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/user/{future}")
    @ResponseBody
    public int displayFuture(@PathVariable int future) {
        int res = future + 10;
        return res;
    }

    @RequestMapping("/user/details")
    @ResponseBody
    public String displayDetails(@RequestBody Map<String, String> data) {
        String fName = data.get("fName");
        String lName = data.get("lName");
        String age = data.get("age");
        String gender = data.get("gen");
        String mark = data.get("mark");

        return(fName + " " + lName + " with age " + age + " year");
    }

    @RequestMapping("/user/result")
    public String calculatePercentage(@RequestParam("tot") int tot, @RequestParam("nos") int nos, Model model) {
        float res = ((float) tot / (nos * 100)) * 100;
        model.addAttribute("res", res);
        return "Result";
    }


}
