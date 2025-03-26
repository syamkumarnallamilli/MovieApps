
# Mockito Testing Notes

## 1. Mockito Basics
- **Mockito** is a popular Java framework for unit testing, allowing the creation of mock objects for testing the behavior of classes.
- **Mocking**: Simulates the behavior of real objects in a controlled way.
- **Verification**: Verifies that specific methods were called with expected arguments during testing.

## 2. Key Concepts
- **Mocks**: Fake objects that simulate the behavior of real dependencies.
- **Stubbing**: Defining the behavior of mock objects when specific methods are called.
- **Spying**: Partial mocking where real methods can also be called.

## 3. Common Mockito Methods

- **`when(...).thenReturn(...)`**:
  - Stubs a method to return a specific value when called.
  - Example:
    ```java
    when(movieRepository.findByGenre("Sci-Fi")).thenReturn(movies);
    ```

- **`thenThrow(...)`**:
  - Makes a mock throw an exception when a method is called.
  - Example:
    ```java
    when(movieRepository.findByGenre("Sci-Fi")).thenThrow(new RuntimeException("Error!"));
    ```

- **`thenAnswer(...)`**:
  - Provides custom behavior based on method inputs.
  - Example:
    ```java
    when(movieRepository.findByGenre("Sci-Fi")).thenAnswer(invocation -> new Movie("Inception", "Sci-Fi"));
    ```

- **`thenCallRealMethod()`**:
  - Calls the real method instead of a mock.
  - Example:
    ```java
    when(movieRepository.findByGenre("Sci-Fi")).thenCallRealMethod();
    ```

- **`doReturn(...).when(...)`**:
  - Alternative syntax for stubbing, useful for void methods.
  - Example:
    ```java
    doReturn(movies).when(movieRepository).findByGenre("Sci-Fi");
    ```

- **`doThrow(...).when(...)`**:
  - Stubs a void method to throw an exception.
  - Example:
    ```java
    doThrow(new RuntimeException("Error")).when(movieRepository).deleteAll();
    ```

- **`doAnswer(...).when(...)`**:
  - Custom behavior for void methods.
  - Example:
    ```java
    doAnswer(invocation -> {
        System.out.println("Deleting...");
        return null;
    }).when(movieRepository).deleteAll();
    ```

- **`verify(...)`**:
  - Verifies that a method was called a certain number of times with specific arguments.
  - Example:
    ```java
    verify(movieRepository, times(1)).findByGenre("Sci-Fi");
    ```

- **`verifyNoMoreInteractions(mock)`**:
  - Ensures no other interactions were made on the mock after the tested calls.

- **`reset(mock)`**:
  - Clears any stubbing or verifications made on a mock.

## 4. Advanced Mockito Techniques

- **`ArgumentCaptor<T>`**:
  - Captures method arguments for verification.
  - Example:
    ```java
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(movieRepository).findByGenre(captor.capture());
    assertEquals("Sci-Fi", captor.getValue());
    ```

- **`InOrder`**:
  - Verifies the order in which methods are called.
  - Example:
    ```java
    InOrder inOrder = inOrder(movieRepository);
    inOrder.verify(movieRepository).findByGenre("Sci-Fi");
    inOrder.verify(movieRepository).save(movie);
    ```

- **`spy()`**:
  - Creates a partial mock where some methods are real, and some are mocked.
  - Example:
    ```java
    MovieService spyService = spy(movieService);
    when(spyService.getMoviesByGenre("Sci-Fi")).thenReturn(movies);
    ```

## 5. Common Mockito Assertions
- **`assertEquals(expected, actual)`**: Verifies that the expected result matches the actual result.
- **`assertNotNull(object)`**: Verifies that the object is not null.
- **`assertThrows(Exception.class, () -> methodCall)`**: Verifies that an exception is thrown.
  
## 6. Example Mockito Test

```java
@Test
void testGetMoviesByGenre() {
    // Stubbing
    when(movieRepository.findByGenre("Sci-Fi")).thenReturn(Arrays.asList(movie));
    
    // Call method to test
    List<Movie> moviesByGenre = movieService.getMoviesByGenre("Sci-Fi");

    // Assertions
    assertNotNull(moviesByGenre);
    assertEquals(1, moviesByGenre.size());
    assertEquals("Sci-Fi", moviesByGenre.get(0).getGenre());

    // Verifying interaction
    verify(movieRepository, times(1)).findByGenre("Sci-Fi");
}