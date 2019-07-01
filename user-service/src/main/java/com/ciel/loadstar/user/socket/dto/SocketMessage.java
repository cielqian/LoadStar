package com.ciel.loadstar.user.socket.dto;

import com.ciel.loadstar.user.enums.NotifyEnum;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/28 9:32
 */

@Data
public class SocketMessage {
    NotifyEnum type;

    Object data;
}
