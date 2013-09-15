package com.htm;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.dto.Stocks;

import java.util.List;

/**
 * Created by yanga on 2013/09/15.
 */
public class StockActivity extends BaseActivity {
    final static String TAG = StockActivity.class.getName();
    private ListView detialList;
    private EquipmentDetailAdapter equipmentDetailAdapter;
    private ProgressDialog progressDialog;
    private double totalStocks = 0;
    private List<Stocks.Stock> stockList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_view);
        calculateTotal();
        loadStocks();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading stocks...");
        progressDialog.show();
        equipmentDetailAdapter = new EquipmentDetailAdapter();
        View footer = getLayoutInflater().inflate(R.layout.stocks_footer, null);
        View header = getLayoutInflater().inflate(R.layout.stock_list_header,null);
        ((TextView)footer.findViewById(R.id.txt_quantity_footer)).setText("R "+String.valueOf(totalStocks));
        detialList = (ListView) findViewById(R.id.detail_list);
        detialList.addHeaderView(header);
        detialList.addFooterView(footer);
        detialList.setAdapter(equipmentDetailAdapter);
    }

    private void calculateTotal() {
        for (Stocks.Stock stock:getStockManager().getStocks("newStocks").getStocks().get("commissioned")){
            totalStocks = totalStocks + Double.valueOf(stock.getAmount().split(" ")[1]);
        }
    }

    private class EquipmentDetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return stockList!=null?stockList.size():0;
        }

        @Override
        public Stocks.Stock getItem(int i) {
            return stockList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.detail_view,null);
            }
            progressDialog.dismiss();
            ((TextView)view.findViewById(R.id.txt_part_number)).setText(getItem(i).getPartNumber());
            ((TextView)view.findViewById(R.id.txt_description)).setText(getItem(i).getDescription());
            ((TextView)view.findViewById(R.id.txt_quantity)).setText(String.valueOf(getItem(i).getQuantity()));
            ((TextView)view.findViewById(R.id.txt_amount)).setText(getItem(i).getAmount());
            return view;
        }
    }
    private void loadStocks(){
        new AsyncTask<Void, Void, List<Stocks.Stock>>() {
            @Override
            protected List<Stocks.Stock> doInBackground(Void... voids) {
                return getStockManager().getStocks("newStocks").getStocks().get("commissioned");
            }

            @Override
            protected void onPostExecute(List<Stocks.Stock> stocks) {
                progressDialog.dismiss();
                stockList = stocks;
                equipmentDetailAdapter.notifyDataSetChanged();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}