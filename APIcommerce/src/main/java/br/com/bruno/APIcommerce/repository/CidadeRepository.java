package br.com.bruno.APIcommerce.repository;

import br.com.bruno.APIcommerce.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository <Cidade, Long> {
}
