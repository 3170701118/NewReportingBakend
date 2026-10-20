package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.mapper.PullInWebMapper;
import mfg.reporting.pojo.PullInWebVO;
import mfg.reporting.pojo.PullInwebPO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PullInWebService {

    @Resource
    private PullInWebMapper mapper;

    public List<PullInwebPO> getPullInWebData(String shiftDate,String functionName) {
        return mapper.getPullInWebData(shiftDate,functionName);
    }

    //需要回滚机制
    @Transactional(rollbackFor = Exception.class)
    public void uploadPullInWebExcel(MultipartFile file, String username, String source) throws IOException {
        log.info("开始上传 PullIn Web Excel 数据，用户：{}，来源：{}", username, source);

        try {
            List<PullInWebVO> list = EasyExcel.read(file.getInputStream())
                    .head(PullInWebVO.class)
                    .sheet()
                    .doReadSync();

            if (list == null || list.isEmpty()) {
                throw new IOException("上传文件为空");
            }

            log.info("读取到 {} 条数据", list.size());

            for (PullInWebVO item : list) {
                String requestDate = item.getRequestDate();
                if (requestDate != null && !requestDate.matches("\\d{8}")) {
                    throw new IOException("Request Date 格式错误，应为YYYYMMDD格式");
                }

                if (!isValidYYYYMMDD(requestDate)) {
                    throw new IOException("Request Date 格式错误，应为YYYYMMDD格式");
                }
            }

            List<Map<String,Object>> testData = mapper.testuploadPullInWebData(list, username, source);
            if(testData.size() > 0){
                String errorMsg = testData.stream()
                        .map(item -> {
                            Object question = item.get("QUESTION");
                            return question != null ? question.toString() : "";
                        })
                        .filter(msg -> !msg.isEmpty())
                        .collect(Collectors.joining(","));
                throw new IOException("数据校验失败，错误信息：" + errorMsg);
            }

            log.info("数据校验通过，开始分批插入数据库");

            Integer step = 500;
            int totalInserted = 0;
            for (int i = 0; i < list.size(); i += step) {
                int endIndex = Math.min(i + step, list.size());
                List<PullInWebVO> subList = list.subList(i, endIndex);

                mapper.insertPullInWebUpload(subList, username, source);
                mapper.insertpullInFlowCTInfo(subList, username, source);

                totalInserted += subList.size();
                log.info("已插入 {}/{} 条数据", totalInserted, list.size());
            }

            mapper.deleteTableDetailsSummary(source);

            mapper.refreshTabDetailsSummary(source);

            log.info("PullIn Web Excel 数据上传成功，共处理 {} 条数据", list.size());

        } catch (IOException e) {
            log.error("PullIn Web Excel 数据上传失败：{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("PullIn Web Excel 数据上传发生未知错误：{}", e.getMessage(), e);
            throw new IOException("数据上传失败：" + e.getMessage(), e);
        }
    }

    /**
     * 使用Java 8 DateTime API验证YYYYMMDD格式
     */
    public static boolean isValidYYYYMMDD(String dateStr) {
        if (dateStr == null || dateStr.length() != 8) {
            return false;
        }

        try {
            LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    /**
     * 删除 PullIn Lot（带事务回滚）
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePullInLot(String lotList, String functionName) {
        log.info("开始删除 PullIn Lot，lotList：{}，functionName：{}", lotList, functionName);
        try {
            if (lotList == null || lotList.trim().isEmpty()) {
                log.warn("lotIdsStr 为空，跳过删除操作");
                return;
            }

            // 将逗号分隔的字符串转换为 List
            List<String> lotIdList = Arrays.stream(lotList.split(","))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .collect(Collectors.toList());

            if (lotIdList.isEmpty()) {
                log.warn("解析后的 lotIdList 为空，跳过删除操作");
                return;
            }

            log.info("开始删除 Lot 数据，functionName: {}, lot数量: {}", functionName, lotIdList.size());

            // 分批删除，每批 500 条
            int batchSize = 500;
            int totalDeleted = 0;

            for (int i = 0; i < lotIdList.size(); i += batchSize) {
                int endIndex = Math.min(i + batchSize, lotIdList.size());
                List<String> batch = lotIdList.subList(i, endIndex);

                mapper.deletePullInLot(batch, functionName);
                mapper.deletePullInFlowCTInfo(batch, functionName);

                totalDeleted += batch.size();
                log.info("已删除批次: {}/{}, 本批数量: {}",
                        (i / batchSize + 1),
                        (lotIdList.size() + batchSize - 1) / batchSize,
                        batch.size());
            }

            log.info("删除完成，共处理 {} 条记录", totalDeleted);
        } catch (Exception e) {
            log.error("PullIn Lot 删除失败：{}", e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 设置 Top Lot 信息（带事务回滚）
     */
    @Transactional(rollbackFor = Exception.class)
    public void setTopLotInfo(String lotList, String username, String functionName) {
        log.info("开始设置 Top Lot 信息，lotList：{}，用户：{}", lotList, username);
        try {
            mapper.setTopLotInfo(lotList, username, functionName);
            log.info("Top Lot 信息设置成功");
        } catch (Exception e) {
            log.error("Top Lot 信息设置失败：{}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Map<String, Object>> getDelayLotSummary(String fab) {
        return mapper.getDelayLotSummary(fab);
    }

    public List<Map<String, Object>> getActivetabNamesByFab(String fab) {
        return mapper.getActivetabNamesByFab(fab);
    }

    public List<Map<String, Object>> getAllTabNameByFab(String fab) {
        return mapper.getAllTabNameByFab(fab);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateActiveTabInfo(String tabName, String fab, String active, String rank, String label, String updateUser) {
        log.info("开始更新 Active Tab 信息，tabName：{}，fab：{}", tabName, fab);
        try {
            mapper.updateActiveTabInfo(tabName, fab, active, rank, label, updateUser);
            log.info("Active Tab 信息更新成功");
        } catch (Exception e) {
            log.error("Active Tab 信息更新失败：{}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Map<String, Object>> getDelayLotDetails(String fab ,String date,String module,String functionName){
        return mapper.getDelayLotDetails(fab,date,module,functionName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDelayLotComment(Map<String,Object> data){
        log.info("开始更新 Delay Lot 评论");
        try {
            mapper.updateDelayLotComment(data);
            log.info("Delay Lot 评论更新成功");
        } catch (Exception e) {
            log.error("Delay Lot 评论更新失败：{}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Map<String,Object>> getPullinWebMonitorByEQPG( String functionName){
        return mapper.getPullinWebMonitorByEQPG(functionName);
    }


}
