package com.htm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htm.dto.Stocks;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/09/15.
 */
public class DescriptionActivity extends BaseActivity {
    final static String TAG = PartNumberActivity.class.getName();
    private ListView detialList;
    private EquipmentDetailAdapter equipmentDetailAdapter;
    private double totalStocks = 0;
    private List<Stocks.Stock> stockList = new LinkedList<Stocks.Stock>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_view);
        calculateTotal();
        loadStocks();
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
            if (stock.getDescription().equalsIgnoreCase(getIntent().getStringExtra("description"))){
                totalStocks = totalStocks + Double.valueOf(stock.getAmount().split(" ")[1]);
            }
        }
    }

    private class EquipmentDetailAdapter extends BaseAdapter {

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
                for (Stocks.Stock stock:stocks){
                    if (stock.getDescription().equalsIgnoreCase(getIntent().getStringExtra("description"))){
                        stockList.add(stock);
                    }
                }
                equipmentDetailAdapter.notifyDataSetChanged();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}