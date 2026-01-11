import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LogGenerator {

    private static final String[] LOG_LEVELS = {"DEBUG", "INFO", "WARN", "ERROR", "FATAL"};

    private static final String[] SERVICES = {
        "UserService", "AuthService", "PaymentService", "OrderService",
        "InventoryService", "NotificationService", "EmailService", "CacheService"
    };

    private static final String[] OPERATIONS = {
        "createUser", "updateUser", "deleteUser", "login", "logout",
        "processPayment", "refundPayment", "createOrder", "cancelOrder",
        "updateInventory", "sendEmail", "sendNotification", "cacheData",
        "validateToken", "generateReport", "exportData", "importData"
    };

    private static final String[] ERROR_MESSAGES = {
        "NullPointerException: Cannot invoke method on null object",
        "SQLException: Connection timeout after 30 seconds",
        "IOException: File not found: /tmp/data.txt",
        "IllegalArgumentException: Invalid parameter value",
        "OutOfMemoryError: Java heap space exceeded",
        "TimeoutException: Request timed out after 5000ms",
        "AuthenticationException: Invalid credentials provided",
        "ValidationException: Email format is invalid",
        "DatabaseException: Failed to execute query",
        "NetworkException: Connection refused to remote server"
    };

    private static final String[] INFO_MESSAGES = {
        "Request processed successfully",
        "User authenticated successfully",
        "Data synchronized with remote server",
        "Cache updated with new entries",
        "Email sent successfully to user",
        "Payment processed: amount=$%.2f",
        "New order created: orderId=%d",
        "Report generated successfully",
        "Database backup completed",
        "Health check passed"
    };

    private static final String[] WARN_MESSAGES = {
        "Deprecated API endpoint used",
        "High memory usage detected: %.2f%%",
        "Slow query detected: took %dms",
        "Rate limit approaching for user",
        "SSL certificate expires in %d days",
        "Disk space running low: %.2f%% used",
        "Connection pool nearly exhausted",
        "Unusual login attempt detected",
        "Legacy system integration warning",
        "Cache hit ratio below threshold"
    };

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final Random random = new Random();

    public static void main(String[] args) {
        String filename = args.length > 0 ? args[0] : "application.log";
        int numberOfLines = args.length > 1 ? Integer.parseInt(args[1]) : 10000;

        System.out.println("Generating " + numberOfLines + " log lines to " + filename);

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            LocalDateTime startTime = LocalDateTime.now().minusHours(24);

            for (int i = 0; i < numberOfLines; i++) {
                // Increment time randomly between 1-100 milliseconds
                startTime = startTime.plusNanos(ThreadLocalRandom.current().nextLong(1_000_000, 100_000_000));

                String logLine = generateLogLine(startTime);
                writer.println(logLine);

                // Print progress every 1000 lines
                if ((i + 1) % 1000 == 0) {
                    System.out.println("Generated " + (i + 1) + " lines...");
                }
            }

            System.out.println("Log generation completed!");
            System.out.println("\nYou can now use fzf to search logs:");
            System.out.println("  cat " + filename + " | fzf");
            System.out.println("  cat " + filename + " | fzf --preview 'echo {}'");
            System.out.println("  cat " + filename + " | fzf -q 'ERROR'");

        } catch (IOException e) {
            System.err.println("Error writing log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String generateLogLine(LocalDateTime timestamp) {
        String level = selectLogLevel();
        String service = SERVICES[random.nextInt(SERVICES.length)];
        String operation = OPERATIONS[random.nextInt(OPERATIONS.length)];
        String message = generateMessage(level);
        String threadId = String.format("thread-%d", random.nextInt(20));

        return String.format("%s [%s] [%s] [%s.%s] - %s",
            timestamp.format(FORMATTER),
            level,
            threadId,
            service,
            operation,
            message
        );
    }

    private static String selectLogLevel() {
        int rand = random.nextInt(100);
        if (rand < 50) return "INFO";      // 50%
        if (rand < 70) return "DEBUG";     // 20%
        if (rand < 85) return "WARN";      // 15%
        if (rand < 95) return "ERROR";     // 10%
        return "FATAL";                     // 5%
    }

    private static String generateMessage(String level) {
        switch (level) {
            case "ERROR":
            case "FATAL":
                String error = ERROR_MESSAGES[random.nextInt(ERROR_MESSAGES.length)];
                if (random.nextBoolean()) {
                    error += " [userId=" + random.nextInt(10000) + "]";
                }
                if (random.nextBoolean()) {
                    error += " {stacktrace: line " + random.nextInt(500) + "}";
                }
                return error;

            case "WARN":
                String warn = WARN_MESSAGES[random.nextInt(WARN_MESSAGES.length)];
                if (warn.contains("%d")) {
                    return String.format(warn, random.nextInt(1000));
                } else if (warn.contains("%.2f")) {
                    return String.format(warn, random.nextDouble() * 100);
                }
                return warn;

            case "INFO":
                String info = INFO_MESSAGES[random.nextInt(INFO_MESSAGES.length)];
                if (info.contains("%.2f")) {
                    return String.format(info, random.nextDouble() * 1000);
                } else if (info.contains("%d")) {
                    return String.format(info, random.nextInt(100000));
                }
                return info;

            case "DEBUG":
            default:
                return String.format("Processing request [requestId=%s, duration=%dms]",
                    generateRequestId(),
                    random.nextInt(1000)
                );
        }
    }

    private static String generateRequestId() {
        return String.format("%s-%s-%s",
            randomHex(8),
            randomHex(4),
            randomHex(4)
        );
    }

    private static String randomHex(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(Integer.toHexString(random.nextInt(16)));
        }
        return sb.toString();
    }
}
