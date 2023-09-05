package zerobase.EasyRemittance.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String accountNumber;
    private long userId;
}
