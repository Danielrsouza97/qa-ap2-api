# AP2 — Qualidade e Testes de Software

Projeto Java de controle de estoque usado na **AP2**. Ele já funciona: o seu trabalho é **testá-lo**.

## 🎯 O que você precisa entregar

| Tipo | Quantidade | Onde criar | Como |
|---|---|---|---|
| **Testes unitários** | **4** | `src/test/java/br/ulbra/estoque/ProdutoServiceTest.java` | `ProdutoService` com o repositório **mockado** (Mockito) |
| **Testes de integração** | **2** | `src/test/java/br/ulbra/estoque/ProdutoIntegracaoTest.java` | `ProdutoService` com o repositório **real** (`ProdutoRepositoryEmMemoria`) |

Total: **6 testes**, todos passando (`mvn test` verde).

> O arquivo `ExemploMockitoTest.java` é só um modelo. **Ele não conta** e não pode ser copiado como um dos seus testes.

## Como rodar

Pré-requisito: JDK 25 e IntelliJ.

```bash
git clone https://github.com/Danielrsouza97/qa-ap2-api.git
cd qa-ap2-api
mvn test
```

No IntelliJ: abra a pasta do projeto, espere o Maven baixar as dependências e clique na seta verde ao lado da classe de teste. Para ver o sistema funcionando, rode a classe `Main`.

## Regras de negócio (`ProdutoService`)

- **R1** — O nome do produto é obrigatório.
- **R2** — O preço deve ser maior que zero.
- **R3** — O estoque inicial não pode ser negativo.
- **R4** — A baixa de estoque exige quantidade maior que zero.
- **R5** — Não é possível dar baixa de mais unidades do que existem em estoque.
- **R6** — Buscar um produto que não existe lança `ProdutoNaoEncontradoException`.

Regras R1 a R5 violadas lançam `IllegalArgumentException`.

## Estrutura

```
src/main/java/br/ulbra/estoque/
├── Main.java                                  ← demonstração
├── modelo/Produto.java                        ← o produto
├── repositorio/ProdutoRepository.java         ← interface (é ela que você mocka)
├── repositorio/ProdutoRepositoryEmMemoria.java ← implementação real, em memória
├── servico/ProdutoService.java                ← as regras R1 a R6
└── servico/ProdutoNaoEncontradoException.java
```
