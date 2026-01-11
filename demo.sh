#!/bin/bash

# FZF Log Analysis Demo Script
# This script provides quick access to common log analysis commands

set -e

LOG_FILE="application.log"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== FZF Log Analysis Demo ===${NC}\n"

# Check if log file exists
if [ ! -f "$LOG_FILE" ]; then
    echo -e "${YELLOW}Log file not found. Generating sample logs...${NC}"

    # Check if LogGenerator.class exists, if not compile it
    if [ ! -f "LogGenerator.class" ]; then
        echo -e "${YELLOW}Compiling LogGenerator...${NC}"
        javac LogGenerator.java
    fi

    echo -e "${YELLOW}Generating 10,000 log lines...${NC}"
    java LogGenerator "$LOG_FILE" 10000
    echo -e "${GREEN}✓ Log file generated!${NC}\n"
fi

# Display menu
echo "Select a demo option:"
echo ""
echo "  1) Basic FZF search (all logs)"
echo "  2) Search ERROR logs only"
echo "  3) Search ERROR or FATAL logs"
echo "  4) Filter by PaymentService"
echo "  5) Filter by UserService"
echo "  6) Filter by time range"
echo "  7) Show logs with line numbers"
echo "  8) Multi-select mode (press TAB to select, ENTER to confirm)"
echo "  9) Show log statistics"
echo " 10) Search with preview window"
echo " 11) Real-time monitoring (tail -f)"
echo " 12) Generate new log file"
echo ""
read -p "Enter option (1-12): " option

case $option in
    1)
        echo -e "\n${GREEN}Running: cat $LOG_FILE | fzf${NC}\n"
        cat "$LOG_FILE" | fzf
        ;;
    2)
        echo -e "\n${GREEN}Running: grep ERROR $LOG_FILE | fzf${NC}\n"
        grep ERROR "$LOG_FILE" | fzf
        ;;
    3)
        echo -e "\n${GREEN}Running: grep -E 'ERROR|FATAL' $LOG_FILE | fzf${NC}\n"
        grep -E 'ERROR|FATAL' "$LOG_FILE" | fzf
        ;;
    4)
        echo -e "\n${GREEN}Running: grep PaymentService $LOG_FILE | fzf${NC}\n"
        grep PaymentService "$LOG_FILE" | fzf
        ;;
    5)
        echo -e "\n${GREEN}Running: grep UserService $LOG_FILE | fzf${NC}\n"
        grep UserService "$LOG_FILE" | fzf
        ;;
    6)
        # Get first timestamp from log
        FIRST_TIME=$(head -1 "$LOG_FILE" | cut -d' ' -f1-2 | cut -d' ' -f1)
        echo -e "\n${GREEN}Running: grep '$FIRST_TIME' $LOG_FILE | fzf${NC}\n"
        grep "$FIRST_TIME" "$LOG_FILE" | fzf
        ;;
    7)
        echo -e "\n${GREEN}Running: cat -n $LOG_FILE | fzf${NC}\n"
        cat -n "$LOG_FILE" | fzf
        ;;
    8)
        echo -e "\n${GREEN}Running: cat $LOG_FILE | fzf -m${NC}"
        echo -e "${YELLOW}Tip: Press TAB to select multiple lines, ENTER to confirm${NC}\n"
        SELECTED=$(cat "$LOG_FILE" | fzf -m)
        echo -e "\n${GREEN}You selected:${NC}"
        echo "$SELECTED"
        echo -e "\n${YELLOW}Save to file? (y/n)${NC}"
        read -p "" save
        if [ "$save" = "y" ]; then
            echo "$SELECTED" > selected-logs.txt
            echo -e "${GREEN}✓ Saved to selected-logs.txt${NC}"
        fi
        ;;
    9)
        echo -e "\n${BLUE}=== Log Statistics ===${NC}"
        echo -e "${YELLOW}Total lines:${NC} $(wc -l < "$LOG_FILE")"
        echo ""
        echo -e "${YELLOW}Log levels:${NC}"
        grep -oE '\[(DEBUG|INFO|WARN|ERROR|FATAL)\]' "$LOG_FILE" | sort | uniq -c | sort -rn
        echo ""
        echo -e "${YELLOW}Top 5 services:${NC}"
        grep -oE '\[.*Service\.' "$LOG_FILE" | sed 's/\[//' | sed 's/\..*//' | sort | uniq -c | sort -rn | head -5
        echo ""
        ;;
    10)
        echo -e "\n${GREEN}Running: cat $LOG_FILE | fzf --preview 'echo {}'${NC}\n"
        cat "$LOG_FILE" | fzf --preview 'echo {}'
        ;;
    11)
        echo -e "\n${GREEN}Running: tail -f $LOG_FILE | fzf${NC}"
        echo -e "${YELLOW}Note: This will monitor the file in real-time. Press Ctrl+C to exit.${NC}\n"
        tail -f "$LOG_FILE" | fzf
        ;;
    12)
        read -p "Enter number of lines to generate: " lines
        read -p "Enter output filename (default: application.log): " filename
        filename=${filename:-application.log}

        echo -e "\n${YELLOW}Generating $lines log lines to $filename...${NC}"
        java LogGenerator "$filename" "$lines"
        echo -e "${GREEN}✓ Done!${NC}\n"
        ;;
    *)
        echo -e "${RED}Invalid option!${NC}"
        exit 1
        ;;
esac

echo -e "\n${GREEN}Demo completed!${NC}"
