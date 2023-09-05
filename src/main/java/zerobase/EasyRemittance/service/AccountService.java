package zerobase.EasyRemittance.service;

import zerobase.EasyRemittance.domain.Account;
import zerobase.EasyRemittance.dto.AccountDto;
import zerobase.EasyRemittance.repository.AccountRepository;
import zerobase.EasyRemittance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.EasyRemittance.type.AccountStatus;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    //계좌 추가
    @Transactional
    public Account regiAccount(AccountDto accountDto){
        boolean isUserId = userRepository.existsById(accountDto.getUserId());
        if(!isUserId){
            throw new RuntimeException("없는 user 입니다.");
        }
        Account entity = Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .userId(accountDto.getUserId())
                .balance(0L)
                .accountStatus(AccountStatus.IN_USE)
                .registered(LocalDateTime.now())
                .unregistered(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        Account save = accountRepository.save(entity);
        return save;
    }
}
