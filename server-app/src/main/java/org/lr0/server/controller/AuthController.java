package org.lr0.server.controller;

import lombok.RequiredArgsConstructor;
import org.lr0.server.data.R;
import org.lr0.server.data.req.LoginInfoReq;
import org.lr0.server.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 授权认证
 *
 * @author Qnxy
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody @Validated LoginInfoReq loginInfoReq) {
        return authService.login(loginInfoReq);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public R<Void> register(@RequestBody @Validated LoginInfoReq loginInfoReq) {
        return authService.register(loginInfoReq);
    }
}
