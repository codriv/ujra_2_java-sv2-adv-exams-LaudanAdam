package videos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceIT {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    UserRepository repository = new UserRepository(factory);
    UserService service = new UserService(repository);

    User userTest1 = new User("Nagy Sándor", LocalDate.of(2022, 4, 5));

    Video videoTest1 = new Video("Titanic");
    Video videoTest2 = new Video("Holiday");
    Video videoTest3 = new Video("Birthday party");
    Video videoTest4 = new Video("Dogs");
    Video videoTest5 = new Video("Car exhibition");

    @BeforeEach
    void init() {
        repository.saveUser(userTest1);
    }

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    @DisplayName("Upload video and check status.")
    void uploadVideoTest() {
        assertEquals(UserStatus.BEGINNER, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
        service.uploadVideo(userTest1.getId(), videoTest1);
        assertEquals(UserStatus.BEGINNER, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
    }

    @Test
    void uploadAndFindVideoTest() {
        assertEquals(0, repository.findUserWithVideos(userTest1.getId()).getVideos().size());
        service.uploadVideo(userTest1.getId(), videoTest1);
        assertEquals(UserStatus.BEGINNER, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
        assertThat(repository.findUserWithVideos(userTest1.getId()).getVideos())
                .hasSize(1)
                .map(Video::getTitle)
                .containsOnly("Titanic");
        System.out.println(userTest1.getVideos());
    }

    @Test
    @DisplayName("Test status after 5 uploads.")
    void upload5VideosTest() {
        service.uploadVideo(userTest1.getId(), videoTest1);
        service.uploadVideo(userTest1.getId(), videoTest2);
        service.uploadVideo(userTest1.getId(), videoTest3);
        service.uploadVideo(userTest1.getId(), videoTest4);
        assertEquals(UserStatus.BEGINNER, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
        service.uploadVideo(userTest1.getId(), videoTest5);
        assertEquals(UserStatus.ADVANCED, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
    }
}