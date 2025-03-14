Feature: Pesquisa de Livros na Biblioteca

  Background:
    Given a biblioteca contém os seguintes livros:
      | título                | autor         | publicado   |
      | O Senhor dos Anéis    | J.R.R. Tolkien | 1954-07-29 |
      | O Hobbit             | J.R.R. Tolkien | 1937-09-21 |
      | Dom Quixote          | Miguel de Cervantes | 1605-01-16 |
      | Cem Anos de Solidão  | Gabriel García Márquez | 1967-05-30 |

  Scenario: Procurar livros por autor
    When o cliente pesquisa por livros do autor "J.R.R. Tolkien"
    Then os seguintes livros são retornados:
      | título             |
      | O Senhor dos Anéis |
      | O Hobbit          |

  Scenario: Procurar livros publicados entre duas datas
    When o cliente pesquisa por livros publicados entre "1900-01-01" e "2000-12-31"
    Then os seguintes livros são retornados:
      | título                 |
      | O Senhor dos Anéis      |
      | O Hobbit               |
      | Cem Anos de Solidão    |
