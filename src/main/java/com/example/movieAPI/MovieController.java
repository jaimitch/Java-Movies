package com.example.movieAPI;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    private String url = "http://api.themoviedb.org/3/movie/now_playing/?api_key=be2a38521a7859c95e2d73c48786e4bb";

    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }


    @RequestMapping("/now-playing")
    public String nowPlaying(Model model) {

//        ResultsPage allresults = new ResultsPage();
//
//        //Change your /now-playing endpoint to call the method and add the resulting list of movies to the Model//
//        List movies = getMovies(url);
        model.addAttribute("movies", getMovies(url));

        return "now-playing";
    }


    @RequestMapping("/medium-popular-long-name")
    public String mediumPopularLongName(Model model) {

        List<Movie> movies = getMovies(url);

        List<Movie> filterMovies =
                movies.stream().filter(movie -> movie.getPopularity() > 30 && movie.getPopularity() < 80)
                .filter(movie -> movie.getTitle().length() > 10)
                .collect(Collectors.toList());

        model.addAttribute("movies", filterMovies);

        return "medium-popular-long-name";
    }


    public static List<Movie> getMovies(String route) {

        RestTemplate restTemplate = new RestTemplate();
        ResultsPage newresults = restTemplate.getForObject(route, ResultsPage.class);
        System.out.println(newresults);
        return newresults.results;
    }


}


