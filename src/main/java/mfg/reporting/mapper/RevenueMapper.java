package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.dto.RevenueChartDTO;
import mfg.reporting.dto.RevenueDTO;
import mfg.reporting.dto.RevenueDetailsLotDTO;
import mfg.reporting.pojo.RevenueDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RevenueMapper {

    @UseDataSource("pdwmfg")
    List<RevenueDTO> getRevenueDetails(@Param("data") Map<String,Object> data);

    @UseDataSource("pdwmfg")
    List<RevenueChartDTO> getRevenueChart();


    @UseDataSource("pdwmfg")
    List<RevenueDetailsLotDTO> getWtHoldWipDetailByProdg1(@Param("prodg1") String prodg1,@Param("line")String line);

    @UseDataSource("pdwmfg")
    List<RevenueDetailsLotDTO> getRevenueDiffLotDetail(@Param("prodg1") String prodg1,@Param("line")String line);

    @UseDataSource("pdwmfg")
    void insertRevenueData(List<RevenueDataVO> list);


    @UseDataSource("pdwmfg")
    void deleteRevenueData();

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getRevenueDateList();

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getCustomerList();

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getTechList();
}
