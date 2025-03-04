package org.lr0.server.data.entity;

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
    private Long id;
    private String username;
    private String password;
    
    private String token;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
