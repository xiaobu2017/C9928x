package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 添加的文章的vo
 */
@Data
public class ArticleVo {

    @NotBlank(message = "title不能为空")
    @ApiModelProperty(value = "文章标题", required = true, dataType = "string")
    private String title;

    @NotBlank(message = "content不能为空")
    @ApiModelProperty(value = "文章内容", required = true, dataType = "string")
    private String content;

    @ApiModelProperty(value = "文章分类", required = false, dataType = "string")
    private String categories;

    @ApiModelProperty(value = "文章标签", required = false, dataType = "string")
    private String tags;

    @ApiModelProperty(value = "是否可评论", required = false, dataType = "boolean")
    private Boolean allowComment;

    @ApiModelProperty(value = "摘要", required = false, dataType = "string")
    private String smallText;

}
