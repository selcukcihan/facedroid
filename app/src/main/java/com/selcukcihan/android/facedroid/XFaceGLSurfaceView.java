package com.selcukcihan.android.facedroid;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.selcukcihan.xfacej.xengine.RendererGL;

/**
 * Created by SELCUKCI on 23.2.2016.
 */
public class XFaceGLSurfaceView extends GLSurfaceView {

    private final Renderer mRenderer = null;

    public XFaceGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        //mRenderer = new RendererGL();

        // Set the Renderer for drawing on the GLSurfaceView
        //setRenderer(mRenderer);
    }
}
