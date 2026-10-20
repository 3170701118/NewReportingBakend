package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.dto.RevenueChartDTO;
import mfg.reporting.dto.RevenueDTO;
import mfg.reporting.dto.RevenueDetailsLotDTO;
import mfg.reporting.mapper.RevenueMapper;
import mfg.reporting.pojo.RevenueDataVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class RevenueService {

    @Resource
    public RevenueMapper revenueMapper;

    @Resource
    public JwtUtils jwtUtils;

    public List<RevenueDTO> getRevenueDetails(Map<String,Object> data){
        try {
            return revenueMapper.getRevenueDetails(data);
        } catch (Exception e) {
           throw new ServiceException(e.getMessage());
        }
    }


    public List<RevenueChartDTO> getRevenueChart(){
        try {
            return revenueMapper.getRevenueChart();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public List<RevenueDetailsLotDTO> getWtHoldWipDetailByProdg1(String prodg1,String line){
        try {
            return revenueMapper.getWtHoldWipDetailByProdg1(prodg1,line);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<RevenueDetailsLotDTO> getRevenueDiffLotDetail(String prodg1,String line){
        try {
            return revenueMapper.getRevenueDiffLotDetail(prodg1,line);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public void UploadRevenueTemplate(MultipartFile file){
        try {
            // 上传文件
            List<RevenueDataVO> list = EasyExcel.read(file.getInputStream())
                    .head(RevenueDataVO.class)
                    .sheet()
                    .doReadSync();
            String updateUser = jwtUtils.getUsernameFromToken();
            for (RevenueDataVO revenueDataVO : list) {
                //验证所有数据不能为空
                if(revenueDataVO.getRevenueDate() == null || revenueDataVO.getProdg1() == null || revenueDataVO.getReceiveMore() == null || revenueDataVO.getLine() == null || revenueDataVO.getRevenueTarget() == null){
                    throw new ServiceException("数据不能为空");
                }

                //验证数据 line 只能为A12/A3
                if(!"A12".equals(revenueDataVO.getLine()) && !"A3".equals(revenueDataVO.getLine())){
                    throw new ServiceException("line 只能为A12/A3");
                }
                //验证数据 receiveMore 只能为 是/否
                if(!"是".equals(revenueDataVO.getReceiveMore()) && !"否".equals(revenueDataVO.getReceiveMore())){
                    throw new ServiceException("receiveMore 只能为 是/否");
                }
                //验证数据 receiveDate 只能为 日期格式格式的字符串 YYYY/MM/DD 或者 YYYY/M/DD,并且只能是当月的日期

                //验证数据 receiveDate 只能是当月的日期
                //获取当前日期的年月
                String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
                if(!revenueDataVO.getRevenueDate().startsWith(currentDate)){
                    throw new ServiceException("receiveDate 只能是当月的日期");
                }
                //验证数据 receiveTarget 只能是 > 0的数字
                if(revenueDataVO.getRevenueTarget() <= 0){
                    throw new ServiceException("receiveTarget 只能是 > 0的数字");
                }

                revenueDataVO.setUpdateUser(updateUser);

            }
            //删除数据
            revenueMapper.deleteRevenueData();

            //插入数据
            revenueMapper.insertRevenueData(list);

        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }



    public Map<String,List<CommonSelectDTO>> getSelectionInfo(){
        try {
            return Map.of("dateList",revenueMapper.getRevenueDateList(),"customerList",revenueMapper.getCustomerList(),"techList",revenueMapper.getTechList());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
