package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.MoveTargetPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoveTargetMapper {
    @UseDataSource("a3mfg")
    List<MoveTargetPO> getN1A1MoveTargetList();

    @UseDataSource("a3mfg")
    List<MoveTargetPO> getN1A3MoveTargetList();

}
