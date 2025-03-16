package org.lr0.server.data.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cyh
 * @since 2025/3/12
 */
@Data
public class RegisterReq {
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

    /**
     * 头像信息
     */
    private MultipartFile avatar;
}
