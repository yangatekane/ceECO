package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.Stock;
import com.htm.dto.Stocks;

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
 * Created by yanga on 2013/08/14.
 */
public class StockManager extends Manager{
    private static final String TAG = StockManager.class.getName();

    public StockManager(CeECOApplication application) {
        super(application);
    }


    public void saveStock( String stockCategory,String repairType, String part_number, String description, int quantity, String amount) throws IOException, InstantiationException, IllegalAccessException {
        Stocks s = (Stocks) getItem(stockCategory, "stocks", Stocks.class);
        if (!s.getStocks().containsKey(repairType)){
            s.getStocks().put(repairType,new ArrayList<Stock>());
        }
        s.getStocks().get(repairType).add(new Stock(part_number,description,quantity,amount));
        File stockFile = getFile(stockCategory, "stocks");
        if (!stockFile.exists()){
            stockFile.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(stockFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();

    }
    public Stocks getStocks(String stockCategory){
        try {
            return (Stocks) getItem(stockCategory, "stocks", Stocks.class);
        } catch (IllegalAccessException e) {
            Log.e(TAG,e.getMessage(),e);
        } catch (InstantiationException e) {
            Log.e(TAG,e.getMessage(),e);
        }
        return null;
    }
    public void clearCache(String stockCategory){
        clearCache(stockCategory);
    }
}
