package com.utils.excle.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本类为 需要生成的excel 的 模板
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelModel extends BaseRowModel {
    /**
     * 第一列的数据 (列名)
     */
    @ExcelProperty(index = 1)
    private String name;

    /**
     * 第二列的数据 (中文名)
     */
    @ExcelProperty(index = 2)
    private String cnName;

    /**
     * 第二列的数据 (类型)
     */
    @ExcelProperty(index = 3)
    private String type;

    /**
     * 第四列的数据 (长度)
     */
    @ExcelProperty(index = 4)
    private String length;

    /**
     * 第五列的数据 (小数)
     */
    @ExcelProperty(index = 5)
    private String decimal;

    /**
     * 第五列的数据 (主键)
     */
    @ExcelProperty(index = 6)
    private String primaryKey;

    /**
     * 第五列的数据 (备注)
     */
    @ExcelProperty(index = 7)
    private String remarks;
}
