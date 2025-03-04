package org.lr0.server.controller;

import org.lr0.server.core.ProjectException;
import org.lr0.server.core.RespStatus;
import org.lr0.server.data.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Qnxy
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public R<UserInfo> getTest() {
        return R.success(
                new UserInfo(
                        "user01",
                        UUID.randomUUID().toString().replace("-", ""),
                        LocalDate.now().plusYears(-18),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/ex")
    public R<Void> testEx() {
        throw new ProjectException(RespStatus.ACCESS_RESOURCE_DOES_NOT_EXIST, "/aa");
    }

    public record UserInfo(
            String username,
            String password,
            LocalDate birthday,
            LocalDateTime createdAt
    ) {
    }

}

