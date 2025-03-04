package org.lr0.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.lr0.server.data.entity.UserInfo;

/**
 * @author Qnxy
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    default UserInfo selectUsernameAndPassword(@NotNull String username, String password) {
        return this.selectOne(
                Wrappers.<UserInfo>lambdaQuery()
                        .eq(UserInfo::getUsername, username)
                        .eq(UserInfo::getPassword, password)
        );
    }
}
