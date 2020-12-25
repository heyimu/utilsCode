package com.utils.excle.service.impl;

import com.utils.excle.entity.ExcelModel;
import com.utils.excle.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
        StringBuilder replace = new StringBuilder();
        builder.append("CREATE TABLE \"" + tableName + "\"(\n");
        List<String> pk = new ArrayList<>();
        for (ExcelModel excelModel : excelModels) {
            if (StringUtils.isEmpty(excelModel.getLength())) {
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
            if (!StringUtils.isEmpty(excelModel.getPrimaryKey())) {
                pk.add(excelModel.getName());
            }
            builder.append(",\n");


            annotation.append("COMMENT ON COLUMN \"" + tableName + "\"." + "\"" + excelModel.getName() + "\" IS '" +
                    (StringUtils.isEmpty(excelModel.getCnName()) ? " " : excelModel.getCnName())
                    + (!StringUtils.isEmpty(excelModel.getRemarks()) ? " " + excelModel.getRemarks() + "';" : "';"));
            annotation.append("\n");
        }

        annotation.append("COMMENT ON TABLE \"" + tableName + "\" IS '" + tableName + "';");

        annotation.append("\n");
        if (pk.size() > 0) {
            log.info("ok=============" + pk.size());
            if (pk.size() > 2) {
                builder.append("constraint " + pk.get(0) + "_JOINT_PK primary key(");
                for (int i = 0; i < pk.size() - 1; i++) {
                    builder.append(pk.get(i) + ", ");
                }
                builder.append(pk.get(pk.size()-1) + ")\n");
            } else {
                builder.append("constraint " + pk.get(0) + "_PK primary key(" + pk.get(0) + ")\n");
            }
            builder.append(")\n");
            replace = builder;
        } else {
            builder.append(")\n");
            int x = builder.lastIndexOf(",");
            replace = builder.replace(x, builder.length(), "\n )");
        }

        System.out.println("建表语句如下:  " + "\n" + replace);
        System.out.println("注释语句如下:  " + "\n" + annotation);
        return "建表语句如下:  \n" + replace + "\n \n \n" + "注释语句如下:  \n" + annotation;
    }


}
