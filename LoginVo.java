package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class LoginVo {


    @NotBlank(message = "email不能为空")
    @ApiModelProperty(value = "用户邮箱", required = true, dataType = "string")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式")
    private String email;

    @NotBlank(message = "password不能为空")
    @ApiModelProperty(value = "密码", required = true, dataType = "string")
    private String password;

}
