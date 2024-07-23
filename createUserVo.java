package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotBlank;

@Data
@ApiModel("创建用户的信息携带类")
public class createUserVo {

    @NotBlank(message = "username不能为空")
    @ApiModelProperty(value = "用户名称", required = true, dataType = "string")
    private String username;

    @NotBlank(message = "password不能为空")
    @ApiModelProperty(value = "用户密码", required = true, dataType = "string")
    private String password;

    @NotBlank(message = "email不能为空")
    @ApiModelProperty(value = "用户邮箱", required = true, dataType = "string")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式")
    private String email;
}
