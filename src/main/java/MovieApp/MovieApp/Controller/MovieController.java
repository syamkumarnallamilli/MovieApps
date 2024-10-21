package MovieApp.MovieApp.Controller;


import MovieApp.MovieApp.Entity.Movie;
import MovieApp.MovieApp.Service.MovieService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @PostMapping("saveMovies")
    public Movie saved(@Valid @RequestBody Movie m){
       
        return movieService.saved(m);
       

    }
    @GetMapping("/movies")
    public Page<Movie> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return movieService.getAllMovies(page, size);
            }
    
    
  
            @PostMapping("saveBulk")
    public List<Movie>saveBulk(@RequestBody List<Movie>m){
        return movieService.savebulk(m);
    }

    
    @GetMapping("/popular")
    public List<Movie> getPopularMovies() {
        return movieService.getPopularMovies();
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }
    @GetMapping("findbyname/{name}")
    public Movie getbyname(@PathVariable String name){
        return movieService.findbyName(name);
    }
    @GetMapping("Search/{name}")
    public List<Movie>getSimilar(@PathVariable String name){
        return movieService.search(name);
    }
    @GetMapping("/upcoming")
    public List<Movie> getUpcomingMovies() {
        return movieService.getUpcomingMovies();
    }
    @GetMapping("/desc")
    public List<Movie> getMoviesBasedondesc(){
        return movieService.getMoviesbydesc();
    }
    @DeleteMapping("/del/{id}")
    public String deletemovie(@PathVariable Long id){
        return movieService.deletemovie(id);
    }

}