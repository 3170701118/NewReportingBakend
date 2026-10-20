package mfg.reporting.CommonUtil;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.pojo.ClickRate;
import mfg.reporting.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gszhang
 */
@Slf4j
@Component
public class ClickLogReader {
    @Autowired
    public CommonService commonService;

    @Resource
    public CustomParam customParam;

    public void getClickLog() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyMinutesAgo = now.minusMinutes(30);

        // 获取昨天的日期
        LocalDateTime yesterday = now.minusDays(1).toLocalDate().atStartOfDay();

        // 判断是否需要读取昨天的日志
        if (thirtyMinutesAgo.toLocalDate().isEqual(yesterday.toLocalDate())) {
            readLogFile("yesterday");
        } else {
            readLogFile("today");
        }
    }

    private void readLogFile(String day) {
        String logFilePath;
        if ("yesterday".equals(day)) {
            // 假设昨天的日志文件名为 "app-YYYY-MM-DD.log"
            String yesterdayDate = LocalDateTime.now().minusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            logFilePath = customParam.getClickLogLocation() + "output-" + yesterdayDate + ".log";
        } else {
            // 日志文件前缀
            logFilePath = customParam.getClickLogLocation() + "output.log";
        }

        try {
            List<ClickRate> clickLogList = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(logFilePath));
            for (String line : lines) {
                if(!line.isEmpty()){
                    //转成实体类
                    ClickRate clickRate = JSON.parseObject(line, ClickRate.class);
                    String targetTimeStr = clickRate.getUpdateTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    // 解析目标时间
                    LocalDateTime targetTime = LocalDateTime.parse(targetTimeStr, formatter);

                    // 获取当前时间
                    LocalDateTime now = LocalDateTime.now();

                    // 获取当前小时的整点
                    boolean isInRange = isIsInRange(now, targetTime);

                    if (isInRange) {
                        clickLogList.add(clickRate);
                    }
                }
            }

            if(!clickLogList.isEmpty()){
                commonService.insertClickRateLogList(clickLogList);
            }
        } catch (IOException e) {
            log.error("error:{}",e.getMessage());
        }
    }

    private boolean isIsInRange(LocalDateTime now, LocalDateTime targetTime) {
        LocalDateTime currentHourStart = now.withMinute(0).withSecond(0).withNano(0);
        // 获取当前小时的 30 分钟
        LocalDateTime currentHalfHourEnd = currentHourStart.plusMinutes(30);

        // 获取上一小时的整点
        LocalDateTime previousHourStart = currentHourStart.minusHours(1);
        // 获取上一小时的 30 分钟
        LocalDateTime previousHalfHourEnd = previousHourStart.plusMinutes(30);

        // 判断当前时间是否超过当前小时的一半
        boolean isPastHalfHour = now.getMinute() >= 30;

        // 根据判断结果确定时间范围
        LocalDateTime rangeStart;
        LocalDateTime rangeEnd;

        if (isPastHalfHour) {
            // 当前时间超过当前小时的一半
            rangeStart = currentHourStart;
            rangeEnd = currentHalfHourEnd;
        } else {
            // 当前时间未超过当前小时的一半
            rangeStart = previousHalfHourEnd;
            rangeEnd = currentHourStart;
        }

        return targetTime.isAfter(rangeStart) && targetTime.isBefore(rangeEnd);
    }

}
