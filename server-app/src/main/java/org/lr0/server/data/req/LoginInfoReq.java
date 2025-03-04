package org.lr0.server.data.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录参数
 *
 * @author Qnxy
 */
@Data
public class LoginInfoReq {

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
