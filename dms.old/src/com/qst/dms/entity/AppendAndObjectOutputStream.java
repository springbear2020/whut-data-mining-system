package com.qst.dms.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author 李春雄
 * 以追加方式向文件中写入序列化的日志、物流信息
 */
public class AppendAndObjectOutputStream extends ObjectOutputStream {
    public static File file = null;

    public AppendAndObjectOutputStream(File file) throws IOException{
        super(new FileOutputStream(file,true));
    }

    //以追加方式向文件中写入序列化的日志、物流信息
    @Override
    public void writeStreamHeader() throws IOException{
        if(file != null){
            if(file.length() == 0){
                super.writeStreamHeader();
            }else{
                this.reset();
            }
        }else{
            //System.out.println("文件不存在")；
            super.writeStreamHeader();
        }
    }

}
