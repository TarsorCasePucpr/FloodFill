# Flood Fill com Pilha e Fila

TDE 2 — Estrutura de Dados (PUCPR). Aplicação em Java que implementa o algoritmo Flood Fill (balde de tinta) de duas formas, ambas com estruturas lineares próprias (sem `Stack`, `Queue` ou `Deque` do Java) e sem recursão:

- **Pilha** (`Stack`): preenchimento em profundidade.
- **Fila** (`Queue`): preenchimento em camadas.

A busca considera apenas 4 vizinhos (cima, baixo, esquerda, direita). A mudança de cor do pixel funciona como marca de visita, portanto não há lista de visitados.

## Requisitos

- JDK 11 ou superior (`javac` e `java` no PATH)
- Nenhuma biblioteca externa (usa apenas `java.awt.image.BufferedImage` e `javax.imageio.ImageIO` para abrir e salvar imagens)

## Estrutura

| Arquivo | Responsabilidade |
|---|---|
| `Position.java` | Linha e coluna de um pixel |
| `Node.java` | Nó encadeado que guarda uma `Position` |
| `Stack.java` | Pilha própria (`push`, `pop`, `isEmpty`) |
| `Queue.java` | Fila própria (`enqueue`, `dequeue`, `isEmpty`) |
| `FloodFill.java` | Algoritmo iterativo (versão pilha e versão fila) |
| `ImageService.java` | Abre a imagem, lê/altera pixels e salva as etapas |
| `Main.java` | Menu e validação das entradas |
| `testes/` | Imagens usadas nos testes |

## Compilar

```bash
javac *.java
```

## Executar

```bash
java Main
```

## Menu

```
1 - Executar com pilha
2 - Executar com fila
3 - Escolher imagem
4 - Escolher coordenada de inicio
5 - Escolher nova cor
0 - Encerrar
```

Uso típico: opção **3** (caminho da imagem) → **4** (X e Y) → opcionalmente **5** (cor) → **1** e/ou **2**. Após cada operação o programa volta ao menu. A cada execução a imagem é restaurada ao estado original, então pilha e fila podem ser executadas em sequência sobre a mesma imagem.

- **X** é a coluna e **Y** é a linha, ambos a partir de 0 no canto superior esquerdo.
- A cor padrão é vermelho (`FF0000`). Na opção 5 informe 6 dígitos hexadecimais (`RRGGBB`), com ou sem `#`.
- Se a nova cor for igual à cor do pixel inicial, o algoritmo encerra antes de começar.

## Saída

Cada execução grava as etapas em PNG (único formato usado), numeradas:

| Versão | Pasta |
|---|---|
| Pilha | `saida_pilha/` |
| Fila | `saida_fila/` |

- `passo_0001.png` é a imagem original.
- Os quadros seguintes (`passo_0002.png`, ...) mostram o preenchimento em andamento.
- O último quadro é a imagem final.
- Para imagens grandes, é salvo um quadro a cada N pixels pintados (o total fica limitado a cerca de 300 quadros).
- Ao iniciar uma execução, os arquivos `passo_*.png` antigos da pasta correspondente são removidos.

Comparando os quadros intermediários das duas pastas vê-se a diferença de ordem (pilha em profundidade, fila em camadas). A imagem final é a mesma nas duas versões.

## Arquivos de teste (`testes/`)

| Imagem | Tamanho | Sugestão |
|---|---|---|
| `regioes.png` | 60x40 | X=10, Y=10 preenche o retângulo azul (560 pixels); X=45, Y=20 preenche o círculo |
| `blocos.png` | 30x30 | X=5, Y=5 preenche um quadrante de cor sólida (225 pixels) |
| `labirinto.png` | 41x41 | X=0, Y=0 preenche o corredor externo (157 pixels); X=20, Y=20 preenche os corredores internos conectados (1216 pixels) |
| `teste.png` | 20x20 | Imagem simples inicial |

## Validações

O programa informa mensagens claras para: opção inválida, entrada não numérica, imagem não carregada, arquivo inexistente ou em formato não suportado, coordenada fora da imagem, cor hexadecimal inválida e nova cor igual à original.

## Equipe

- Gerard Gonzalez 
- Kaua Rubbo 
- Bismark Otto 
