package zsc.ruanc.practicaltraining.model.vo;

import lombok.Data;

/**
 *  管理员使用
 *  按条件查找用户
 *  最少三选一
 */
@Data
public class SelectConditionVo {

    private Integer id;

    private String email;

    private String name;

}
