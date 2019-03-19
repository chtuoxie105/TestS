package android.materialdesignlibrary.zdepthshadowlayout.shadow;

import android.graphics.Canvas;
import android.materialdesignlibrary.zdepthshadowlayout.ZDepthParam;


public interface Shadow {
    void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom);

    void onDraw(Canvas canvas);
}
