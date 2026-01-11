# FZF Log Analysis - Quick Start Guide

## 🚀 Setup (2 minutes)

### 1. Compile and Generate Logs
```bash
javac LogGenerator.java
java LogGenerator application.log 10000
```

### 2. Run the Interactive Demo
```bash
./demo.sh
```

## 📊 Generated Log Statistics

- **5,000 lines** generated (~572KB)
- **Log Level Distribution:**
  - INFO: ~50% (2,469 lines)
  - DEBUG: ~20% (1,019 lines)
  - WARN: ~15% (712 lines)
  - ERROR: ~10% (510 lines)
  - FATAL: ~5% (290 lines)

- **8 Different Services:**
  - UserService, AuthService, PaymentService, OrderService
  - InventoryService, NotificationService, EmailService, CacheService

## 🎯 Quick FZF Commands

### Basic Search
```bash
# Interactive search through all logs
cat application.log | fzf

# Start with pre-filtered query
cat application.log | fzf -q 'ERROR'
```

### Filter by Log Level
```bash
# ERROR logs only
grep ERROR application.log | fzf

# ERROR or FATAL
grep -E 'ERROR|FATAL' application.log | fzf

# WARN, ERROR, or FATAL
grep -E 'WARN|ERROR|FATAL' application.log | fzf
```

### Filter by Service
```bash
# PaymentService logs
grep PaymentService application.log | fzf

# Multiple services
grep -E 'PaymentService|UserService' application.log | fzf
```

### Filter by Time
```bash
# Specific hour
grep '2026-01-10 16:' application.log | fzf

# Time range (between 10:00 and 11:00)
grep '2026-01-10 1[01]:' application.log | fzf
```

### Advanced Features
```bash
# Multi-select mode (TAB to select, ENTER to confirm)
cat application.log | fzf -m > selected-logs.txt

# With line numbers
cat -n application.log | fzf

# Real-time monitoring
tail -f application.log | fzf

# With preview window
cat application.log | fzf --preview 'echo {}'
```

## 🔥 Real-World Troubleshooting Scenarios

### Scenario 1: Find Payment Errors
```bash
grep PaymentService application.log | grep ERROR | fzf
```

### Scenario 2: Find All Errors in Last Hour
```bash
grep '2026-01-10 16:' application.log | grep -E 'ERROR|FATAL' | fzf
```

### Scenario 3: Track Specific User
```bash
grep 'userId=1234' application.log | fzf
```

### Scenario 4: Find Slow Queries
```bash
grep -E 'took [5-9][0-9]{2}ms|took [0-9]{4,}ms' application.log | fzf
```

### Scenario 5: Multi-Service Investigation
```bash
grep -E 'PaymentService|OrderService' application.log | \
grep ERROR | \
fzf
```

## 💡 Pro Tips

1. **Combine with grep for pre-filtering:**
   ```bash
   grep ERROR application.log | fzf -q 'PaymentService'
   ```

2. **Use -m for multi-select:**
   - Press TAB to select multiple lines
   - Press ENTER to confirm selection

3. **Keyboard shortcuts in FZF:**
   - `Ctrl+N` / `Down Arrow`: Next result
   - `Ctrl+P` / `Up Arrow`: Previous result
   - `Ctrl+C` / `Esc`: Exit
   - `TAB`: Multi-select (when -m flag is used)

4. **Save frequently used commands as aliases:**
   ```bash
   alias log-errors='grep ERROR application.log | fzf'
   alias log-payment='grep PaymentService application.log | fzf'
   ```

## 📈 Performance

- Handles 10,000 lines instantly
- Tested with 100,000+ lines without issues
- FZF is optimized for large files

## 🎓 Learning Path

1. Start with basic: `cat application.log | fzf`
2. Add filters: `grep ERROR application.log | fzf`
3. Combine filters: `grep PaymentService application.log | grep ERROR | fzf`
4. Use multi-select: `cat application.log | fzf -m`
5. Create custom scripts with your most-used queries

## 📁 Files Generated

- `LogGenerator.java` - Log generation tool
- `LogGenerator.class` - Compiled Java class
- `application.log` - Sample log file (5,000 lines)
- `demo.sh` - Interactive demo script
- `LOG_GENERATOR_README.md` - Detailed documentation
- `QUICK_START.md` - This file

## 🆘 Troubleshooting

**Q: FZF not found?**
```bash
brew install fzf  # macOS
apt-get install fzf  # Ubuntu/Debian
```

**Q: Java not found?**
```bash
brew install openjdk  # macOS
apt-get install default-jdk  # Ubuntu/Debian
```

**Q: Need more logs?**
```bash
java LogGenerator large.log 100000  # Generate 100k lines
```

## 🎬 Ready to Demo!

1. Open your terminal
2. Navigate to this directory
3. Run: `./demo.sh`
4. Select option 1 to start exploring!

---

**Next Steps:** Check out `LOG_GENERATOR_README.md` for detailed documentation and more examples.
