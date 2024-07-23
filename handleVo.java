package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *  管理员处理投诉结果的Vo
 */
@Data
public class handleVo {

    @Min(message = "id不能为小于1", value = 1)
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "投诉id（主键）", required = true, dataType = "Integer")
    private Integer id;

    @NotBlank(message = "result不能为空")
    @ApiModelProperty(value = "文章内容", required = true, dataType = "string")
    private String result;

    @ApiModelProperty(value = "事件是否结束标志", required = false, dataType = "Boolean")
    private Boolean flag;

    @ApiModelProperty(value = "处理时间", required = false, dataType = "Date")
    private Date resultTime;

    @ApiModelProperty(value = "事件是否结束标志", required = false, dataType = "Boolean")
    private Boolean articleFlag;

}
