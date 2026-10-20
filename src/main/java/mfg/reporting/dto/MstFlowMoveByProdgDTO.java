package mfg.reporting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MstFlowMoveByProdgDTO implements Serializable  {

    private String shiftDate;

    private String prodg1;

    private String mainpdId;

    private String operNo;

    private Integer move;

    private String locationFab;

    private String fab;

}
