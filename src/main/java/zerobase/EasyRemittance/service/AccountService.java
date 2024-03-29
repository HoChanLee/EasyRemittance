package zerobase.EasyRemittance.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import zerobase.EasyRemittance.domain.Account;
import zerobase.EasyRemittance.domain.User;
import zerobase.EasyRemittance.dto.AccountDto;
import zerobase.EasyRemittance.repository.AccountRepository;
import zerobase.EasyRemittance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.EasyRemittance.type.AccountStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    //계좌 추가
    @Transactional
    public Account regiAccount(AccountDto.regiAccount accountDto){
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

    //계좌 확인
    @Transactional
    public List<Account> getAccounts(long userId){
        List<Account> accounts = accountRepository.findByUserId(userId).stream()
                .map(account ->
                        Account.builder()
                                .accountNumber(account.getAccountNumber())
                                .userId(account.getUserId())
                                .balance(account.getBalance())
                                .accountStatus(account.getAccountStatus())
                                .registered(account.getRegistered())
                                .unregistered(account.getUnregistered())
                                .createdAt(account.getCreatedAt())
                                .updatedAt(account.getUpdatedAt())
                                .build()
                )
                .collect(Collectors.toList());
        return accounts;
    }

    //계좌 삭제
    @Transactional
    public void deleteAccount(AccountDto.deleteAccount accountDto){
        Account account = accountRepository.findByAccountNumber(accountDto.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("계좌 번호확인"));

        User user = userRepository.findById(account.getUserId()).get();

        //password
        if(!user.getPassword().equals(user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        accountRepository.deleteById(account.getAccountNumber());
    }

    //금액 충전
    @Transactional
    public void chargeAmount(AccountDto.chargeAmount accountDto){
        Account account = accountRepository.findByAccountNumber(accountDto.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("계좌 번호확인"));

        long balance = account.getBalance() + accountDto.getBalance();
        account.setBalance(balance);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);
    }
}
