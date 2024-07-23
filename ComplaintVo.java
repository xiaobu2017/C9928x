package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import zsc.ruanc.practicaltraining.model.domain.TComment;
import zsc.ruanc.practicaltraining.model.domain.TComplaint;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 用户投诉表
 */
@Data
public class ComplaintVo extends TComplaint {
  private String articleTitle;
  @ApiModelProperty(value = "被投诉人的名字", required = false, dataType = "string")
  private String toName;

}
