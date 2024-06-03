package bside.meme.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(User user){
        if (user.getUserId() == null) {
            em.persist(user); // 새로운 엔티티는 persist
        } else {
            em.merge(user); // 이미 존재하는 엔티티는 merge
        }
        return user.getUserId();
    }

    public Optional<User> findById(Long userId) {
        try {
            User user = em.createQuery("select m from User m where m.userId = :user_id", User.class)
                    .setParameter("user_id", userId)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            System.out.println("###" + e);
            return Optional.empty();
        }
    }
    public List<User> findAll(){
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    public Optional<User> findByEmail(String email) {
        try {
            User user = em.createQuery("select m from User m where m.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            System.out.println("###" + e);
            return Optional.empty();
        }
    }
}
