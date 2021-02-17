package utils;

import domain.Access;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bahsk
 * @createTime 2021-02-14 1:04
 * @description
 */
public class MockData {

    public static final String url = "http://47.114.63.55:9527/cat-web/upload";

    @Test
    public void testUpload() throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        for(int i=1; i<=100; i++) {
            Thread.sleep(100);
            Access access = new Access();
            access.setId(i);
            access.setName("name" + i);
            access.setTime(format.format(new Date()));
            UploadUtils.upload(url, access.toString()); // Base64
        }
    }
}
