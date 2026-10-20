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
public class WatHoldLotSummary implements Serializable {
    private String module;
    private Integer waitScrapWip;
    private Integer scrapedWip;
    private Integer nextMonthWip;
    private Integer curMonthWip;
    private Integer nonReleaseWip;
    private Integer waitBackWip;
    private Float waitHoldTime;
    private Integer totalWip;
    private Float curMonthHoldTime;
    private Integer addQty;
    private Integer ydWip;
}
