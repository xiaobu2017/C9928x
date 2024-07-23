package zsc.ruanc.practicaltraining.model.vo;

import lombok.Data;
import zsc.ruanc.practicaltraining.model.domain.TComment;


@Data
public class CommentVo {

    private TComment comment; // 评论

    private String articleName; // 对用的文章名

}
