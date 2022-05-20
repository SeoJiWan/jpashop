package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { // model 을 활용하여 data 를 view 에 넘길 수 있음.
        model.addAttribute("data", "지완 님!");
        return "hello";
    }
}
