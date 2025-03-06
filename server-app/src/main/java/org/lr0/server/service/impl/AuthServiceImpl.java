package org.lr0.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lr0.server.core.RespStatus;
import org.lr0.server.data.R;
import org.lr0.server.data.entity.UserInfo;
import org.lr0.server.data.req.LoginInfoReq;
import org.lr0.server.mapper.UserInfoMapper;
import org.lr0.server.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Qnxy
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserInfoMapper userInfoMapper;

    @Transactional
    @Override
    public R<String> login(LoginInfoReq loginInfoReq) {
        UserInfo userInfo = this.userInfoMapper.selectUsernameAndPassword(loginInfoReq.getUsername(), loginInfoReq.getPassword());

        if (userInfo == null) {
            return R.failure(RespStatus.INCORRECT_ACCOUNT_OR_PASSWORD);
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        UserInfo userInfoUpdate = new UserInfo()
                .setId(userInfo.getId())
                .setToken(token)
                .setUpdatedAt(LocalDateTime.now());
        this.userInfoMapper.updateById(userInfoUpdate);
        return R.success(token);
    }

    @Override
    public R<Void> register(LoginInfoReq loginInfoReq) {
        UserInfo userInfo = this.userInfoMapper.selectByUsername(loginInfoReq.getUsername());
        if (userInfo != null) {
            return R.failure(RespStatus.ACCOUNT_ALREADY_EXISTS);
        }

        UserInfo userInfoInsert = new UserInfo()
                .setUsername(loginInfoReq.getUsername())
                .setPassword(loginInfoReq.getPassword());
        this.userInfoMapper.insert(userInfoInsert);
        return R.success();
    }
}
