package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Cartao;
import br.com.bruno.APIcommerce.Model.Cliente;
import br.com.bruno.APIcommerce.Model.Endereco;
import br.com.bruno.APIcommerce.Model.Pedido;
import br.com.bruno.APIcommerce.Repository.CartaoRepository;
import br.com.bruno.APIcommerce.Repository.ClienteRepository;
import br.com.bruno.APIcommerce.Repository.EnderecoRepository;
import br.com.bruno.APIcommerce.Repository.PedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/clientes")

public class ClienteController {
    private final ClienteRepository clienterepository;
    private final CartaoRepository cartaorepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidorepository;

    public ClienteController(ClienteRepository clienterepository, CartaoRepository cartaorepository, EnderecoRepository enderecoRepository, PedidoRepository pedidorepository) {
        this.clienterepository = clienterepository;
        this.cartaorepository = cartaorepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidorepository = pedidorepository;
    }


    @GetMapping
    public List<Cliente> listar() {
        return clienterepository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe clientes com esse id"));
    }

    @GetMapping("/{id}/endereco")
    public Endereco buscarEndereco(@PathVariable Long id) {
        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para esse endereco"));

        return cliente.getEndereco();
    }

    @GetMapping("{id}/pedidos")
    public List<Pedido> listarPedidosPeriodoTempo(
            @PathVariable Long id,
            @RequestParam (required = false) LocalDate datainicio,
            @RequestParam (required = false) LocalDate datafinal ) {

        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não existe"));

        if (datainicio == null && datafinal == null) {
            return cliente.getPedidos();
        }

        if (datainicio != null && datafinal == null) {
            return pedidorepository.findByClienteIdAndDataCadastroAfter(id, datainicio);
        }

        if (datainicio == null && datafinal != null) {
            return pedidorepository.findByClienteIdAndDataCadastroBefore(id, datafinal);
        }

        return pedidorepository.findByClienteIdAndDataCadastroBetween(id, datainicio, datafinal);

    }


    @GetMapping("/{id}/formas-de-pagamento")
    public List<Cartao> listarFormasDePagamento(@PathVariable Long id) {
        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("nenhuma forma de pagamento"));

        return cliente.getCartoes();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Cliente salvar(@RequestBody Cliente cliente) {
        return clienterepository.save(cliente);
    }

    @PostMapping("/{id}/formas-de-pagamento")
    public Cartao adicionarFormaDePagamento(@PathVariable Long id, @RequestBody Cartao cartao) {
        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cliente não encontrado"));

        cartao.setCliente(cliente);
        return cartaorepository.save(cartao);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return clienterepository.save(cliente);
    }

    @PutMapping("/{id}/endereco")
    public Endereco atualizarEndereco(@PathVariable Long id, @RequestBody Endereco enderecoAtualizado) {
        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não existe"));

        Endereco endereco = cliente.getEndereco();
        if (endereco == null) {
            throw new RuntimeException("endereço não encontrado para esse cliente");
        }

        endereco.setRua(enderecoAtualizado.getRua());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setComplemento(enderecoAtualizado.getComplemento());
        endereco.setTelefone(enderecoAtualizado.getTelefone());
        endereco.setBairro(enderecoAtualizado.getBairro());

        return enderecoRepository.save(endereco);

    }

    @PutMapping("/{idcliente}/forma-de-pagamento/{idformapagamento}")
    public Cartao atualizarCartaoFormaDePagamento(@PathVariable Long idcliente,@PathVariable Long idformapagamento, @RequestBody Cartao cartaoAtualizado) {
        Cliente cliente = clienterepository.findById(idcliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cartao cartao = cartaorepository.findById(idformapagamento)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        if (!cartao.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("cartão não pertence ao cliente informado");
        }

        cartao.setTipoCartao(cartaoAtualizado.getTipoCartao());
        cartao.setDataCriacao(cartaoAtualizado.getDataCriacao());
        cartao.setExcluido(cartaoAtualizado.getExcluido());

        return cartaorepository.save(cartao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienterepository.deleteById(id);
    }

    @DeleteMapping("/{idcliente}/forma-de-pagamento/{idformapagamento}")
    public void deletarFormaDePagamento(@PathVariable Long idcliente, @PathVariable Long idformapagamento) {
        Cliente cliente = clienterepository.findById(idcliente)
                .orElseThrow(() -> new RuntimeException("cliente não cadastrado"));

        Cartao cartao = cartaorepository.findById(idformapagamento)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        if (!cartao.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Cartão não percente ao cliente cadastrado");
        }

        cartaorepository.deleteById(idformapagamento);

    }



}
