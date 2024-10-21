package MovieApp.MovieApp.Repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import MovieApp.MovieApp.Entity.Movie;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{
    // @Query("SELECT m FROM Movie m WHERE m.title = :name")
    @Query("select m from Movie m where m.title=:name")
    Movie findByName(@Param("name") String name);

    @Query("SELECT m FROM Movie m WHERE m.title LIKE :name%")
    List<Movie> Search(@Param("name") String name);

    List<Movie> findByGenre(String genre);
    List<Movie> findByIsPopularTrue();

    @Query("SELECT m FROM Movie m WHERE m.releaseDate >= :today")
    List<Movie> findUpcomingMovies(@Param("today")LocalDate today);
    
    @Query("SELECT m FROM Movie m WHERE m.releaseDate >= :today order  by m.releaseDate desc")
    List<Movie> findUpcomingMoviesDescending(@Param("today")LocalDate today);

    
}
