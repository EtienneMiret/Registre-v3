package fr.elimerl.registre.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the home page.
 */
@Controller
public class Home {

  @RequestMapping("/")
  public ModelAndView home () {
    return new ModelAndView ("home");
  }

}
