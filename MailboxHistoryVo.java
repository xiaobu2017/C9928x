package zsc.ruanc.practicaltraining.model.vo;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import zsc.ruanc.practicaltraining.model.domain.TMailbox;

import java.util.List;


/**
 * 管理员用 发送信息给用户
 */
@Data
public class MailboxHistoryVo {
    private List<TMailbox> mailboxes;
    private String sessionId;
    private String title;
}
