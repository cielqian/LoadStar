package com.ciel.pocket.link.infrastructure.utils;

import com.ciel.pocket.link.dto.output.ReturnModel;
import com.ciel.pocket.link.infrastructure.exceptions.FriendlyException;

public class RemoteReturnCheck {
    public static void doValidate(ReturnModel returnModel){
        if (returnModel.getStatus() != 200){
            throw new FriendlyException(returnModel.getMessage());
        }
    }
}
