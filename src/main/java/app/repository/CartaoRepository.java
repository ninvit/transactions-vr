package app.repository;

import app.model.Cartao;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
