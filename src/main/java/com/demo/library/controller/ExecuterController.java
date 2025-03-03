package com.demo.library.controller;

import com.demo.library.config.SqlFileExecutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/execute")
@RestController
@Api(value = "Execute SQL file", description = "APIs to execute SQL file to add sample data in DB")
public class ExecuterController {

    @Autowired
    private SqlFileExecutorService sqlFileExecutorService;

    @ApiOperation(value = "Execute SQL File", notes = "API to execute SQL file and imports sample data into the database")
    @PostMapping("/sqlFile")
    public String executeSqlFile(@RequestParam(name = "filePath", defaultValue = "./sampleData.sql", required = false) String filePath) {
        return sqlFileExecutorService.executeSqlFile(filePath);
    }

}
