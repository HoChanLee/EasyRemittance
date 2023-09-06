package zerobase.EasyRemittance.dto;

import lombok.*;

public class AccountDto {

    @Data
    public static class regiAccount {
        private String accountNumber;
        private long userId;
    }

    @Data
    public static class chargeAmount {
        private String accountNumber;
        private long balance;
    }
}
