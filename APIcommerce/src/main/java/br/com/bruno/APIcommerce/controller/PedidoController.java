package br.com.bruno.APIcommerce.controller;

import br.com.bruno.APIcommerce.model.Pedido;
import br.com.bruno.APIcommerce.repository.PedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")

public class PedidoController {
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping(produces = "application/json")
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Pedido cadastrarPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

}
