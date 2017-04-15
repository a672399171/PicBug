package com.zzuzl.util;

import org.springframework.mobile.device.Device;

public final class PathUtil {
    public static String path(Device device, String path) {
        if (device.isMobile()) {
            path = "mobile/" + path;
        }
        return path;
    }
}
