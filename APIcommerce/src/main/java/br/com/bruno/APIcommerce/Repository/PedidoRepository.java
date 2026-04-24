package br.com.bruno.APIcommerce.Repository;

import br.com.bruno.APIcommerce.Model.Cliente;
import br.com.bruno.APIcommerce.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteIdAndDataCadastroAfter(Long clienteId, LocalDate datainicio);
    List<Pedido> findByClienteIdAndDataCadastroBefore(Long clienteId, LocalDate datafinal);
    List<Pedido> findByClienteIdAndDataCadastroBetween(Long clienteId, LocalDate datainicio, LocalDate datafinal);
}
