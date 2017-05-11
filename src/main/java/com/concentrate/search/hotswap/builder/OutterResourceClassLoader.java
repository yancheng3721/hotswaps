package com.concentrate.search.hotswap.builder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.concentrate.search.hotswap.Algorithm;
@SuppressWarnings("unchecked")
public class OutterResourceClassLoader extends ClassLoader {

	private static final Logger log = LoggerFactory.getLogger(OutterResourceClassLoader.class);

    public OutterResourceClassLoader() {
		super(OutterResourceClassLoader.class.getClassLoader());
	}

	public Class<Algorithm> defineClass(String name, byte[] b) {
        return (Class<Algorithm>) defineClass(name, b, 0, b.length);
    }

    public Class<Algorithm> loadOutterClass(String filePath,String className) {
        Class<Algorithm> result = null;
        try {
            byte[] datas = loadClassData(filePath);
            if (datas == null) {
            	return result;
            }
            defineClass(className, datas);
            result = (Class<Algorithm>) loadClass(className);
        } catch (Exception e) {
            log.error("load class error!", e);
        }
        return result;
    }
    
    public Class<?> loadOutterClazz(String filePath,String className) {
        Class<?> result = null;
        try {
            byte[] datas = loadClassData(filePath);
            if (datas == null) {
            	return result;
            }
            defineClass(className, datas, 0, datas.length);
            result =  loadClass(className);
        } catch (Exception e) {
            log.error("load class error!", e);
        }
        return result;
    }

    protected byte[] loadClassData(String name) {
        FileInputStream fis = null;
        byte[] datas = null;
        try {
            fis = new FileInputStream(name);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b;
            while ((b = fis.read()) != -1) {
                bos.write(b);
            }
            datas = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
        	log.error("load class error!", e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return datas;
    }

}
