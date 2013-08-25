package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.Stocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by yanga on 2013/08/17.
 */
public abstract class Manager {
    protected static final String EQUIPMENT = "equipment";
    private static final String TAG = "Manager";
    private CeECOApplication application;
    public Manager(CeECOApplication application){
        this.application = application;
    }
    public File getFile(String stockCategory,String fileName){
        File dir = new File(new File(application.getFilesDir(), URLEncoder.encode(EQUIPMENT)), stockCategory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, fileName);
    }
    public void clearCache(String stockCategory,String filename) throws IOException, ClassNotFoundException{
        File cache = getFile(stockCategory,filename);
        File cacheDir = application.getCacheDir();
        if (cache.exists()){
            delete(cache);
        }
        if (cacheDir.exists()){
            delete(cacheDir);
        }
    }
    private void delete(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                delete(child);

        fileOrDirectory.delete();
    }
}
