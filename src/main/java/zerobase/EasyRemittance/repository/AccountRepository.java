package zerobase.EasyRemittance.repository;

import org.springframework.transaction.annotation.Transactional;
import zerobase.EasyRemittance.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
