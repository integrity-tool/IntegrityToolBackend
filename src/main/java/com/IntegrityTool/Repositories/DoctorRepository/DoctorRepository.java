package com.IntegrityTool.Repositories.DoctorRepository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.IntegrityTool.Repositories.Interface.IDoctor;
import com.IntegrityTool.service.util.ConnectionManager;

@Repository
public class DoctorRepository implements IDoctor {
    private final ConnectionManager connectionManager;

    @Autowired
    public DoctorRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Map<String, Object> storeFileData(MultipartFile multipartFile) {
        // SimpleJdbcCall simpleJdbcCall = this.connectionManager.getConnection()
        //         .withProcedureName("File_")
        //         .declareParameters(
        //                 new SqlParameter("personId", Types.VARCHAR),
        //                 new SqlParameter("firstName", Types.VARCHAR),
        //                 new SqlParameter("lastName", Types.VARCHAR),
        //                 new SqlParameter("email", Types.VARCHAR),
        //                 new SqlParameter("gender", Types.VARCHAR),
        //                 new SqlParameter("dateOfBirth", Types.DATE),
        //                 new SqlParameter("password", Types.VARCHAR),
        //                 new SqlParameter("isActive", Types.BIT),
        //                 new SqlParameter("streetNo", Types.VARCHAR),
        //                 new SqlParameter("city", Types.VARCHAR),
        //                 new SqlParameter("state", Types.VARCHAR),
        //                 new SqlParameter("zipcode", Types.VARCHAR),
        //                 new SqlParameter("country", Types.VARCHAR),
        //                 new SqlParameter("createdAt", Types.TIMESTAMP),
        //                 new SqlOutParameter("userid", Types.VARCHAR));

        //                  Map<String, Object> inParams = new HashMap<>();
        // inParams.put(CommonService.convertToSnakeCase("personId"), person.getPersonId().toString());
        // inParams.put(CommonService.convertToSnakeCase("firstName"), person.getFirstName());
        // inParams.put(CommonService.convertToSnakeCase("lastName"), person.getLastName());
        // inParams.put("email", person.getEmail());
        // inParams.put("gender", person.getGender());
        // inParams.put(CommonService.convertToSnakeCase("dateOfBirth"), person.getDateOfBirth());
        // inParams.put("password", person.getPassword());
        // inParams.put(CommonService.convertToSnakeCase("isActive"), person.getPersonStatus() ? 1 : 0);
        // inParams.put(CommonService.convertToSnakeCase("streetNo"), person.getAddress().getStreetNo());
        // inParams.put("city", person.getAddress().getCity());
        // inParams.put("state", person.getAddress().getState());
        // inParams.put(CommonService.convertToSnakeCase("zipCode"), person.getAddress().getZipCode());
        // inParams.put("country", person.getAddress().getCountry());
        // inParams.put(CommonService.convertToSnakeCase("createdAt"), new Timestamp(System.currentTimeMillis()));

        // Map<String, Object> resultSet = simpleJdbcCall.execute(inParams);
        return null;
    }

    @Override
    public Map<String, Object> parseEDI(String ediContent) {
        
        return null;
    }
}
