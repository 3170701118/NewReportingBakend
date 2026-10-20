package mfg.reporting.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatHoldLotChart implements Serializable {
    private String module;
    private Integer tdWip;
    private Integer ydWip;
    private Integer pre2dWip;
    private Integer pre3dWip;
}
