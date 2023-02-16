package videos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    @DisplayName("Test with upload limit.")
    void uploadVideoTest() {
        User userTest = new User("Nagy Sándor", LocalDate.of(2022, 4, 5));
        userTest.setId(1L);
        Video videoTest1 = new Video("Titanic");
        when(repository.findUsersWithMoreVideosThan(9)).thenReturn(List.of(userTest));
        IllegalStateException ise = assertThrows(IllegalStateException.class, () -> service.uploadVideo(userTest.getId(), videoTest1));
        assertEquals("You can not upload more than 10 videos!", ise.getMessage());
        verify(repository, times(1)).findUsersWithMoreVideosThan(9);
    }
}