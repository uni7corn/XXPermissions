package com.hjq.permissions.core;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.hjq.permissions.OnPermissionDescription;
import com.hjq.permissions.permission.base.IPermission;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XXPermissions
 *    time   : 2026/04/11
 *    desc   : 权限说明逻辑处理
 */
public final class PermissionDescriptionLogic implements OnPermissionFragmentCallback {

    @NonNull
    private final Activity mActivity;
    @NonNull
    private final OnPermissionDescription mPermissionDescription;
    @NonNull
    private final List<IPermission> mPermissions;
    @NonNull
    private final Runnable mFinishRunnable;

    public PermissionDescriptionLogic(@NonNull Activity activity,
                                      @NonNull List<IPermission> permissions,
                                      @NonNull Runnable finishRunnable,
                                      @NonNull OnPermissionDescription permissionDescription) {
        mActivity = activity;
        mPermissions = permissions;
        mPermissionDescription = permissionDescription;
        mFinishRunnable = finishRunnable;
    }

    @Override
    public void onRequestPermissionNow() {
        mPermissionDescription.onRequestPermissionStart(mActivity, mPermissions);
    }

    @Override
    public void onRequestPermissionFinish() {
        mPermissionDescription.onRequestPermissionEnd(mActivity, mPermissions);
        mFinishRunnable.run();
    }

    @Override
    public void onRequestPermissionAnomaly() {
        mPermissionDescription.onRequestPermissionEnd(mActivity, mPermissions);
    }
}