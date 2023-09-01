package zerobase.EasyRemittance.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.EasyRemittance.domain.User;
import zerobase.EasyRemittance.dto.Auth;
import zerobase.EasyRemittance.repository.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return this.userRepository.findByUserId(userName)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + userName));
    }

    // 파트너 회원가입
    public User register(Auth.SignUp user){
        boolean exists = this.userRepository.existsByUserId(user.getUserId());
        if(exists){
            throw new RuntimeException("이미 사용 중인 아이디 입니다.");
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword())); //비밀번호 인코딩
        User result = this.userRepository.save(user.toEntity());

        return result;
    }

    // 로그인시 검증
    public User authenticate(Auth.SignIn user){
        User result = this.userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

        if(!this.passwordEncoder.matches(user.getPassword(), result.getPassword())){ //password를 인코딩하여 비교
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return result;
    }
}
