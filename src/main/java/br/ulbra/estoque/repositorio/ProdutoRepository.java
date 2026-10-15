package br.ulbra.estoque.repositorio;

import br.ulbra.estoque.modelo.Produto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Guarda os produtos em memoria (sem banco de dados).
 * Tudo some quando a aplicacao reinicia.
 */
@Repository
public class ProdutoRepository {

    private final Map<Long, Produto> produtos = new LinkedHashMap<>();
    private long proximoId = 1;

    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(proximoId);
            proximoId++;
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtos.get(id));
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }
}
