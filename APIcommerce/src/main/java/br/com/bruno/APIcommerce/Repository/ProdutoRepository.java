package br.com.bruno.APIcommerce.Repository;

import br.com.bruno.APIcommerce.Model.Categoria;
import br.com.bruno.APIcommerce.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoria(Categoria categoria);
    List<Produto> findByCategoriaAndNomeContaining(Categoria Categoria, String nome);
    List<Produto> findByCategoriaAndValorUnitarioBetween(Categoria categoria, BigDecimal valorMinimo, BigDecimal valorMaximo);

    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByValorUnitarioBetween(BigDecimal valorMinimo, BigDecimal valorMaximo);

}
