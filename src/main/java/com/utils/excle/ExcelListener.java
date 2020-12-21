package com.utils.excle;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

    private List<T> rows = new ArrayList<>();
    private Class<T> clazz;
    private List<String> header = new ArrayList<>();


    public List<String> getHeader() {
        return header;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        rows.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("read{},rows.isze()",rows.size());
    }

    public List<T> getRows(){
        return rows;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context)throws Exception{
        log.error("解析失败,继续解析下一行;{}",exception.getMessage());
        if (exception instanceof ExcelDataConvertException){
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第:{}行,第{}列数据解析异常",excelDataConvertException.getRowIndex()+1, excelDataConvertException.getColumnIndex()+1);
        }else if (exception instanceof ExcelAnalysisException){
            super.onException(exception, context);
        }
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        if (clazz!=null){
            try{
                Map<Integer, String> indexNameMap = getIndexNameMap(clazz);
                Set<Integer> keySet = headMap.keySet();
                if (headMap.size() > indexNameMap.size()){
                    throw new ExcelAnalysisException("解析Excel出错, 请传入正确格式的excel");
                }
                for (Integer key : keySet) {
                    if (StringUtils.isEmpty(headMap.get(key))){
                        throw new ExcelAnalysisException("解析Excel出错, 请传入正确格式的excel");
                    }
                    if (!headMap.get(key).equals(indexNameMap.get(key))){
                        throw new ExcelAnalysisException("解析Excel出错, 请传入正确格式的excel");
                    }
                }
            }catch (NoSuchFieldException e){
                log.error("解析表头异常,{}",e.getMessage());
            }
        }
    }

    private Map<Integer, String> getIndexNameMap(Class<T> clazz) throws NoSuchFieldException {
        Map<Integer, String> result = new HashMap<>();
        Field field;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            field = clazz.getDeclaredField(fields[i].getName());
            field.setAccessible(true);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null){
                int index = excelProperty.index();
                String[] values = excelProperty.value();
                StringBuilder value = new StringBuilder();
                for (String s : values) {
                    value.append(s);
                }
                result.put(index, value.toString());
            }
        }
        return null;
    }
}
