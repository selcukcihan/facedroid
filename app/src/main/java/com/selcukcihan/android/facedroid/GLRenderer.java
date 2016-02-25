package com.selcukcihan.android.facedroid;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLES11;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.WindowManager;

import com.selcukcihan.xfacej.XFaceApplication;
import com.selcukcihan.xfacej.xfaceapp.ModelCamera;


public class GLRenderer implements Renderer {
    private final XFaceApplication mApplication;
    private ModelCamera mCamera;

    public GLRenderer(XFaceApplication application) {
        mApplication = application;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        GL11 gl = null;
        if (gl10 instanceof  GL11) {
            gl = (GL11) gl10;
        }
        if (gl == null) {
            return;
        }
        gl.glShadeModel(GL11.GL_SMOOTH);
        gl.glCullFace(GL11.GL_BACK);
        gl.glFrontFace(GL11.GL_CCW);
        gl.glEnable(GL11.GL_CULL_FACE);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL11.GL_DEPTH_TEST);
        gl.glEnable(GL11.GL_LIGHTING);
        gl.glEnable(GL11.GL_LIGHT0);
        gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL11.GL_MODELVIEW);

        mCamera = new ModelCamera();

        mApplication.onLoadFDP("alice.fdp", "face\\", gl);
        mCamera.setAxisAngle(mApplication.m_pFace.getFDP().getGlobalAxisAngle(), gl);
        mCamera.setTranslation(mApplication.m_pFace.getFDP().getGlobalTranslation());

        mApplication.onLoadPHO("face\\say-suffering-angina.pho", "english");

        gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GL11 gl = null;
        if (gl10 instanceof  GL11) {
            gl = (GL11) gl10;
        }
        if (gl == null) {
            return;
        }

        final GLU glu = new GLU();

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL11.GL_PROJECTION);
        gl.glLoadIdentity();

        float ratio = (float)width / (float)height;
        GLU.gluPerspective(gl, 30.0f, ratio, 10.0f, 1000.0f);
        gl.glMatrixMode(GL11.GL_MODELVIEW);

        // Camera creation takes place here
        if(mCamera == null)
        {
            mCamera = new ModelCamera();
            mCamera.setScreenSize(width, height);
            mCamera.setDistance(-700);
            mCamera.setMode(ModelCamera.kMODE.ZOOM);
        }
        else
        {
            mCamera.setScreenSize(width, height);
            mCamera.apply(gl);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (gl instanceof  GL11 && mApplication != null) {
            if(mCamera == null)
            {
                /*
                int h = p_autoDrawable.getHeight();
                int w = p_autoDrawable.getWidth();
                gl.glViewport(0, 0, w, h);
                gl.glMatrixMode(GL.GL_PROJECTION);
                m_gl.glLoadIdentity();

                final GLU glu = new GLU();
                float ratio = (float)w / (float)h;
                glu.gluPerspective(30, ratio, 10.0, 1000.0);

                m_gl.glMatrixMode(GL.GL_MODELVIEW);

                m_pCamera = new ModelCamera();
                m_pCamera.setScreenSize(w, h);
                m_pCamera.setDistance(-700);
                m_pCamera.setMode(kMODE.ZOOM);*/
            }
            mCamera.apply((GL11) gl);
            mApplication.onRenderFrame((GL11) gl);

            mApplication.onAdvanceFrame();
        }
    }
}