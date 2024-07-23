package zsc.ruanc.practicaltraining.model.vo;

import lombok.Data;
import zsc.ruanc.practicaltraining.model.domain.TArticle;
import zsc.ruanc.practicaltraining.model.domain.TComment;
import zsc.ruanc.practicaltraining.model.domain.TStatistic;

import java.util.List;

/**
 *
 */
@Data
public class getArticleVo {

    private TArticle article;  // 文章

    private String author; // 作者

    private TStatistic statistic; // 统计

    private List<CommentVo> CommentVo; // 评论 + 回复

    private Long commentCount;

}
