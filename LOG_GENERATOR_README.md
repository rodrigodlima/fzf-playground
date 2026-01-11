# Log Generator for FZF Demo

A Java application that generates realistic application logs for demonstrating FZF capabilities.

## Features

- Generates thousands of realistic log lines
- Multiple log levels: DEBUG, INFO, WARN, ERROR, FATAL
- Various services and operations
- Realistic timestamps spanning 24 hours
- Different error messages, warnings, and info messages
- Thread information and request IDs

## Compilation

```bash
javac LogGenerator.java
```

## Usage

### Generate default log (10,000 lines to application.log)
```bash
java LogGenerator
```

### Generate custom number of lines
```bash
java LogGenerator application.log 50000
```

### Generate to specific file
```bash
java LogGenerator my-custom.log 5000
```

## FZF Usage Examples

### Basic search through logs
```bash
cat application.log | fzf
```

### Search for ERROR logs only
```bash
cat application.log | fzf -q 'ERROR'
```

### Search with preview
```bash
cat application.log | fzf --preview 'echo {}'
```

### Search ERROR or FATAL logs
```bash
grep -E 'ERROR|FATAL' application.log | fzf
```

### Search logs by service
```bash
cat application.log | fzf -q 'UserService'
```

### Search logs by time range
```bash
grep '2026-01-11 10:' application.log | fzf
```

### Interactive search with line numbers
```bash
cat -n application.log | fzf
```

### Search and open in editor
```bash
cat application.log | fzf | xargs -I {} sh -c 'echo "{}" | less'
```

### Filter by multiple criteria
```bash
cat application.log | fzf --query 'ERROR PaymentService'
```

### Count occurrences of selected pattern
```bash
PATTERN=$(cat application.log | fzf --print-query | tail -1) && grep -c "$PATTERN" application.log
```

## Advanced FZF Examples

### Multi-select and save to file
```bash
cat application.log | fzf -m > selected-logs.txt
```

### Search with context (show surrounding lines)
```bash
cat application.log | fzf --preview 'grep -C 3 {} application.log'
```

### Real-time log monitoring with FZF
```bash
tail -f application.log | fzf
```

### Group by log level and search
```bash
awk '{print $3}' application.log | sort | uniq -c | fzf
```

## Log Format

```
YYYY-MM-DD HH:mm:ss.SSS [LEVEL] [thread-X] [Service.operation] - Message
```

Example:
```
2026-01-11 10:30:45.123 [ERROR] [thread-5] [PaymentService.processPayment] - SQLException: Connection timeout after 30 seconds [userId=1234]
```

## Tips

1. Use `-q` flag to start with a pre-filled query
2. Use `-m` flag for multi-select mode
3. Use `--preview` to see more context
4. Combine with `grep` for initial filtering
5. Use `Ctrl+R` in terminal with FZF for command history search

## Performance

- Generates ~10,000 lines in ~1 second
- Can generate millions of lines for stress testing
- FZF handles large files efficiently (tested with 1M+ lines)
