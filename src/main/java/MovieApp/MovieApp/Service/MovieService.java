package MovieApp.MovieApp.Service;



import MovieApp.MovieApp.Entity.Movie;
import MovieApp.MovieApp.Repository.MovieRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {
   
private final Logger log=LoggerFactory.getLogger(MovieService.class);
    @Autowired
    private  MovieRepository movieRepository;


  
    public Movie saved(Movie m){
     log.info("Saved SucessFully");
        return movieRepository.save(m);
    }

    public List<Movie> getPopularMovies() {
        log.debug("Fetching Movies Based on Popular");
        List<Movie> popularMovies = movieRepository.findByIsPopularTrue();
        log.debug("Found {} popular movies", popularMovies.size());
        return popularMovies;
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }
    public List<Movie> search(String name){
        return movieRepository.Search(name);
    }
    public List<Movie> savebulk(List<Movie>m){
        return movieRepository.saveAll(m);
    }

    @Cacheable(value = "movies",key = "#name")
    public Movie findbyName(String name){
        return movieRepository.findByName(name);
    }
    
    public List<Movie> getUpcomingMovies() {
        LocalDate today = LocalDate.now();
        return movieRepository.findUpcomingMovies(today);
}
public List<Movie> getMoviesbydesc() {
    LocalDate today = LocalDate.now();
    return movieRepository.findUpcomingMoviesDescending(today);
}
public Page<Movie>getAllMovies(int page,int size){
    Pageable p=PageRequest.of(page, size);
    return movieRepository.findAll(p);
}

public String deletemovie(Long id){
    movieRepository.deleteById(id);
    return "Deleted Sucessfully";
}
}