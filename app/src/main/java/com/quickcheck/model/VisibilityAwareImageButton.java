//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.quickcheck.model;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.AttributeSet;
import android.widget.ImageButton;

@RestrictTo({Scope.LIBRARY_GROUP})
public class VisibilityAwareImageButton extends ImageButton {
    private int userSetVisibility;

    public VisibilityAwareImageButton(Context context) {
        this(context, (AttributeSet)null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.userSetVisibility = this.getVisibility();
    }

    public void setVisibility(int visibility) {
        this.internalSetVisibility(visibility, true);
    }

    public final void internalSetVisibility(int visibility, boolean fromUser) {
        super.setVisibility(visibility);
        if (fromUser) {
            this.userSetVisibility = visibility;
        }

    }

    public final int getUserSetVisibility() {
        return this.userSetVisibility;
    }
}
