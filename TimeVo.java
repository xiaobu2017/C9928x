package zsc.ruanc.practicaltraining.model.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 时间查询vo
 */

@Data
public class TimeVo {
    private String title;

    private Date leftTime;

    private Date rightTime;

    private Boolean flag;

    @NotNull(message = "cur不能为空")
    @Min(message = "cur不能小于1", value = 1)
    private Integer cur;

    @NotNull(message = "size不能为空")
    @Min(message = "size不能小于1", value = 1)
    private Integer size;

}
