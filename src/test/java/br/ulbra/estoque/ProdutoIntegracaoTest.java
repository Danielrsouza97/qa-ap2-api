package br.ulbra.estoque;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepositoryEmMemoria;
import br.ulbra.estoque.servico.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoIntegracaoTest {

    private ProdutoService service;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        produto = new Produto("Caneca StarBucks", 89.99, 5);
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
    }

    @Test
    @DisplayName("Cadastra um produto com sucesso")
    public void cadastraProdutoComSucesso() {
        service.cadastrar(produto);
        Produto produtoEncontrado = service.buscarPorId(produto.getId());

        assertEquals("Caneca StarBucks", produtoEncontrado.getNome());
    }

    @Test
    @DisplayName("Realiza baixa em um produto")
    public void realizaBaixaEmUmProduto() {
        service.cadastrar(produto);
        Produto produtoBaixa = service.darBaixa(produto.getId(), 4);

        assertEquals(1, produtoBaixa.getEstoque());
    }

}
