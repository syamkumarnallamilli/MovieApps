package MovieApp.MovieApp;

import MovieApp.MovieApp.Entity.Movie;
import MovieApp.MovieApp.Repository.MovieRepository;
import MovieApp.MovieApp.Service.MovieService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private List<Movie> movies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create multiple movie instances
        movies = Arrays.asList(
            new Movie(1L, "Inception", "Sci-Fi", true, "poster1.jpg", LocalDate.of(2010, 7, 16)),
            new Movie(2L, "Interstellar", "Sci-Fi", true, "poster2.jpg", LocalDate.of(2014, 11, 7)),
            new Movie(3L, "The Dark Knight", "Action", true, "poster3.jpg", LocalDate.of(2008, 7, 18))
        );
    }

    @Test
    void testSavedMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movies.get(0));

        Movie savedMovie = movieService.saved(movies.get(0));

        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testGetPopularMovies() {
        when(movieRepository.findByIsPopularTrue()).thenReturn(movies);

        List<Movie> popularMovies = movieService.getPopularMovies();

        assertNotNull(popularMovies);
        assertEquals(3, popularMovies.size());
        assertTrue(popularMovies.stream().allMatch(Movie::isPopular));
        verify(movieRepository, times(1)).findByIsPopularTrue();
    }

    @Test
    void testGetMoviesByGenre() {
        when(movieRepository.findByGenre("Sci-Fi")).thenReturn(Arrays.asList(movies.get(0), movies.get(1)));

        List<Movie> moviesByGenre = movieService.getMoviesByGenre("Sci-Fi");

        assertNotNull(moviesByGenre);
        assertEquals(2, moviesByGenre.size());
        assertTrue(moviesByGenre.stream().allMatch(movie -> movie.getGenre().equals("Sci-Fi")));
        verify(movieRepository, times(1)).findByGenre("Sci-Fi");
    }

    @Test
    void testSearchMovieByName() {
        when(movieRepository.Search("Inception")).thenReturn(Arrays.asList(movies.get(0)));

        List<Movie> searchResults = movieService.search("Inception");

        assertNotNull(searchResults);
        assertEquals(1, searchResults.size());
        assertEquals("Inception", searchResults.get(0).getTitle());
        verify(movieRepository, times(1)).Search("Inception");
    }

    @Test
    void testSaveBulkMovies() {
        when(movieRepository.saveAll(anyList())).thenReturn(movies);

        List<Movie> savedMovies = movieService.savebulk(movies);

        assertNotNull(savedMovies);
        assertEquals(3, savedMovies.size());
        verify(movieRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFindMovieByName() {
        when(movieRepository.findByName("Inception")).thenReturn(movies.get(0));

        Movie foundMovie = movieService.findbyName("Inception");

        assertNotNull(foundMovie);
        assertEquals("Inception", foundMovie.getTitle());
        verify(movieRepository, times(1)).findByName("Inception");
    }

    @Test
    void testGetUpcomingMovies() {
        LocalDate today = LocalDate.now();
        when(movieRepository.findUpcomingMovies(today)).thenReturn(Arrays.asList(movies.get(1), movies.get(2)));

        List<Movie> upcomingMovies = movieService.getUpcomingMovies();

        assertNotNull(upcomingMovies);
        assertEquals(2, upcomingMovies.size());
        verify(movieRepository, times(1)).findUpcomingMovies(today);
    }

    @Test
    void testGetMoviesByDesc() {
        LocalDate today = LocalDate.now();
        when(movieRepository.findUpcomingMoviesDescending(today)).thenReturn(movies);

        List<Movie> upcomingMoviesDesc = movieService.getMoviesbydesc();

        assertNotNull(upcomingMoviesDesc);
        assertEquals(3, upcomingMoviesDesc.size());
        verify(movieRepository, times(1)).findUpcomingMoviesDescending(today);
    }

    @Test
    void testGetAllMovies() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Movie> page = new PageImpl<>(movies);
        when(movieRepository.findAll(pageable)).thenReturn(page);

        Page<Movie> allMovies = movieService.getAllMovies(0, 5);

        assertNotNull(allMovies);
        assertEquals(3, allMovies.getContent().size());
        verify(movieRepository, times(1)).findAll(pageable);
    }

    @Test
    void testDeleteMovie() {
        doNothing().when(movieRepository).deleteById(1L);

        String result = movieService.deletemovie(1L);

        assertEquals("Deleted Sucessfully", result);
        verify(movieRepository, times(1)).deleteById(1L);
    }
}
