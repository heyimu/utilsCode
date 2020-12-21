package com.utils.excle.service.impl;

import com.utils.excle.entity.ExcelModel;
import com.utils.excle.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {
    @Override
    public String dataDealWith(List<ExcelModel> excelModels, String tableName) {
        if (CollectionUtils.isEmpty(excelModels)) {
            log.error("解析文件为空");
        }
        StringBuilder builder = new StringBuilder();
        StringBuilder annotation = new StringBuilder();
        builder.append("CREATE TABLE \"" + tableName + "\"(\n");
        for (ExcelModel excelModel : excelModels) {
            if (StringUtils.isEmpty(excelModel.getLength())){
                excelModel.setLength("1");
            }
            builder.append("  \"" + excelModel.getName() + "\"");
            builder.append(" " +
                    (excelModel.getType().equals("VARCHAR") ? "VARCHAR2" : excelModel.getType())
                    + " ");
            builder.append("(" + excelModel.getLength());
            if (excelModel.getDecimal() == null) {
                builder.append(")");
            } else {
                builder.append("," + excelModel.getDecimal() + ")");
            }
            if (excelModel.getPrimaryKey() != null) {
                builder.append(" constraint pk_" + excelModel.getName() + " primary key");
                builder.append(",\n");
            } else {
                builder.append(",\n");
            }

            annotation.append("COMMENT ON COLUMN \"" + tableName + "\"." + "\"" + excelModel.getName() + "\" IS '" +
                    (StringUtils.isEmpty(excelModel.getCnName()) ? " " : excelModel.getCnName()) +
                    " " + (excelModel.getRemarks() != null ? excelModel.getRemarks() + "';" : "';"));
            annotation.append("\n");
        }
        annotation.append("COMMENT ON TABLE \""+ tableName + "\" IS '"+ tableName +"';");

        annotation.append("\n");
        builder.append(")\n");
        int x = builder.lastIndexOf(",");
        StringBuilder replace = builder.replace(x, builder.length(), "\n )");
        System.out.println("建表语句如下:  " + "\n" + replace);
        System.out.println("注释语句如下:  " + "\n" + annotation);
        return "建表语句如下:  \n" + replace + "\n \n \n" + "注释语句如下:  \n" + annotation;
    }


}
