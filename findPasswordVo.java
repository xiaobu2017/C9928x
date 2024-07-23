package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class findPasswordVo {

    @NotBlank(message = "email不能为空")
    @ApiModelProperty(value = "用户邮箱", required = true, dataType = "string")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式")
    private String email;

    @NotNull(message = "code不能为空")
    @Min(message = "验证码错误", value = 999)
    @Max(message = "验证码错误", value = 10000)
    private Integer code;

    @NotBlank(message = "password不能为空")
    @Length(message = "密码要大于6位", min = 6)
    private String password;

}
