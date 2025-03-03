package com.demo.library.config;

import com.demo.library.service.BookDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SqlFileExecutorService {

    private static final Logger sfesLogger = LoggerFactory.getLogger(SqlFileExecutorService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public String executeSqlFile(String filePath) {
        String statusMessage = "SQL file executed failed.";
        try {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] sqlStatements = sql.split(";");

            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    jdbcTemplate.execute(sqlStatement);
                }
            }
            sfesLogger.info("SQL file executed successfully.");
            statusMessage = "SQL file executed successfully.";
        } catch (Exception e) {
            sfesLogger.error(statusMessage, e.getLocalizedMessage());
        }
        return statusMessage;
    }
}
