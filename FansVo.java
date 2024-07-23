package zsc.ruanc.practicaltraining.model.vo;


import lombok.Data;
import zsc.ruanc.practicaltraining.model.domain.TMailbox;
import zsc.ruanc.practicaltraining.model.domain.TUserLikes;

import java.util.List;


/**
 * 管理员用 发送信息给用户
 */
@Data
public class FansVo extends TUserLikes {
    private String email;
}
