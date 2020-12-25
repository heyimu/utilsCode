package com.utils.excle.resource;

import com.utils.excle.ExcelUtils;
import com.utils.excle.entity.ExcelModel;
import com.utils.excle.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
public class GenerateResource {

    @Autowired
    private ExcelService service;

    @PostMapping(path = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String generate(@RequestParam("file") MultipartFile file,  @RequestParam("tableName")String tableName) {
        if (file.isEmpty()) {
            log.error("请上传文件");
        }
        List<ExcelModel> excelModels = null;
        try (InputStream inputStream = file.getInputStream()) {
            excelModels = ExcelUtils.redExcel(null, 2, 1, inputStream, ExcelModel.class);

        } catch (IOException e) {
            log.error("文件解析失败, 原因:{}", e.getMessage());
        }
        if (excelModels == null || excelModels.size() == 0) {
            log.error("文件解析失败或文件内容为空");
        }
        return  service.dataDealWith(excelModels, tableName);


    }
}
