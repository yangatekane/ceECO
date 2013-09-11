package com.htm.biz;

import android.util.Log;

import com.htm.CeECOApplication;
import com.htm.dto.Stocks.Stock;
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
        Stocks s = getStocks(stockCategory);
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
        Stocks stocks=new Stocks();
        File objectsFile = getFile(stockCategory,"stocks");
        if (!objectsFile.exists()){
            stocks = new Stocks();
        }else {
            try {
                FileInputStream fileIn = new FileInputStream(objectsFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                stocks = (Stocks)in.readObject();
                in.close();
                fileIn.close();

            } catch (ClassNotFoundException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        if (stocks.getStocks()==null){
            stocks.setStocks(new TreeMap<String, List<Stock>>());
        }
        return stocks;
    }
    public void saveJobs( String stockCategory,String repairType, String part_number, String description, int quantity, String amount) throws IOException, InstantiationException, IllegalAccessException {
        Stocks s = getStocks(stockCategory);
        if (!s.getStocks().containsKey(repairType)){
            s.getStocks().put(repairType,new ArrayList<Stock>());
        }
        s.getStocks().get(repairType).add(new Stock(part_number,description,quantity,amount));
        File stockFile = getFile(stockCategory, "jobs");
        if (!stockFile.exists()){
            stockFile.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(stockFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();

    }
    public Stocks getJobs(String stockCategory){
        Stocks stocks=new Stocks();
        File objectsFile = getFile(stockCategory,"jobs");
        if (!objectsFile.exists()){
            stocks = new Stocks();
        }else {
            try {
                FileInputStream fileIn = new FileInputStream(objectsFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                stocks = (Stocks)in.readObject();
                in.close();
                fileIn.close();

            } catch (ClassNotFoundException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        if (stocks.getStocks()==null){
            stocks.setStocks(new TreeMap<String, List<Stock>>());
        }
        return stocks;
    }
    public void clearCache(String stockCategory){
        clearCache(stockCategory);
    }
}
