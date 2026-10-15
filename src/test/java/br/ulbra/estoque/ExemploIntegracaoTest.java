package br.ulbra.estoque;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * EXEMPLO de teste de integração — NÃO conta para a AP2.
 * Use como modelo para escrever os seus.
 *
 * @SpringBootTest sobe a aplicação inteira (controller + serviço + repositório).
 * @AutoConfigureMockMvc entrega um MockMvc, que faz requisições HTTP
 * de mentira para a API, sem precisar abrir o navegador ou o Postman.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExemploIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Exemplo: cadastrar produto válido devolve 201 com o produto e o id gerado")
    void cadastrarProdutoValidoDevolve201() throws Exception {
        String produtoJson = """
                {"nome": "Caneta", "preco": 2.50, "estoque": 100}
                """;

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Caneta"))
                .andExpect(jsonPath("$.estoque").value(100));
    }
}
