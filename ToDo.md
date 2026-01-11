# 2. **Interesting Technical Topics to Cover**

### A) **Fuzzy Matching Algorithm**
- File: `src/algo/algo.go`
- Show the different algorithms: FuzzyMatchV1, FuzzyMatchV2, ExactMatchNaive
- Explain the scoring system and how the ranking works
- Compare the performance between V1 and V2

### B) **Concurrency and Performance**
- File: `src/core.go` - `Run()` function
- How FZF uses goroutines to process input in parallel
- Channel pattern for cooperation
- Example of the `matcher.scan()` function that distributes work

### C) **Terminal Rendering**
- File: `src/tui/tui.go` and `src/terminal.go`
- How it handles the terminal (escape sequences)
- Double buffer to avoid flicker
- Function `renderer()` that updates the screen


1. Otimização de busca ASCII (Fase 1) - algo.go:454-460

// Phase 1. Optimized search for ASCII string
minIdx, maxIdx := asciiFuzzyIndex(input, pattern, caseSensitive)
Usa bytes.IndexByte() para pular caracteres que não fazem match
Reduz o espaço de busca drasticamente antes do algoritmo principal
2. Pre-computação de matrizes de bonus - algo.go:158-161

// A minor optimization that can give 15%+ performance boost
asciiCharClasses [unicode.MaxASCII + 1]charClass

// A minor optimization that can give yet another 5% performance boost
bonusMatrix [charNumber + 1][charNumber + 1]int16
+15% de performance com cache de classes de caracteres ASCII
+5% adicional com matriz de bonus pre-calculada
Evita cálculos repetidos durante o matching
3. Smith-Waterman com sistema de scoring inteligente - algo.go:112-146

const (
    scoreMatch        = 16
    scoreGapStart     = -3
    scoreGapExtension = -1
    bonusBoundary = scoreMatch / 2
    bonusCamel123 = bonusBoundary + scoreGapExtension
    bonusFirstCharMultiplier = 2
)
Prioriza matches em word boundaries (início de palavras)
Detecta camelCase automaticamente
Penaliza gaps mas recompensa sequências consecutivas
Multiplica bonus do primeiro caractere por 2x
Script rápido para falar:
"O fzf é rápido por 3 otimizações chave: Primeiro, usa bytes.IndexByte para pular caracteres irrelevantes antes do match principal. Segundo, pre-computa matrizes de bonus de caracteres, ganhando +20% de performance só nisso. Terceiro, usa Smith-Waterman modificado com scoring que detecta word boundaries e camelCase automaticamente - então quando você digita 'fb', ele prioriza 'FooBar' ao invés de 'Fooaaaaab'. Tudo isso mantendo complexidade O(nm) apenas quando há match."
Código para mostrar: