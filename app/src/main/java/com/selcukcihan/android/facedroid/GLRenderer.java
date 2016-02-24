package com.selcukcihan.android.facedroid;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLES11;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;




public class GLRenderer implements Renderer {

    private boolean mFirstDraw;
    private boolean mSurfaceCreated;
    private int mWidth;
    private int mHeight;
    private long mLastTime;
    private int mFPS;
    private final GLSurfaceView mView;

    public GLRenderer(GLSurfaceView view) {
        mView = view;
        mFirstDraw = true;
        mSurfaceCreated = false;
        mWidth = -1;
        mHeight = -1;
        mLastTime = System.currentTimeMillis();
        mFPS = 0;
    }

    @Override
    public void onSurfaceCreated(GL10 notUsed,
                                 EGLConfig config) {
        mSurfaceCreated = true;
        mWidth = -1;
        mHeight = -1;
    }

    @Override
    public void onSurfaceChanged(GL10 notUsed, int width,
                                 int height) {
        if (!mSurfaceCreated && width == mWidth && height == mHeight) {
            return;
        }

        mWidth = width;
        mHeight = height;

        GLES11.glClearColor(0.5f, 0f, 0f, 1f);
        mSurfaceCreated = false;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (gl instanceof GL11) {
            GL11 gl11 = (GL11) gl;

        }
        if (mFirstDraw) {
            mFirstDraw = false;
        }
    }

    public int getFPS() {
        return mFPS;
    }
}