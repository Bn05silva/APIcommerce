package br.com.bruno.APIcommerce.Repository;

import br.com.bruno.APIcommerce.Model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository <Cidade, Long> {
}
