package videos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserRepository {

    private EntityManagerFactory factory;

    public UserRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public User saveUser(User user) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public User findUserWithVideos(long userId) {
        EntityManager em = factory.createEntityManager();
        return em.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.videos WHERE u.id = :id", User.class)
                .setParameter("id", userId)
                .getSingleResult();
    }

    public void updateUserWithVideo(long userId, Video video) {
        EntityManager em = factory.createEntityManager();
        User user = em.find(User.class, userId);
        video.setUser(user);
        em.getTransaction().begin();
        em.persist(video);
        em.getTransaction().commit();
        em.close();
    }

    public User updateUserStatus(long userId, UserStatus status) {
        EntityManager em = factory.createEntityManager();
        User user = em.find(User.class, userId);
        em.getTransaction().begin();
        user.setUserStatus(status);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public List<User> findUsersWithMoreVideosThan(int amount) {
        EntityManager em = factory.createEntityManager();
        return em.createQuery("SELECT u FROM User u WHERE SIZE(u.videos) > :amount", User.class)
                .setParameter("amount", amount)
                .getResultList();
    }
}