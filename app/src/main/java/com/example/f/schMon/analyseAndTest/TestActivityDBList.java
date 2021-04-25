/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.model.DAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class TestActivityDBList extends AppCompatActivity {
    Context context = TestActivityDBList.this;
    private static final String TAG = TestActivityDBList.class.getName();
    @BindView(R.id.dbListRecycView)
    RecyclerView recyclerView;
    SQLiteDatabase db;
    List<TableInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dblist);
        ButterKnife.bind(this);

        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        list = getAllTableNames();
        list = removeMetaTablesFromList(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new DBListAdapter(context, list));
    }


    //====================================methods=====================================================================================
    List<TableInfo> getAllTableNames() {
        String table;
        TableInfo tableInfo;
        List<TableInfo> tableInfoList = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                table = c.getString(0);
                if (DAO.isTableEmpty(db, table))
                    tableInfo = new TableInfo(table, "Empty");
                else tableInfo = new TableInfo(table, "Data Exists");
                tableInfoList.add(tableInfo);
                c.moveToNext();
            }
        }
        return tableInfoList;
    }

    List<TableInfo> removeMetaTablesFromList(List<TableInfo> list) {
        String[] metaTables = new String[]{"sqlite_sequence", "android_metadata"};

        for (int j = 0; j < metaTables.length; j++) {
            String table = metaTables[j];
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).tableName.equalsIgnoreCase(table))
                    list.remove(i);
            }
        }

        return list;
    }
}

class TableInfo {
    public String id;
    public String tableName;
    public String tableStatus;

    public TableInfo(String tableName, String tableStatus) {
        this.tableName = tableName;
        this.tableStatus = tableStatus;
    }

    public TableInfo(TableInfo other) {
        this.id = other.id;
        this.tableName = other.tableName;
        this.tableStatus = other.tableStatus;
    }

    public TableInfo() {
    }
}

class DBListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getName();
    private Context context;
    List<TableInfo> list;
    private ViewHolder viewHolder;
    SQLiteDatabase db;
    String tabelStatus;

    public DBListAdapter(Context context, List<TableInfo> list) {
        this.context = context;
        this.list = list;
        db = Global.getDB_with_nativeWay_writable(Global.dbName);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater LInflater = LayoutInflater.from(context);
        View row = LInflater.inflate(R.layout.row_db_list_test, parent, false);
        viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int i) {
        ((ViewHolder) h).tableName.setText(list.get(i).tableName + "\n" + list.get(i).tableStatus);
        ((ViewHolder) h).tableDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(context, list.get(i).tableName + " Table Deleted").show();
                delTable(list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //============================== viewholder ==========================================================================================
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.table)
        public TextView tableName;
        @BindView(R.id.tableDel)
        public Button tableDel;
        @BindView(R.id.tableShow)
        public Button tableShow;


        public ViewHolder(View row) {
            super(row);
            ButterKnife.bind(this, row);
        }
    }


    //=============================Methods============================================================================================
    void delTable(TableInfo tableInfo) {
        DAO.deleteDataFromTable(db, tableInfo.tableName);
        tableInfo.tableStatus="Empty";
        TableInfo temp = new TableInfo(tableInfo);
        list.remove(tableInfo);
        list.add(tableInfo);
        notifyDataSetChanged();
    }
}