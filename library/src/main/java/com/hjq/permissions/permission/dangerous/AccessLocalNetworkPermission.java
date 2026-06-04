package com.hjq.permissions.permission.dangerous;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.hjq.permissions.manifest.AndroidManifestInfo;
import com.hjq.permissions.manifest.node.PermissionManifestInfo;
import com.hjq.permissions.permission.PermissionGroups;
import com.hjq.permissions.permission.PermissionNames;
import com.hjq.permissions.permission.base.IPermission;
import com.hjq.permissions.permission.common.DangerousPermission;
import com.hjq.permissions.tools.PermissionVersion;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XXPermissions
 *    time   : 2026/05/24
 *    desc   : 访问局域网权限类
 */
public final class AccessLocalNetworkPermission extends DangerousPermission {

    /** 当前权限名称，注意：该常量字段仅供框架内部使用，不提供给外部引用，如果需要获取权限名称的字符串，请直接通过 {@link PermissionNames} 类获取 */
    public static final String PERMISSION_NAME = PermissionNames.ACCESS_LOCAL_NETWORK;

    public static final Creator<AccessLocalNetworkPermission> CREATOR = new Creator<AccessLocalNetworkPermission>() {

        @Override
        public AccessLocalNetworkPermission createFromParcel(Parcel source) {
            return new AccessLocalNetworkPermission(source);
        }

        @Override
        public AccessLocalNetworkPermission[] newArray(int size) {
            return new AccessLocalNetworkPermission[size];
        }
    };

    public AccessLocalNetworkPermission() {
        // default implementation ignored
    }

    private AccessLocalNetworkPermission(Parcel in) {
        super(in);
    }

    @NonNull
    @Override
    public String getPermissionName() {
        return PERMISSION_NAME;
    }

    @Override
    public String getPermissionGroup(@NonNull Context context) {
        return PermissionGroups.NEARBY_DEVICES;
    }

    @Override
    public int getFromAndroidVersion(@NonNull Context context) {
        return PermissionVersion.ANDROID_17;
    }

    @Override
    protected void checkSelfByManifestFile(@NonNull Activity activity,
                                           @NonNull List<IPermission> requestList,
                                           @NonNull AndroidManifestInfo manifestInfo,
                                           @NonNull List<PermissionManifestInfo> permissionInfoList,
                                           @Nullable PermissionManifestInfo currentPermissionInfo) {
        super.checkSelfByManifestFile(activity, requestList, manifestInfo, permissionInfoList, currentPermissionInfo);
        // 如果权限出现的版本小于 minSdkVersion，则证明该权限可能会在旧系统上面申请，需要在 AndroidManifest.xml 文件注册一下旧版权限
        if (getFromAndroidVersion(activity) > getMinSdkVersion(activity, manifestInfo)) {
            // 拆分权限模型：https://developer.android.google.cn/privacy-and-security/local-network-permission?hl=zh-cn#split-permission
            checkPermissionRegistrationStatus(permissionInfoList, Manifest.permission.INTERNET);
        }
    }
}