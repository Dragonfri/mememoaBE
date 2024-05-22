package bside.meme;


import bside.meme.user.User;
import bside.meme.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testUser() throws Exception {
        User user = new User();
        user.setName("ykim");

        Long saveId = userRepository.save(user);
        User findUser = userRepository.findOne(saveId);

        Assertions.assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
    }
}