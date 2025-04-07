package com.IntegrityTool.Repositories.AuthenticationRepository;

import java.util.ArrayList;

import org.springframework.jdbc.core.SqlParameter;

public class SqlParamModel extends SqlParameter {

    public SqlParamModel(int sqlType) {
        super(sqlType);
    }
    public static final SqlParameter generateSqlParamModel(){
        return new SqlParameter(null, 0);
    }
}
