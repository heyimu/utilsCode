package com.utils.translate;

import com.utils.translate.utils.TransApi;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TranslateDemo {
    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    @Value("${TRANS.APP_ID}")
    private String appId = "202008";
    @Value("${TRANS.SECURITY_KEY}")
    private String securityKey = "qV4ymsFdjUl";

    public void transApi() {

        TransApi api = new TransApi(appId, securityKey);

        String query = "姓名\n" +
                "年龄\n" +
                "地区\n" +
                "性别\n" +
                "身高\n" +
                "家庭住址\n" +
                "手机号\n" +
                "单位地址\n" +
                "单位电话\n" +
                "联系人一\n" +
                "联系人一地址\n" +
                "联系人二\n" +
                "联系人二地址\n" +
                "联系人一手机号\n" +
                "联系二手机号\n" +
                "联系人一金额\n";
//        String query = "《出塞》\n" +
//                "[唐] \n" +
//                "王昌龄\n" +
//                "秦时明月汉时关，万里长征人未还。\n" +
//                "但使龙城飞将在，不教胡马度阴山。";
//        System.out.println(api.getTransResult(query, "auto", "en"));

        List<String> transResult = api.getList(query, "zh", "en");
//        String transResult1 = api.getTransResult(query, "zh", "en");
//        System.out.println(transResult1);
        for (String s : transResult) {
            boolean contains = s.contains(" ");
            int i = s.indexOf(" ");
            if (i > 0) {
                s = s.replace(" ", "_");
                System.out.println(s);
            } else {
                System.out.println(s);
            }
        }

    }


    public static void main(String[] args) {
        String s = "s d";
        System.out.println(s.indexOf(" 1"));
//        TranslateDemo translateDemo = new TranslateDemo();
//        translateDemo.transApi();
    }
}
