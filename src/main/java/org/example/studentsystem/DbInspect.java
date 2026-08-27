package org.example.studentsystem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 开发工具：重建 operation_log 表并查看数据。
 * 运行：./mvnw -q compile exec:java -Dexec.mainClass=org.example.studentsystem.DbInspect
 */
public class DbInspect {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/student_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASS = "Hp08160816.";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("=== 连接成功: student_system ===\n");

            if (args.length > 0 && "migrate".equals(args[0])) {
                migrate(stmt);
            }

            printTableCount(stmt, "operation_log");

            System.out.println("\n=== operation_log 表结构 ===");
            try (ResultSet rs = stmt.executeQuery("DESCRIBE operation_log")) {
                while (rs.next()) {
                    System.out.printf("  %-20s %s%n", rs.getString("Field"), rs.getString("Type"));
                }
            }

            System.out.println("\n=== operation_log 最近 5 条 ===");
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT id, module, operation, description, status, created_at FROM operation_log ORDER BY created_at DESC LIMIT 5")) {
                boolean hasRow = false;
                while (rs.next()) {
                    hasRow = true;
                    System.out.printf("id=%d | %s/%s | %s | status=%d | %s%n",
                            rs.getLong("id"),
                            rs.getString("module"),
                            rs.getString("operation"),
                            rs.getString("description"),
                            rs.getInt("status"),
                            rs.getTimestamp("created_at"));
                }
                if (!hasRow) {
                    System.out.println("(暂无数据)");
                }
            }
        }
    }

    private static void migrate(Statement stmt) throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/sql/operation_log.sql"));
        for (String statement : sql.split(";")) {
            String trimmed = statement.trim();
            if (!trimmed.isEmpty()) {
                stmt.execute(trimmed);
            }
        }
        System.out.println("operation_log 表已重建（旧数据已清空）\n");
    }

    private static void printTableCount(Statement stmt, String table) throws Exception {
        try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS cnt FROM " + table)) {
            rs.next();
            System.out.println(table + " 表记录数: " + rs.getInt("cnt"));
        }
    }
}
