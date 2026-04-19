package br.com.bruno.APIcommerce.Repository;

import br.com.bruno.APIcommerce.Model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
