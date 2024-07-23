package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 管理员给用户发邮件
 */
@Data
public class AdminEmailVo {
    @NotBlank(message = "text不能为空")
    @ApiModelProperty(value = "邮件内容", required = true, dataType = "string")
    private String text;

    @ApiModelProperty(value = "用户id", required = true, dataType = "string")
    @NotNull(message = "id不能为空")
    private Integer id;

}
