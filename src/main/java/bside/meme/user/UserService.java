package bside.meme.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private bside.meme.user.UserRepository userRepository;

    //회원가입
    public Long join(User user){
        validateDuplicateUser(user); //중복회원 검증
        userRepository.save(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
    }
    //회원 저넻조회
}
