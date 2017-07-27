package com.brianj.openglestest

import android.opengl.GLES20
import android.opengl.GLES31
import java.util.*
import android.opengl.GLES32.*
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channel
import java.nio.channels.FileChannel

/**
 * Created by brianj on 7/21/17.
 */
class ShaderManager
{
    private var programMap: MutableMap<UUID, Int>

    init {
        programMap = mutableMapOf()


    }

    fun buildGraphicsProgram(vertexFile: String, fragmentFile: String): UUID
    {
        val programId = doBuildGraphicsProgram(vertexFile, fragmentFile)
        // we have a valid programId.
        val uuid = addProgramToManager(programId)

        return uuid
    }

    private fun doBuildGraphicsProgram(vertexFile: String, fragmentFile: String): Int
    {

        val vertexShader = buildVertex(vertexFile)
        val fragmentShader = buildFragment(fragmentFile)

        val programId = generateProgram()
        attachShader(programId, vertexShader)
        attachShader(programId, fragmentShader)

        linkProgram(programId)
        checkProgramStatus(programId, "Graphics program")

        detachAndDeleteShader(programId, vertexShader)
        detachAndDeleteShader(programId, fragmentShader)

        return programId
    }

    private fun openChannelToFile(fileName: String): FileChannel
    {
        val fos = FileInputStream(fileName)
        val channel = fos.channel
        return channel
    }

    private fun readSource(channel: FileChannel): String
    {
        val size = channel.size()
        val buffer = ByteBuffer.allocate(size.toInt())
        val bytesRead = channel.read(buffer)


        if(bytesRead != size.toInt())
            throw Exception("Not all bytes could be read. Bytes read: $bytesRead. size of file: $size")

        val source = String(buffer.array())

        channel.close()
        return source
    }

    private fun compileShader(shaderType: Int, shaderSource: String): Int
    {
        val shader = GLES20.glCreateShader(shaderType)
        GLES20.glShaderSource(shader, shaderSource)
        GLES20.glCompileShader(shader)

        return shader
    }

    private fun buildVertex(vertexFile: String): Int
    {
        val vertexChannel = openChannelToFile(vertexFile)
        val vertexSource = readSource(vertexChannel)
        val vertexShader = compileShader(GLES20.GL_VERTEX_SHADER, vertexSource)
        checkShaderStatus(vertexShader, "Vertex")
        vertexChannel.close()

        return vertexShader
    }

    private fun buildFragment(fragmentFile: String): Int
    {
        val fragmentChannel = openChannelToFile(fragmentFile)
        val fragmentSource = readSource(fragmentChannel)
        val fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentSource)
        checkShaderStatus(fragmentShader, "Fragment")
        fragmentChannel.close()

        return fragmentShader
    }

    private fun linkProgram(program: Int)
    {

    }

    private fun checkShaderStatus(shader: Int, shaderType: String)
    {
        var success = IntArray(1)
        GLES20.glGetShaderiv(shader, GL_COMPILE_STATUS, success, 0)

        if(success[0] <= GL_FALSE)
        {
            // failure
            val errorString = GLES20.glGetShaderInfoLog(shader)
            val resultString = "$shaderType: $errorString"
            throw RuntimeException(resultString)
        }
    }

    private fun checkProgramStatus(program: Int, programType: String)
    {
        var success = IntArray(1)
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, success, 0)

        if(success[0] <= GL_FALSE)
        {
            // failure
            val errorString = GLES20.glGetProgramInfoLog(program)
            val resultString = "$programType: $errorString"
            throw RuntimeException(resultString)
        }
    }

    private fun generateProgram(): Int = GLES20.glCreateProgram()
    private fun attachShader(program: Int, shader: Int) {
        GLES20.glAttachShader(program, shader)
    }

    private fun detachAndDeleteShader(programId: Int, shaderId: Int)
    {
        GLES20.glDetachShader(programId, shaderId)
        GLES20.glDeleteShader(shaderId)
    }

    private fun addProgramToManager(programId: Int): UUID
    {
        val uuid = UUID.randomUUID()
        programMap[uuid] = programId

        return uuid
    }

    private fun removeProgramFromManager(uuid: UUID)
    {

    }

}