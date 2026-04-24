package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Pedido;
import br.com.bruno.APIcommerce.Repository.PedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")

public class PedidoController {
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    public Pedido cadastrarPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

}
