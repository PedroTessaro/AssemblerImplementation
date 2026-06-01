# Assembler Implementation

Interpretador interativo, em Java, para uma linguagem de montagem (*assembly*) simplificada. O programa carrega instruções numeradas a partir de arquivos `.ed1`, mantém-nas em memória através de uma lista encadeada ordenada por número de linha e as executa sobre um conjunto de registradores.

## Visão geral

Cada programa é um conjunto de linhas no formato:

```
<número da linha> <instrução> <registrador> [registrador|valor]
```

As linhas são armazenadas em uma lista encadeada simples (`LinkedList`/`Node`), sempre ordenadas pelo número da linha, o que permite inserir, remover e saltar entre instruções de forma eficiente. Os registradores são representados pelas letras de `A` a `Z`, cada um guardando um valor inteiro.

## Requisitos

- JDK 8 ou superior

## Compilação e execução

```bash
javac *.java
java Main
```

Após iniciar, o programa exibe um prompt interativo (`>`) que aceita os comandos descritos abaixo.

## Comandos do interpretador

| Comando             | Descrição                                                        |
| ------------------- | ---------------------------------------------------------------- |
| `LOAD <arquivo.ed1>`| Carrega um arquivo de instruções para a memória.                 |
| `LIST`              | Lista todas as instruções atualmente em memória.                 |
| `RUN`               | Executa o programa carregado.                                    |
| `INS <linha> <instrução>` | Insere ou substitui a instrução na linha informada.        |
| `DEL <linha>`       | Remove a instrução da linha informada.                           |
| `DEL <início> <fim>`| Remove todas as instruções no intervalo de linhas informado.     |
| `SAVE`              | Salva as instruções no arquivo atual.                            |
| `SAVE <arquivo.ed1>`| Salva as instruções em um arquivo específico.                    |
| `EXIT`              | Encerra o interpretador.                                         |

## Conjunto de instruções

| Instrução      | Operação                                                                 |
| -------------- | ------------------------------------------------------------------------ |
| `MOV X v`      | Atribui ao registrador `X` um valor literal ou o conteúdo de outro registrador. |
| `ADD X v`      | Soma `v` (valor ou registrador) a `X`.                                   |
| `SUB X v`      | Subtrai `v` (valor ou registrador) de `X`.                               |
| `MUL X v`      | Multiplica `X` por `v` (valor ou registrador).                           |
| `DIV X v`      | Divide `X` por `v` (valor ou registrador), com tratamento de divisão por zero. |
| `INC X`        | Incrementa o registrador `X` em 1.                                        |
| `DEC X`        | Decrementa o registrador `X` em 1.                                        |
| `OUT X`        | Imprime o valor do registrador `X`.                                      |
| `JNZ X linha`  | Salta para `linha` caso o registrador `X` seja diferente de zero.        |

> Registradores precisam ser inicializados (via `MOV`) antes de serem lidos ou usados em operações.

## Exemplo

Arquivo `teste.ed1`:

```
10 mov a 10
20 mov b 2
30 sub a b
40 out a
50 jnz a 30
60 OUT A
```

Execução:

```
> LOAD teste.ed1
> RUN
```

O programa inicializa `A = 10` e `B = 2`, então subtrai `B` de `A` repetidamente, imprimindo o valor de `A` a cada iteração, até que `A` chegue a zero.

## Estrutura do projeto

```
.
├── Main.java        # Interpretador: leitura de comandos, parsing e execução das instruções
├── LinkedList.java  # Lista encadeada ordenada que armazena as instruções
├── Node.java        # Nó da lista (número da linha, código e referência ao próximo)
├── teste.ed1        # Programa de exemplo
└── LICENSE
```

## Licença

Distribuído sob a licença [MIT](LICENSE).
