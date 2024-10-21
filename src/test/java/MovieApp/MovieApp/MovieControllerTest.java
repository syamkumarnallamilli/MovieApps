package MovieApp.MovieApp;

import MovieApp.MovieApp.Controller.MovieController;
import MovieApp.MovieApp.Entity.Movie;
import MovieApp.MovieApp.Service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;
    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito annotations
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build(); // Initialize MockMvc
        movie = new Movie(1L, "Inception", "Sci-Fi", true, "inception.jpg", LocalDate.of(2010, 7, 16));
    }

    @Test
    void testSaveMovie() throws Exception {
        when(movieService.saved(any(Movie.class))).thenReturn(movie);

        String movieJson = "{ \"title\": \"Inception\", \"genre\": \"Sci-Fi\", \"isPopular\": true, \"poster\": \"inception.jpg\", \"releaseDate\": \"2010-07-16\" }";

        mockMvc.perform(post("/api/movies/saveMovies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"));

        verify(movieService, times(1)).saved(any(Movie.class));
    }

    @Test
    void testGetAllMovies() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Movie> moviePage = new PageImpl<>(List.of(movie), pageable, 1);

        when(movieService.getAllMovies(0, 10)).thenReturn(moviePage);

        mockMvc.perform(get("/api/movies/movies")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Inception"))
                .andExpect(jsonPath("$.content[0].genre").value("Sci-Fi"));

        verify(movieService, times(1)).getAllMovies(0, 10);
    }

    @Test
    void testGetPopularMovies() throws Exception {
        when(movieService.getPopularMovies()).thenReturn(Arrays.asList(movie));

        mockMvc.perform(get("/api/movies/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[0].isPopular").value(true));

        verify(movieService, times(1)).getPopularMovies();
    }

    @Test
    void testGetMoviesByGenre() throws Exception {
        when(movieService.getMoviesByGenre("Sci-Fi")).thenReturn(Arrays.asList(movie));

        mockMvc.perform(get("/api/movies/genre/Sci-Fi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[0].genre").value("Sci-Fi"));

        verify(movieService, times(1)).getMoviesByGenre("Sci-Fi");
    }

    @Test
    void testFindByName() throws Exception {
        when(movieService.findbyName("Inception")).thenReturn(movie);

        mockMvc.perform(get("/api/movies/findbyname/Inception"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"));

        verify(movieService, times(1)).findbyName("Inception");
    }

    @Test
    void testSearchMovies() throws Exception {
        when(movieService.search("Inception")).thenReturn(Arrays.asList(movie));

        mockMvc.perform(get("/api/movies/Search/Inception"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[0].genre").value("Sci-Fi"));

        verify(movieService, times(1)).search("Inception");
    }

    @Test
    void testGetUpcomingMovies() throws Exception {
        when(movieService.getUpcomingMovies()).thenReturn(Arrays.asList(movie));

        mockMvc.perform(get("/api/movies/upcoming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"));

        verify(movieService, times(1)).getUpcomingMovies();
    }

    @Test
        void testDeleteMovie() throws Exception {
        when(movieService.deletemovie(1L)).thenReturn("Deleted Sucessfully");

             mockMvc.perform(delete("/api/movies/del/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Deleted Sucessfully"));

        verify(movieService, times(1)).deletemovie(1L);
}

}
