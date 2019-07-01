package com.ciel.loadstar.link.utils;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.exceptions.FriendlyException;

public class RemoteReturnCheck {
    public static void doValidate(ReturnModel returnModel){
        if (returnModel.getStatus() != 200){
            throw new FriendlyException(returnModel.getMessage());
        }
    }
}
