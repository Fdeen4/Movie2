package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    MovieRepository movieRepository;

    @RequestMapping("/home")
    public String homePage(Model model)
    {
        model.addAttribute("movie",new Movie());
        return "HomePage";
    }

    @GetMapping("/addmovie")
    public String movieForm(Model model)
    {
        model.addAttribute("movie",new Movie());
        return "movieform";
    }

    @GetMapping("/list")
    public String listMovies (Model model){
        model.addAttribute("movie", movieRepository.findAll());
        return "listmovie";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("movie") Movie movies, BindingResult result)
    {
        if (result.hasErrors()) {
            return "movieform";
        }
        movieRepository.save(movies);
        return "redirect:/list";
    }
    @RequestMapping("/detail/{id}")
    public String showMovie(@PathVariable("id") long id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") long id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).get());
        return "movieform";
    }

    @RequestMapping("/delete/{id}")
    public String delMovie(@PathVariable("id") long id){
        movieRepository.deleteById(id);
        return "redirect:/";
    }
}
