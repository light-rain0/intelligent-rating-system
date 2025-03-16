package org.lr0.server.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.lr0.server.core.FileSavaParamProperties;
import org.lr0.server.core.RespStatus;
import org.lr0.server.data.R;
import org.lr0.server.data.entity.UserInfo;
import org.lr0.server.data.req.LoginInfoReq;
import org.lr0.server.data.req.RegisterReq;
import org.lr0.server.mapper.UserInfoMapper;
import org.lr0.server.service.AuthService;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


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
    private final UserInfoMapper userInfoMapper;
    private final FileSavaParamProperties fileSavaParamProperties;

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
    public R<Void> register(@Validated RegisterReq registerReq) {
        return authService.register(registerReq);
    }

    @SneakyThrows
    @GetMapping("/avatar/{username}")
    public R<Void> avatar(@PathVariable String username, HttpServletResponse resp) {
        UserInfo userInfo = this.userInfoMapper.selectByUsername(username);
        if (userInfo == null) {
            return R.failure(RespStatus.UNSUPPORTED_REQUEST_METHOD);
        }


        String avatarUri = userInfo.getAvatarUri();
        Path path = Paths.get(fileSavaParamProperties.getSaveAvatarPath(), avatarUri);

//        resp.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatarUri, StandardCharsets.UTF_8));
        StreamUtils.copy(new FileInputStream(path.toFile()), resp.getOutputStream());

        return R.success();
    }

//    @SneakyThrows
//    @GetMapping("/avatar/{username}")
//    public R<Void> getUserAvatar(@PathVariable String username, HttpServletResponse response) {
//        UserInfo userInfo = this.userInfoMapper.selectByUsername(username);
//        if (userInfo == null) {
//            return R.failure(RespStatus.UNSUPPORTED_REQUEST_METHOD);
//        }
//
//        byte[] avatar = userInfo.getAvatar();
//        response.setContentType("image/jpeg");
//        response.setHeader("Cache-Control", "no-cache");
//        response.getOutputStream().write(avatar);
//        return null;
//    }

    @GetMapping
    public R<List<UserInfo>> getUserInfo() {
        List<UserInfo> userInfos = this.userInfoMapper.selectList(null);
        return R.success(userInfos);
    }
}
