package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.Service;
import com.htm.dto.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by yanga on 2013/09/29.
 */
public class ServiceManager extends Manager {
    private static final String TAG = ServiceManager.class.getName();
    private static final String category = "services";
    private static final String fileName = "due";
    public ServiceManager(CeECOApplication application) {
        super(application);
    }
    public void saveService(Service service){
        Services s = getServices();
        s.getServices().add(service);
        File serviceFile = getFile(category,fileName);
        try {
            if (!serviceFile.exists()){
                serviceFile.createNewFile();
            }
            FileOutputStream fileOut = new FileOutputStream(serviceFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(s);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Log.e(TAG,e.getMessage(),e);
        }

    }
    public Services getServices(){
        Services services;
        File serviceFile = getFile(category,fileName);
        try {
            if (!serviceFile.exists()){
                services = new Services();
            } else {
                FileInputStream fileIn = new FileInputStream(serviceFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                services = (Services)in.readObject();
                in.close();
                fileIn.close();
            }
            if (services.getServices()==null){
                services.setServices(new LinkedList<Service>());
            }
            return services;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
            return new Services();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return new Services();
        }
    }
}
