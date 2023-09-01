package zerobase.EasyRemittance.dto;

import zerobase.EasyRemittance.domain.User;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

public class Auth {
    @Data
    public static class SignIn {
        private String userId;
        private String password;
    }

    @Data
    public static class SignUp {
        private String userId;
        private String password;
        private String name;
        private String email;
        private List<String> roles;

        public User toEntity(){
            return User.builder()
                    .userId(this.userId)
                    .password(this.password)
                    .name(this.name)
                    .email(this.email)
                    .regi_date(LocalDateTime.now())
                    .roles(this.roles)
                    .build();
        }

    }
}
