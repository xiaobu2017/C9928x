package zsc.ruanc.practicaltraining.model.vo;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 管理员用 发送信息给用户
 */
@Data
public class MailboxVo {

    private Integer toId;

    @NotBlank(message = "发送信息不能为空")
    private String info;

}
