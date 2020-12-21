package com.utils.excle.service;


import com.utils.excle.entity.ExcelModel;

import java.util.List;

public interface ExcelService {

    String dataDealWith(List<ExcelModel> excelModels, String tableName);
}
