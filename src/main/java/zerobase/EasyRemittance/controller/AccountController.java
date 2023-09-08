package zerobase.EasyRemittance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerobase.EasyRemittance.domain.Account;
import zerobase.EasyRemittance.dto.AccountDto;
import zerobase.EasyRemittance.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 계좌등록
     * @param accountDto
     * @return
     */
    @PostMapping("/registration")
    @PreAuthorize("hasRole('USER')")
    public Account regiAccount(@RequestBody @Validated AccountDto.regiAccount accountDto){
        Account result = accountService.regiAccount(accountDto);
        return result;
    }

    /**
     * 계좌 확인
     * @param userId
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Account> getAccounts(@RequestParam("user_id") long userId){
        List<Account> result = accountService.getAccounts(userId);
        return result;
    }

    /**
     * 계좌 삭제
     * @param accountDto
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public void deleteAccount(@RequestBody @Validated AccountDto.deleteAccount accountDto){
        accountService.deleteAccount(accountDto);
    }

    /**
     * 금액충전
     * @param accountDto
     */
    @PatchMapping("/charge")
    @PreAuthorize("hasRole('USER')")
    public void chargeAmount(@RequestBody AccountDto.chargeAmount accountDto){
        accountService.chargeAmount(accountDto);
    }
}
