package com.fges.adapters.in.web.mapper;

import com.fges.valueobject.SystemInfo;
import fr.anthonyquere.MyGroceryShop.Runtime;

public class SystemInfoMapper {
    public static Runtime toRuntime(SystemInfo systemInfo) {
        return new Runtime(
                systemInfo.getDate(),
                systemInfo.getJavaVersion(),
                systemInfo.getOsName()
        );
    }
}
