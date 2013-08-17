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
public class NewDtoManager extends Manager{
    private static final String TAG = NewDtoManager.class.getName();

    public NewDtoManager(CeECOApplication application){
        super(application);
    }
    public NewDtos getNewEquipments(String stockCategory){
        try {
            return (NewDtos)getItem(stockCategory,"newEquipment",NewDtos.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveNewEquipment( String stockCategory,String repairType, String serial_number, String device, String make, String model,String supplier) throws IOException {
        NewDtos s = getNewEquipments(stockCategory);
        if (!s.getNewEquiments().containsKey(repairType)){
            s.getNewEquiments().put(repairType, new ArrayList<NewDto>());
        }
        s.getNewEquiments().get(repairType).add(new NewDto(serial_number,device,make,model,supplier));
        File newEquipmentsFile = getFile(stockCategory, "newEquipment");
        if (!newEquipmentsFile.exists()){
            newEquipmentsFile.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(newEquipmentsFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();

    }
    public void clearCache(String stockCategory){
        clearCache(stockCategory);
    }
}
