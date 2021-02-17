package utils;

import com.imooc.bigdata.gen.LogGenerator;

/**
 * @author bahsk
 * @createTime 2021-02-14 15:39
 * @description 测试慕课网给的项目接口
 */
public class LogClient {



    public static void main(String[] args) throws Exception {
        //上传的地址
        String url = "http://47.114.63.55:9527/cat-web/upload";
        //
        String code="3191F58BDD203AD0";
        LogGenerator.generator(url,code);
    }
}
