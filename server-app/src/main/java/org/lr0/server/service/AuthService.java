package org.lr0.server.service;

import org.lr0.server.data.R;
import org.lr0.server.data.req.LoginInfoReq;
import org.lr0.server.data.req.RegisterReq;

/**
 * @author Qnxy
 */
public interface AuthService {
    
    R<String> login(LoginInfoReq loginInfoReq);

    R<Void> register(RegisterReq registerReq);
}
