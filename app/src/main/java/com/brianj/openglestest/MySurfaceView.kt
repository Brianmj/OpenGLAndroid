package com.brianj.openglestest

import android.content.Context
import android.opengl.GLES10



import android.opengl.GLES32.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import java.nio.ByteBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by brianj on 6/21/17.
 */

class MyRenderer(ctx: Context) : GLSurfaceView.Renderer
{
    val TAG = "MyRenderer"
    val colorBuffer = FloatArray(4)
    val depthBuffer = FloatArray(1)
    val context = ctx
    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG, "onFrameDraw")
        glClearBufferfv(GL_DEPTH, 0, depthBuffer, 0)
        glClearBufferfv(GL_COLOR, 0, colorBuffer, 0)






    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged")
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated")
        colorBuffer[0] = 0.0f; colorBuffer[1] = 1.0f; colorBuffer[2] = 0.5f; colorBuffer[3] = 1.0f
        depthBuffer[0] = 1.0f

        val manager = ShaderManager(context)
        manager.buildGraphicsProgramAssets("myglsl.vert", "fragment.frag")




        
    }
}

public class MySurfaceView constructor(context: Context, attrs: AttributeSet) : GLSurfaceView(context, attrs)
{
    init {
        setEGLContextClientVersion(3)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        setRenderer(MyRenderer(context))
    }
}