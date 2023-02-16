package videos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    UserRepository repository = new UserRepository(factory);

    User userTest1 = new User("Nagy Sándor", LocalDate.of(2022, 4, 5));
    User userTest2 = new User("Kis Mari", LocalDate.of(2020, 3, 2));
    User userTest3 = new User("Fekete Péter", LocalDate.of(2021, 6, 8));
    User userTest4 = new User("Fehér Kati", LocalDate.of(2015, 2, 19));

    Video videoTest1 = new Video("Titanic");
    Video videoTest2 = new Video("Holiday");
    Video videoTest3 = new Video("Birthday party");
    Video videoTest4 = new Video("Dogs");
    Video videoTest5 = new Video("Car exhibition");

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    void saveAndFindUserTest() {
        repository.saveUser(userTest1);
        User userFound = repository.findUserWithVideos(userTest1.getId());
        assertEquals("Nagy Sándor", userFound.getName());
        assertEquals(UserStatus.BEGINNER, userFound.getUserStatus());
    }

    @Nested
    class UserRepositoryTestWithInit {

        @BeforeEach
        void init() {
            repository.saveUser(userTest1);
        }

        @AfterEach
        void close() {
            factory.close();
        }

        @Test
        void updateUserWithVideoTest() {
            repository.updateUserWithVideo(userTest1.getId(), videoTest1);
            assertEquals(UserStatus.BEGINNER, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
            assertThat(repository.findUserWithVideos(userTest1.getId()).getVideos())
                    .hasSize(1)
                    .map(Video::getTitle)
                    .containsOnly("Titanic");
        }

        @Test
        void updateUserStatusTest() {
            assertEquals(UserStatus.BEGINNER, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
            repository.updateUserStatus(userTest1.getId(), UserStatus.ADVANCED);
            assertEquals(UserStatus.ADVANCED, repository.findUserWithVideos(userTest1.getId()).getUserStatus());
        }

        @Test
        void findUsersWithMoreVideosThanTest() {
            repository.saveUser(userTest2);
            repository.saveUser(userTest3);
            repository.saveUser(userTest4);
            repository.updateUserWithVideo(userTest1.getId(), videoTest1);
            repository.updateUserWithVideo(userTest2.getId(), videoTest2);
            repository.updateUserWithVideo(userTest2.getId(), videoTest3);
            repository.updateUserWithVideo(userTest2.getId(), videoTest4);
            repository.updateUserWithVideo(userTest3.getId(), videoTest5);
            assertThat(repository.findUsersWithMoreVideosThan(2))
                    .hasSize(1)
                    .map(User::getName)
                    .containsOnly("Kis Mari");
        }
    }
}