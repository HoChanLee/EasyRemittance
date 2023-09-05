package zerobase.EasyRemittance.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import zerobase.EasyRemittance.type.AccountStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="account")
public class Account {
    @Id
    @GenericGenerator(name = "USER_GENERATOR", strategy = "uuid")
    private String accountNumber;

    private long userId;

    private long balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private LocalDateTime registered;
    private LocalDateTime unregistered;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
