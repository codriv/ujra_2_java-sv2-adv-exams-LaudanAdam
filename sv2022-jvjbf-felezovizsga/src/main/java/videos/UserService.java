package videos;

import java.util.List;

public class UserService {

    private UserRepository repository;
    private final int MAXUPLOADS = 10;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void uploadVideo(long id, Video video) {
        List<User> usersWithMaxUploads = repository.findUsersWithMoreVideosThan(MAXUPLOADS - 1);
        if (usersWithMaxUploads.stream().map(User::getId).toList().contains(id)) {
            throw new IllegalStateException("You can not upload more than " + MAXUPLOADS + " videos!");
        }
        repository.updateUserWithVideo(id, video);
        if (repository.findUsersWithMoreVideosThan(4).stream().map(User::getId).toList().contains(id)) {
            repository.updateUserStatus(id, UserStatus.ADVANCED);
        }
    }
}