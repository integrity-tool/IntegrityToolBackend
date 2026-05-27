package com.IntegrityTool.Repositories.AuthenticationRepository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.IntegrityTool.Repositories.Interface.IAuthenticationModule;
import com.IntegrityTool.model.Authentication.LoginParam;
import com.IntegrityTool.model.abstractClasses.Person;
import com.IntegrityTool.service.util.CommonService;
import com.IntegrityTool.service.util.ConnectionManager;
import com.IntegrityTool.service.util.SqlQueryLoader;

@Repository
public class AuthenticationRepository implements IAuthenticationModule {

    private final ConnectionManager connectionManager;
    private final SqlQueryLoader sqlQueryLoader;


    @Autowired
    public AuthenticationRepository(ConnectionManager connectionManager,SqlQueryLoader sqlQueryLoader) {
        this.connectionManager = connectionManager;
        this.sqlQueryLoader = sqlQueryLoader;
    }

    @Override
    public Map<String, Object> registerUser(Person person) {
        SimpleJdbcCall simpleJdbcCall = this.connectionManager.getConnection()
                .withProcedureName("Register_user")
                .declareParameters(
                        new SqlParameter("personId", Types.VARCHAR),
                        new SqlParameter("firstName", Types.VARCHAR),
                        new SqlParameter("lastName", Types.VARCHAR),
                        new SqlParameter("email", Types.VARCHAR),
                        new SqlParameter("gender", Types.VARCHAR),
                        new SqlParameter("dateOfBirth", Types.DATE),
                        new SqlParameter("password", Types.VARCHAR),
                        new SqlParameter("isActive", Types.BIT),
                        new SqlParameter("streetNo", Types.VARCHAR),
                        new SqlParameter("city", Types.VARCHAR),
                        new SqlParameter("state", Types.VARCHAR),
                        new SqlParameter("zipcode", Types.VARCHAR),
                        new SqlParameter("country", Types.VARCHAR),
                        new SqlParameter("createdAt", Types.TIMESTAMP),
                        new SqlOutParameter("userid", Types.VARCHAR));

        Map<String, Object> inParams = new HashMap<>();
        inParams.put(CommonService.convertToSnakeCase("personId"), person.getPersonId().toString());
        inParams.put(CommonService.convertToSnakeCase("firstName"), person.getFirstName());
        inParams.put(CommonService.convertToSnakeCase("lastName"), person.getLastName());
        inParams.put("email", person.getEmail());
        inParams.put("gender", person.getGender());
        inParams.put(CommonService.convertToSnakeCase("dateOfBirth"), person.getDateOfBirth());
        inParams.put("password", person.getPassword());
        inParams.put(CommonService.convertToSnakeCase("isActive"), person.getPersonStatus() ? 1 : 0);
        inParams.put(CommonService.convertToSnakeCase("streetNo"), person.getAddress().getStreetNo());
        inParams.put("city", person.getAddress().getCity());
        inParams.put("state", person.getAddress().getState());
        inParams.put(CommonService.convertToSnakeCase("zipCode"), person.getAddress().getZipCode());
        inParams.put("country", person.getAddress().getCountry());
        inParams.put(CommonService.convertToSnakeCase("createdAt"), new Timestamp(System.currentTimeMillis()));
        
        Map<String, Object> resultSet = simpleJdbcCall.execute(inParams);
        return resultSet;
    }

    @Override
    public Map<String,Object> loginUser(LoginParam loginParam) {
        SimpleJdbcCall simpleJdbcCall = this.connectionManager.getConnection().withProcedureName("login_user").declareParameters(new SqlParameter("emailId",Types.VARCHAR),new SqlParameter("password",Types.VARCHAR));
        Map<String,Object> inParam = new HashMap<>();
        inParam.put(CommonService.convertToSnakeCase("emailId"), loginParam.getEmailId());
        inParam.put("password", loginParam.getPassword());

        Map<String,Object> resultSet = simpleJdbcCall.execute(inParam);
        return resultSet;
    }

    @Override
    public List<Map<String,Object>> getAllRoles() {
        JdbcTemplate jdbcTemplate = this.connectionManager.getConnection().getJdbcTemplate();
        String query = this.sqlQueryLoader.getSqlQuery("fetch_all_roles");
        Objects.requireNonNull(query, "SQL query 'fetch_all_roles' not found by SqlQueryLoader");

        List<Map<String,Object>> resultSet = new ArrayList<>();
        resultSet = jdbcTemplate.queryForList(query);
        return resultSet;
    }

    @Override
    public void checkAuthorization(Person person, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAuthorization'");
    }

    @Override
    public void forgetPassword(Person person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forgetPassword'");
    }

    @Override
    public void resetPassword(Person person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }

    @Override
    public void generateToken(Person person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    @Override
    public Person updateProfile(Person person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProfile'");
    }

    
}
