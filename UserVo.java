package zsc.ruanc.practicaltraining.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 用户基本信息
 */
@Data
public class UserVo {

    private Integer id;

    @Past(message = "日期错误")
    @ApiModelProperty(value = "生日", required = false, dataType = "Date")
    private Date birthday;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别", required = false, dataType = "bool")
    private Boolean sex;


    @ApiModelProperty(value = "座右铭", required = false, dataType = "String")
    private String motto;

    @ApiModelProperty(value = "爱好", required = false, dataType = "String")
    private String hobby;

    @ApiModelProperty(value = "自我介绍", required = false, dataType = "String")
    private String introduce;

}
