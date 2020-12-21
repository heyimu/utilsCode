package com.utils.translate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultModel {
    private String from;
    private String to;
    private List<Trans_result> trans_result;
}
