package zsc.ruanc.practicaltraining.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

/**
 * 修改邮箱发邮件
 */
@Data
public class EmailVo {
    @Email(message = "邮箱格式错误")
    private String email;

    private Integer code;
    private String username;
}
