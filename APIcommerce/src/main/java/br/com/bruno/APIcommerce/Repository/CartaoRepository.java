package br.com.bruno.APIcommerce.Repository;

import br.com.bruno.APIcommerce.Model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
