package br.com.bruno.APIcommerce.repository;

import br.com.bruno.APIcommerce.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
