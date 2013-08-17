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
public class StockManager {
    private static final String TAG = StockManager.class.getName();
    private static final String EQUIPMENT = "equipment";
    private CeECOApplication application;
    public StockManager(CeECOApplication application){
        this.application = application;
    }
    public File getStocksFile(String stockCategory) {
        File dir = new File(new File(application.getFilesDir(), URLEncoder.encode(EQUIPMENT)), stockCategory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, "stocks");
    }
    public Stocks getStocks(String stockCategory){
        Stocks stocks;
        File stocksFile = getStocksFile(stockCategory);
        if (!stocksFile.exists()){
            stocks = new Stocks();
        }else {
            try {
                FileInputStream fileIn = new FileInputStream(stocksFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                stocks = (Stocks) in.readObject();
                in.close();
                fileIn.close();
            } catch (ClassNotFoundException e) {
                Log.e(TAG, e.getMessage(), e);
                stocks = new Stocks();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
                stocks = new Stocks();
            }
            if (stocks.getStocks() == null) {
                stocks.setStocks(new TreeMap<String, List<Stock>>());
            }
        }
        return stocks;
    }
    public void saveStock( String stockCategory,String repairType, String part_number, String description, int quantity, String amount) throws IOException {
        Stocks s = getStocks(stockCategory);
        if (!s.getStocks().containsKey(repairType)){
            s.getStocks().put(repairType,new ArrayList<Stock>());
        }
        s.getStocks().get(repairType).add(new Stock(part_number,description,quantity,amount));
        File stockFile = getStocksFile(stockCategory);
        if (!stockFile.exists()){
            stockFile.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(stockFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();

    }
    public void clearCache(String stockCategory) throws IOException, ClassNotFoundException{
        File cache = getStocksFile(stockCategory);
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
