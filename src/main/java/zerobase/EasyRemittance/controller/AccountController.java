package zerobase.EasyRemittance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerobase.EasyRemittance.domain.Account;
import zerobase.EasyRemittance.dto.AccountDto;
import zerobase.EasyRemittance.service.AccountService;

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
    public Account regiAccount(@RequestBody @Validated AccountDto.regiAccount accountDto){
        Account result = accountService.regiAccount(accountDto);
        return result;
    }

    @DeleteMapping("/delete")
    public void deleteAccount(@RequestBody @Validated AccountDto.deleteAccount accountDto){
        accountService.deleteAccount(accountDto);
    }

    /**
     * 금액충전
     * @param accountDto
     */
    @PatchMapping("/charge")
    public void chargeAmount(@RequestBody AccountDto.chargeAmount accountDto){
        accountService.chargeAmount(accountDto);
    }
}
