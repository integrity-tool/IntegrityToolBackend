package com.IntegrityTool.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManager {
    private final SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public ConnectionManager(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
    }

    public SimpleJdbcCall getConnection() {
        return this.simpleJdbcCall;
    }
}
