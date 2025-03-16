package org.lr0.server.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author Qnxy
 */
@Data
@Accessors(chain = true)
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;

    private String token;

//    private byte[] avatar;

    private String avatarUri;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
