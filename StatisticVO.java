package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 添加的文章的vo
 */
@Data
public class StatisticVO {
    private Integer hitsSum;
    private String categories;

}
