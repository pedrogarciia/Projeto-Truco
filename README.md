# Projeto Truco

## 1. Descrição Geral
O projeto **Truco** consiste em uma implementação do tradicional jogo de cartas homônimo, desenvolvida em **Java**, com o objetivo de aplicar os principais conceitos de **Programação Orientada a Objetos (POO)**. O sistema simula partidas entre o jogador e o computador, incluindo lógica de decisão automatizada, gerenciamento de cartas e aplicação das regras básicas do truco.

A proposta busca não apenas reproduzir as mecânicas do jogo, mas também demonstrar boas práticas de estruturação de código, encapsulamento, herança e polimorfismo, de forma didática e organizada.

---

## 2. Objetivo e Justificativa
O principal objetivo do projeto é desenvolver um jogo interativo de truco que funcione via **interface de texto (CLI)**, possibilitando partidas completas entre um jogador humano e o computador. O projeto foi escolhido por sua combinação entre **lógica de decisão**, **aleatoriedade** e **interação**, o que o torna um excelente exercício de raciocínio lógico e aplicação prática dos conceitos de POO.

Além disso, o truco é um jogo culturalmente popular, o que facilita a compreensão das regras e torna o desenvolvimento mais intuitivo e envolvente.

---

## 3. Tecnologias Utilizadas
- **Linguagem:** Java (versão 8 ou superior)
- **Paradigma:** Programação Orientada a Objetos (POO)
- **Ambiente de execução:** Terminal (CLI)
- **Bibliotecas padrão:** `java.util.Random`, `java.util.List`, entre outras

---

## 4. Estrutura do Projeto
O código está dividido em classes independentes, cada uma responsável por uma parte específica da lógica do jogo:

| Classe | Função Principal |
|--------|------------------|
| `App.java` | Classe principal (método `main`) responsável por iniciar o jogo. |
| `Jogo.java` | Controla o fluxo principal da partida, alternando turnos e verificando condições de vitória. |
| `Partida.java` | Gerencia uma única rodada de truco, armazenando cartas jogadas e pontuação parcial. |
| `Jogador.java` | Representa o jogador humano, contendo métodos para escolha de cartas e interações. |
| `Computador.java` | Implementa o comportamento do adversário virtual (bot), incluindo decisões baseadas em probabilidade. |
| `Baralho.java` | Cria e embaralha o conjunto de cartas. |
| `Carta.java` | Modela os atributos de uma carta (naipe, valor, hierarquia). |
| `CartaEspecial.java` | Define cartas especiais com valores diferenciados. |
| `FabricaDeBots.java` | Gera oponentes com diferentes personalidades e níveis de dificuldade. |
| `Personalidade.java` | Enumera tipos de comportamento do computador (agressivo, cauteloso, equilibrado). |
| `GerenciadorCartasEspeciais.java` | Controla a ativação e uso das cartas especiais no jogo. |
| `Contexto.java` | Centraliza variáveis de contexto usadas entre as classes, facilitando a integração. |

Essa estrutura modular favorece a manutenção e expansão do projeto, permitindo a futura inclusão de interface gráfica (GUI) ou novos modos de jogo.

---

## 5. Como Executar o Projeto

### Pré-requisitos
- Ter o **Java JDK** instalado (versão 8 ou superior)
- Um **terminal** ou **IDE Java** (como IntelliJ IDEA, Eclipse ou VS Code com extensão Java)

### Passos para execução via terminal
1. **Abra o terminal** na pasta raiz do projeto.
2. **Compile os arquivos Java:**
   ```bash
   javac *.java
   ```
3. **Execute o programa:**
   ```bash
   java App
   ```

O jogo será iniciado diretamente no terminal, apresentando menus e opções de jogadas. Basta seguir as instruções exibidas na tela para participar da partida.
