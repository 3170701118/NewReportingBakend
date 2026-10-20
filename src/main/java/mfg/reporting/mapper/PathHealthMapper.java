package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PathHealthMapper {
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getEqpA12(@Param("module")String module, @Param("eqpg")String eqpg);

    @UseDataSource("a3pdw")
    List<Map<String,Object>> getEqpA3(@Param("module")String module, @Param("eqpg")String eqpg);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getEqpgA12(@Param("module")String module);

    @UseDataSource("a3pdw")
    List<Map<String,Object>> getEqpgA3(@Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getEqpProdg(@Param("fab")String fab, @Param("module")String module, @Param("eqpg")String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getTech();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSuffix(@Param("fab")String fab, @Param("module")String module, @Param("eqpg")String eqpg, @Param("prodg")String prodg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOpeno(@Param("fab")String fab, @Param("module")String module, @Param("eqpg")String eqpg, @Param("prodg")String prodg, @Param("prodgspecid")String prodgspecid);


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPathDetail(@Param("fab")String fab, @Param("module")String module, @Param("eqpg")String eqpg, @Param("prodg")String prodg, @Param("prodgspecid")String prodgspecid
            ,@Param("openo")String openo,@Param("recipe")String recipe,@Param("array") String[] array,@Param("tb")String tb,@Param("tech")String tech
    );
}
