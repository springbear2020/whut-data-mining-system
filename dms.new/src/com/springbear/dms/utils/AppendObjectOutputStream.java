package com.springbear.dms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 解决 java.io.StreamCorruptedException: invalid type code: AC 异常
 * 原因分析参考博客：https://blog.csdn.net/weixin_51008866/article/details/121061149
 *
 * @author Spring-_-Bear
 * @version 2021-10-30 21:48
 */
public class AppendObjectOutputStream extends ObjectOutputStream {
    /**
     * 文件路径
     */
    private static File file = null;

    public static void setFile(File file) {
        AppendObjectOutputStream.file = file;
    }

    /**
     * 调用父类的构造器，初始化父类
     *
     * @param file The path of the file
     * @throws IOException I/O异常
     */
    public AppendObjectOutputStream(File file) throws IOException {
        super(new FileOutputStream(file, true));
    }

    /**
     * 重写父类的 writeSteamHeader() 方法以实现文件中只存在一个StreamHeader
     *
     * @throws IOException I/O 异常
     */
    @Override
    public void writeStreamHeader() throws IOException {
        // 如果文件为空直接写入 StreamHeader
        if (file == null) {
            super.writeStreamHeader();
        } else {
            // 文件长度为0即文件中没有内容时也写入 StreamHeader
            if (file.length() == 0) {
                super.writeStreamHeader();
            } else {
                // 文件存在且文件中存在内容，则说明文件中已经存在了一个 StreamHeader
                // 则调用父类的 reset() 方法保证文件中只存在一个 StreamHeader
                this.reset();
            }
        }
    }
}

