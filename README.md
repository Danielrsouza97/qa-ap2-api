# AP2 — Qualidade e Testes de Software

API de estoque usada na **AP2**. Ela já funciona: o seu trabalho é **testá-la**.

## 🎯 O que você precisa entregar

| Tipo | Quantidade | Onde criar |
|---|---|---|
| **Testes unitários** | **4** | `src/test/java/br/ulbra/estoque/ProdutoServiceTest.java` |
| **Testes de integração** | **2** | `src/test/java/br/ulbra/estoque/ProdutoIntegracaoTest.java` |

Total: **6 testes**, todos passando (`mvn test` verde).

> O arquivo `ExemploIntegracaoTest.java` é só um modelo. **Ele não conta** e não pode ser copiado como um dos seus 2 testes de integração.

## Como rodar

Pré-requisito: JDK 25 e IntelliJ (ou Maven instalado).

```bash
git clone https://github.com/Danielrsouza97/qa-ap2-api.git
cd qa-ap2-api
mvn test                  # roda os testes
mvn spring-boot:run       # sobe a API em http://localhost:8080
```

No IntelliJ: abra a pasta do projeto, espere o Maven baixar as dependências e clique na seta verde ao lado da classe de teste.

## A API

| Método | Caminho | O que faz | Resposta |
|---|---|---|---|
| `POST` | `/produtos` | Cadastra um produto | `201` com o produto, ou `400` se inválido |
| `GET` | `/produtos` | Lista todos | `200` |
| `GET` | `/produtos/{id}` | Busca um produto | `200`, ou `404` se não existir |
| `POST` | `/produtos/{id}/baixa?quantidade=N` | Tira `N` unidades do estoque | `200`, `400` ou `404` |

Exemplo de corpo para o `POST /produtos`:

```json
{ "nome": "Caneta", "preco": 2.50, "estoque": 100 }
```

## Regras de negócio (`ProdutoService`)

- **R1** — O nome do produto é obrigatório.
- **R2** — O preço deve ser maior que zero.
- **R3** — O estoque inicial não pode ser negativo.
- **R4** — A baixa de estoque exige quantidade maior que zero.
- **R5** — Não é possível dar baixa de mais unidades do que existem em estoque.
- **R6** — Buscar um produto que não existe lança `ProdutoNaoEncontradoException`.

Regras violadas viram `IllegalArgumentException` no serviço e `400` na API. Produto inexistente vira `404`.

## Estrutura

```
src/main/java/br/ulbra/estoque/
├── EstoqueApplication.java          ← sobe a API
├── modelo/Produto.java              ← o produto
├── repositorio/ProdutoRepository.java  ← guarda em memória (sem banco)
├── servico/ProdutoService.java      ← as regras R1 a R6
└── controlador/ProdutoController.java  ← os endpoints HTTP
```
