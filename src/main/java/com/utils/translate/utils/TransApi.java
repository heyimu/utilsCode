package com.utils.translate.utils;

import com.alibaba.fastjson.JSONObject;
import com.utils.translate.entity.ResultModel;
import com.utils.translate.entity.Trans_result;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    public List<String> getList(String query, String from, String to) {
        List<String> resultList = new ArrayList<>();

        String text = getTransResult(query, from,to);
        if (StringUtils.isEmpty(text)){
            return resultList;
        }

        JSONObject jsonObject = JSONObject.parseObject(text);
        ResultModel resultModel = jsonObject.toJavaObject(jsonObject, ResultModel.class);
        for (Trans_result result : resultModel.getTrans_result()) {
            resultList.add(result.getDst());
        }

        return resultList;
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
