package zerobase.EasyRemittance.repository;

import zerobase.EasyRemittance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId); //회원 이름 중복 확인

    boolean existsById(Long id);
}
