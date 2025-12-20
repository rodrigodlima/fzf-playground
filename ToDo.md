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