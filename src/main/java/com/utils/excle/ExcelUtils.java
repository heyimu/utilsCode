package com.utils.excle;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.utils.excle.entity.ExcelModel;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.util.List;

@Slf4j
public class ExcelUtils {

    public static <T extends BaseRowModel> List<T> redExcel(String sheetName, int sheetNo, int startReadNo,
                                                            InputStream inputStream, final Class<? extends BaseRowModel> excelModelClass) {
        List<T> rowList = null;
        BufferedInputStream bis = null;

        try {
            bis = new BufferedInputStream(inputStream);
            ExcelListener listener = new ExcelListener();
            ExcelReader excelReader = EasyExcelFactory.getReader(bis, listener);
            if (sheetName != null) {
                excelReader.getSheets().stream().filter(st -> sheetName.equals(st.getSheetName()))
                        .forEach(sheet -> {
                            excelReader.read(new Sheet(sheet.getSheetNo(), startReadNo, excelModelClass));
                        });
            } else {
                excelReader.read(new Sheet(sheetNo, startReadNo, excelModelClass));
            }
            System.out.println(listener.getHeader());
            rowList = listener.getRows();
            excelReader.finish();
        } catch (Exception e) {
            log.error("Excel解析:{}", e.getMessage());
            return null;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("IO异常,{},IOException:{}", e.getMessage(), e);
                }
            }
        }

        return rowList;
    }


    public static List<String> readHead(){
        return null;
    }

}
