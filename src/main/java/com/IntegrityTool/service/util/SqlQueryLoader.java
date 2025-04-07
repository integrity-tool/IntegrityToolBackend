package com.IntegrityTool.service.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;

import org.springframework.core.io.Resource;

@Service
public class SqlQueryLoader implements ResourceLoaderAware {

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;
    private Map<String, String> sqlQueries = new HashMap<>();

    @Override
    public void setResourceLoader(@NotNull ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }

    private String asString(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @PostConstruct
    public void loadSqlFiles() throws IOException {
        Resource[] resources = resourcePatternResolver.getResources("classpath:Sql/*.sql");
        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            if (fileName != null)
                sqlQueries.put(fileName.replace(".sql", ""), asString(resource));
        }
    }

    public String getSqlQuery(String queryName) {
        return sqlQueries.get(queryName);
    }
}
