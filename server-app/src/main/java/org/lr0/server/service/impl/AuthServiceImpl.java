package org.lr0.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lr0.server.core.FileSavaParamProperties;
import org.lr0.server.core.RespStatus;
import org.lr0.server.data.R;
import org.lr0.server.data.entity.UserInfo;
import org.lr0.server.data.req.LoginInfoReq;
import org.lr0.server.data.req.RegisterReq;
import org.lr0.server.mapper.UserInfoMapper;
import org.lr0.server.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author Qnxy
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final FileSavaParamProperties fileSavaParamProperties;


    private final UserInfoMapper userInfoMapper;
    private final RequestContextFilter requestContextFilter;

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

    @SneakyThrows
    @Override
    public R<Void> register(RegisterReq registerReq) {
//        UserInfo userInfo = this.userInfoMapper.selectByUsername(registerReq.getUsername());
//        if (userInfo != null) {
//            return R.failure(RespStatus.ACCOUNT_ALREADY_EXISTS);
//        }

        MultipartFile avatar = registerReq.getAvatar();
        String avatarUri = UUID.randomUUID().toString().replace("-", "");
        System.out.println(avatar.getName());
        String originalFilename = avatar.getOriginalFilename();
        String fileSuffix;
        if (originalFilename == null) {
            fileSuffix = ".jpg";
        } else {
            fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (StringUtils.hasText(fileSuffix)) {
                fileSuffix = ".jpg";
            }
        }
        String dirPath = this.fileSavaParamProperties.getSaveAvatarPath() + LocalDate.now().format(dtf);
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }


        log.info("original filename: {}", originalFilename);
        avatarUri = avatarUri + fileSuffix;
        avatar.transferTo(new File(dirPath + "/" + avatarUri));
        log.info("头像上传成功: {}", avatarUri);

//        return saveToDb(registerReq, avatar);
        UserInfo userInfoInsert = new UserInfo()
                .setUsername(registerReq.getUsername())
                .setPassword(registerReq.getPassword())
                .setAvatarUri(avatarUri);
        this.userInfoMapper.insert(userInfoInsert);

        return R.success();
    }

    private R<Void> saveToDb(RegisterReq registerReq, MultipartFile avatar) throws IOException {
        byte[] bytes = avatar.getBytes();
        System.out.println(bytes.length);

        UserInfo userInfoInsert = new UserInfo()
                .setUsername(registerReq.getUsername())
                .setPassword(registerReq.getPassword());
//                .setAvatar(bytes);

        this.userInfoMapper.insert(userInfoInsert);
        return R.success();
    }

}
