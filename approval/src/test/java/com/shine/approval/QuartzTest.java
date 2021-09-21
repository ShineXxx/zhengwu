package com.shine.approval;

import com.shine.approval.quartz.QuartzJobManagement;
import com.shine.approval.quartz.dynamic.CallBackNoticeJob;
import com.shine.approval.quartz.util.DateUtils;
import com.shine.approval.quartz.util.NoticeClockUtil;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class QuartzTest {

    @Resource
    QuartzJobManagement quartzJobManagement;

    @Test
    void contextLoads() throws IOException {
        Integer noticeTimes = 0;
        Map<String, String> dataMap = new HashMap<>(16);
        dataMap.put("recordDetailId", "1");
        int nextClockGap = NoticeClockUtil.getNextClockGap(noticeTimes);
        if (nextClockGap == 0) {
            // 通知次数达到上限，通知失败
            return;
        }
        Date activeTime = DateTime.now().plusSeconds(5).toDate();
        quartzJobManagement.addJob("1", CallBackNoticeJob.class, DateUtils.getCron(activeTime), dataMap);
        System.in.read();
    }

}
