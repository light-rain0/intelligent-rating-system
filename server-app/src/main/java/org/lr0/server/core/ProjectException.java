package org.lr0.server.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Qnxy
 */
@Getter
@Setter
public class ProjectException extends RuntimeException {

    private final RespStatus status;
    private final Object[] args;

    public ProjectException(RespStatus status) {
        this.status = status;
        this.args = null;
    }

    public ProjectException(RespStatus status, Object... args) {
        this.status = status;
        this.args = args;
    }

    public ProjectException(String message, RespStatus status, Object[] args) {
        super(message);
        this.status = status;
        this.args = args;
    }
}
