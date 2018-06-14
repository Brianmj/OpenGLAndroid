package com.brianj.openglestest

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyRenderer(ctx: Context) : GLSurfaceView.Renderer
{
    val TAG = "MyRenderer"
    val colorBuffer = FloatArray(4)
    val depthBuffer = FloatArray(1)
    val context = ctx
    var viewportWidth: Int = 0
    var viewportHeight: Int = 0

    override fun onDrawFrame(gl: GL10?) {
        GLES32.glClearBufferfv(GLES32.GL_DEPTH, 0, depthBuffer, 0)
        GLES32.glClearBufferfv(GLES32.GL_COLOR, 0, colorBuffer, 0)


    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated")
        colorBuffer[0] = 0.607843f; colorBuffer[1] = 0.803921f; colorBuffer[2] = 0.992156f; colorBuffer[3] = 1.0f
        depthBuffer[0] = 1.0f

        val shaderManager = ShaderManager(context)
        //manager.buildGraphicsProgramAssets("myglsl.vert", "fragment.frag")
        //manager.buildGraphicsProgramRaw(R.raw.vertex, R.raw.fragment)


    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged, width: $width, height: $height")
        viewportHeight = height
        viewportWidth = width
        GLES20.glViewport(0, 0, width, height)
    }
}