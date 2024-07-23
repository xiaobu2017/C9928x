package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateArticleVo {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "文章id", required = true, dataType = "string")
    private Integer id;

    @NotBlank(message = "title不能为空")
    @ApiModelProperty(value = "文章标题", required = true, dataType = "string")
    private String title;

    @ApiModelProperty(value = "摘要", required = true, dataType = "string")
    private String smallText;

    @NotBlank(message = "content不能为空")
    @ApiModelProperty(value = "文章内容", required = true, dataType = "string")
    private String content;

    @ApiModelProperty(value = "文章分类", required = true, dataType = "string")
    private String categories;

    @ApiModelProperty(value = "旧文章分类", required = true, dataType = "string")
    private String oldCategories;

    @ApiModelProperty(value = "文章标签", required = true, dataType = "string")
    private String tags;

    private Date date;

    private Integer userId;

}
