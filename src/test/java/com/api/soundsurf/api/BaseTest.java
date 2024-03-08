package com.api.soundsurf.api;

import com.api.soundsurf.SoundSurfApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;

@SqlGroup({
        @Sql(config = @SqlConfig(dataSource = "dataSource"),
                value = {"classpath:/db/drop_tables.sql", "classpath:/db/app_migration.sql"},
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.config.location=classpath:/application.yaml")
@ContextConfiguration(classes = SoundSurfApplication.class)
@AutoConfigureMockMvc
public class BaseTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    public static Collection<DynamicNode> group(DynamicNode... dynamicNodes) {
        return List.of(dynamicNodes);
    }

    public static DynamicNode group(final String displayName, DynamicNode... dynamicNodes) {
        return DynamicContainer.dynamicContainer(displayName, List.of(dynamicNodes));
    }

    public static DynamicNode group(final String displayName, Collection<DynamicNode> dynamicNodes) {
        return DynamicContainer.dynamicContainer(displayName, dynamicNodes);
    }

    public static DynamicNode single(final String displayName, final Executable executable) {
        return DynamicTest.dynamicTest(displayName, executable);
    }
}