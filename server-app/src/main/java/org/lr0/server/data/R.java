package org.lr0.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.lr0.server.core.ProjectException;
import org.lr0.server.core.RespStatus;

/**
 * @author Qnxy
 */
@Data
@RequiredArgsConstructor
public final class R<DATA> {
    private final DATA data;
    private final String statusCode;
    private final String statusMessage;

    @JsonIgnore
    private Object[] args = null;

    public R(DATA data, RespStatus status, Object... args) {
        this.data = data;
        this.args = args;
        this.statusCode = status.getCode();
        this.statusMessage = status.messageFormat(this.args);
    }

    public static <DATA> R<DATA> success(DATA data) {
        return new R<>(data, RespStatus.SUCCESS);
    }

    public static <DATA> R<DATA> failure(RespStatus status) {
        return new R<>(null, status);
    }

    public static <DATA> R<DATA> failure(RespStatus status, Object... args) {
        return new R<>(null, status, args);
    }

    public static <DATA> R<DATA> ofProjectEx(ProjectException exception) {
        return new R<>(null, exception.getStatus(), exception.getArgs());
    }


}
