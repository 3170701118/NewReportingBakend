package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import mfg.reporting.dto.EtchMaintainInputPlanDTO;
import mfg.reporting.dto.EtchMaintainStdTcDTO;
import mfg.reporting.mapper.EtchUploadMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EtchUploadService {

    //版本管理 上传etch标准TC
    @Resource
    public EtchUploadMapper mapper;


    public void insertEtchStdTc(MultipartFile file) throws Exception {

        //TODO 解析Excel文件

        List<EtchMaintainStdTcDTO> list = EasyExcel.read(file.getInputStream())
                .head(EtchMaintainStdTcDTO.class)
                .sheet()
                .doReadSync();


        /*
         * TODO 验证 Excel数据
         * 1. module 列必须大写的ETCH
         * 2. stdTc 列可以为空，但是maintain值的单元格必须大于0
         * 3. 去除空格
        * */
        for(EtchMaintainStdTcDTO dto : list){
            if(!"ETCH".equalsIgnoreCase(dto.getModule())){
                throw new Exception("Excel数据错误，MODULE列必须为大写的ETCH");
            }
            if(dto.getStdTc() != null && dto.getStdTc() <= 0){
                throw new Exception("Excel数据错误，STD_TC列必须大于0");
            }
            //去除空格
            dto.setProdg1(dto.getProdg1().trim());
            dto.setOpeNo(dto.getOpeNo().trim());
            dto.setEqpG(dto.getEqpG().trim());
            dto.setModule(dto.getModule().trim());
        }

        //TODO 删除已有数据
        mapper.deleteEtchStdTcAll();


        //TODO 分批次插入Excel数据到数据库
        int batchSize = 2000; // 每批次插入的记录数
        for (int i = 0; i < list.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, list.size());
            List<EtchMaintainStdTcDTO> batchList = list.subList(i, endIndex);
            mapper.insertEtchStdTcBatch(batchList);
        }

    }

    //insertInputPlanDailyNewForETCH
    public void insertInputPlanDailyNewForETCH(MultipartFile file) throws Exception{
        //TODO 解析Excel文件
        List<EtchMaintainInputPlanDTO> list = EasyExcel.read(file.getInputStream())
                .head(EtchMaintainInputPlanDTO.class)
                .sheet()
                .doReadSync();

        /*
        *数据处理和验证
        *1. 日期格式验证 shiftDate 是否为YYYYMMDD格式
        *2. inputPlanA12/inputPlanA3 为数字
        *3. 去除空格
        *
        *
         */
        for(EtchMaintainInputPlanDTO dto : list){
            if(!dto.getShiftDate().matches("\\d{8}")){
                throw new Exception("Excel数据错误，SHIFT_DATE列必须为YYYYMMDD格式");
            }
            //去除空格
            dto.setProdg1(dto.getProdg1().trim());
        }

        //删除已有数据
        mapper.deleteInputPlanDailyNewForETCHAll();

        //数据量较少，直接上传
        mapper.insertInputPlanDailyNewForETCHBatch(list);
    }
}
