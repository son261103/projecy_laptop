package org.example.dev_ban_hang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("")
    public String showIndex(){
        return "Layout/Index";
    }

}
