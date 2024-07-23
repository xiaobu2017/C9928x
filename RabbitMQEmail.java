package zsc.ruanc.practicaltraining.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发送邮件的内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQEmail implements Serializable {

    private String to; // 收件人

    private String content; // 内容主体

    private String path; // 图片路径名称

}
