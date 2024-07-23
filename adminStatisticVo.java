package zsc.ruanc.practicaltraining.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 统计
 */
@Data
public class adminStatisticVo {

    @NotNull(message = "articleId不能为空")
    private Integer articleId;

    private Integer hits;

    private Integer comments;

    private Integer likes;

}
