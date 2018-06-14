package com.brianj.openglestest

import android.content.Context
import android.opengl.GLES10
import android.opengl.GLES20


import android.opengl.GLES32.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import java.nio.ByteBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by brianj on 6/21/17.
 */
public class MySurfaceView constructor(context: Context, attrs: AttributeSet) : GLSurfaceView(context, attrs)
{
    init {
        setEGLContextClientVersion(3)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        setRenderer(MyRenderer(context))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}