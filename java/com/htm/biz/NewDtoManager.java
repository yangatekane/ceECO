package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.NewDto;
import com.htm.dto.NewDtos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by yanga on 2013/08/16.
 */
public class NewDtoManager {
    private static final String TAG = NewDtoManager.class.getName();
    private CeECOApplication application;
    private static final String EQUIPMENT = "equipment";
    public NewDtoManager(CeECOApplication application){
        this.application = application;
    }
    public File getNewEquipmentFile(String stockCategory) {
        File dir = new File(new File(application.getFilesDir(), URLEncoder.encode(EQUIPMENT)), stockCategory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, "newEquipment");
    }
    public NewDtos getNewEquipments(String stockCategory){
        NewDtos newEquipments;
        File newEquipmentsFile = getNewEquipmentFile(stockCategory);
        if (!newEquipmentsFile.exists()){
            newEquipments = new NewDtos();
        }else {
            try {
                FileInputStream fileIn = new FileInputStream(newEquipmentsFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                newEquipments = (NewDtos) in.readObject();
                in.close();
                fileIn.close();
            } catch (ClassNotFoundException e) {
                Log.e(TAG, e.getMessage(), e);
                newEquipments = new NewDtos();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
                newEquipments = new NewDtos();
            }
            if (newEquipments.getNewEquiments() == null) {
                newEquipments.setNewEquiments(new TreeMap<String, List<NewDto>>());
            }
        }
        return newEquipments;
    }
    public void saveNewEquipment( String stockCategory,String repairType, String serial_number, String device, String make, String model,String supplier) throws IOException {
        NewDtos s = getNewEquipments(stockCategory);
        if (!s.getNewEquiments().containsKey(repairType)){
            s.getNewEquiments().put(repairType, new ArrayList<NewDto>());
        }
        s.getNewEquiments().get(repairType).add(new NewDto(serial_number,device,make,model,supplier));
        File newEquipmentsFile = getNewEquipmentFile(stockCategory);
        if (!newEquipmentsFile.exists()){
            newEquipmentsFile.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(newEquipmentsFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();

    }
    public void clearCache(String stockCategory) throws IOException, ClassNotFoundException{
        File cache = getNewEquipmentFile(stockCategory);
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
