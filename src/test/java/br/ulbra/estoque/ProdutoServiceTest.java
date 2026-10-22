package br.ulbra.estoque;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepository;
import br.ulbra.estoque.servico.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Mouse Logitech", 150.00, 1);
        produto.setId(1L);
    }

    @Test
    @DisplayName("Deve cadastrar o produto quando o nome é considerado válido.")
    void deveCadastrarQuandoNomeProdutoValido() {
        when(repository.salvar(produto)).thenReturn(produto);

        Produto resultado = service.cadastrar(produto);

        assertEquals("Mouse Logitech", resultado.getNome());
        verify(repository).salvar(produto);
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {"  "})
    @DisplayName("Deve lançar erro ao tentar cadastrar um produto com nome inválido")
    void deveLancarErroQuandoNomeProdutoInvalido(String nome) {
        produto.setNome(nome);

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(produto));
        verify(repository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve cadastrar o produto quando o valor é o menor valor válido (0.01).")
    void deveCadastrarQuandoValorValido() {
        produto.setPreco(0.01);
        when(repository.salvar(produto)).thenReturn(produto);

        Produto resultado = service.cadastrar(produto);

        assertEquals(0.01, resultado.getPreco());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0})
    @DisplayName("Deve lançar erro ao tentar cadastrar um produto com valor inválido")
    void deveLancarErroQuandoValorInvalido(int valor) {
        produto.setPreco(valor);

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(produto));
        verify(repository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve cadastrar o produto quando o estoque inicial é zero")
    void deveCadastrarQuandoEstoqueValido() {
        produto.setEstoque(0);
        when(repository.salvar(produto)).thenReturn(produto);

        Produto resultado = service.cadastrar(produto);

        assertEquals(0, resultado.getEstoque());
    }

    @Test
    @DisplayName("Deve lançar erro quando tentar cadastrar um produto com estoque considerado inválido")
    void deveLancarErroQuandoEstoqueInvalido() {
        produto.setEstoque(-1);

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(produto));
        verify(repository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve dar baixa no valor de estoque do produto")
    void deveDarBaixaNoValorDeEstoqueDoProduto() {
        when(repository.buscarPorId(produto.getId())).thenReturn(Optional.of(produto));

        service.darBaixa(produto.getId(), 1);

        assertEquals(0, produto.getEstoque());
        verify(repository).salvar(produto);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0})
    @DisplayName("Deve lançar erro quando tentar dar baixa com uma quantidade inválida")
    void deveLancarErroQuandoQuantidadeBaixaInvalida(int quantidade) {
        assertThrows(IllegalArgumentException.class, () -> service.darBaixa(produto.getId(), quantidade));
        verify(repository, never()).buscarPorId(anyLong());
        verify(repository, never()).salvar(any());
    },

}
