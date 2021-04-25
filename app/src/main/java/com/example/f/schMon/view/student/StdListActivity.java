/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.student;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.MessageEvent;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.Student;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.school.SchoolProfileActivity;
import com.example.f.schMon.view.sync_mir.SyncSynchronous;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class StdListActivity extends AppCompatActivity {
    private static final String TAG = StdListActivity.class.getName();
    private Context context = StdListActivity.this;
    private Student[] stdList;
    private Student[] stdListSearch;
    private List<Student> stdListSearchL;
    private int stdListLenth;
    private SyncSynchronous syncSynchronous = new SyncSynchronous(context);
    private SQLiteDatabase db;
    private String schID;
    private Snackbar snackbar;
    Intent intent;
    @BindView(R.id.listViewStdSearch)
    ListView listview;
    @BindView(R.id.searchET)
    EditText searchET;
    @BindView(R.id.searchBtn)
    ImageButton searchBtn;
    StdListAdapter adapter;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.noDataTV)
    TextView noDataTV;
    @BindView(R.id.searchContainer)
    View searchContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //___________________Start option menu______________________________________


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);//Menu Resource, Menu
        return true;
    }

    /**
     * hiding irrelevant option menu for this Activity
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            menu.findItem(R.id.sync_menu).setVisible(true);
            menu.findItem(R.id.home_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_menu:
                if (H.isInternetConnected(context)) {
                    if (!syncStudentThread.isAlive()) {
                        Toasty.info(context, "Sync Started.", Toast.LENGTH_LONG).show();
                        noDataTV.setText(Global.syncRunningDontClose);
                        pb.setVisibility(View.VISIBLE);
                        snackbar = Snackbar.make(noDataTV, Global.syncRunningDontClose, Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                        syncStudentThread.start();
                    }
                } else Toasty.error(context, "No Internet.Try Again").show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//___________________End option menu______________________________________


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {
            super.onBackPressed();
            Intent intentSchProf = new Intent(context, SchoolProfileActivity.class);
            intentSchProf.putExtra("schID", schID);
            startActivity(intentSchProf);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_list);
        ButterKnife.bind(this);
        //app bar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        //get writable db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);


        H.setFont(context, new View[]{noDataTV});
        //___________________set data___________________

        pb.setVisibility(View.GONE);

       intent = getIntent();
        schID = intent.getStringExtra("schID");

        if (DAO.isTableEmpty(db, DB.students.table)) {
            searchContainer.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            noDataTV.setVisibility(View.VISIBLE);

        } else {
            searchContainer.setVisibility(View.VISIBLE);
            listview.setVisibility(View.VISIBLE);
            noDataTV.setVisibility(View.GONE);
            loadData();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    //============== method ===========================================================================================================
    private Thread syncStudentThread = new Thread(new Runnable() {
        MessageEvent event = new MessageEvent();

        @Override
        public void run() {
            try {
                syncSynchronous.studentSync();
                event.setSuccess(true);
                EventBus.getDefault().post(event);
            } catch (Exception e) {
                Log.e(TAG, "exception", e);
                event.setSuccess(false);
                EventBus.getDefault().post(event);
            }
        }
    });


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSyncComplete(MessageEvent event) {
        pb.setVisibility(View.GONE);
        noDataTV.setVisibility(View.GONE);
        if (event.success == true) {
            Toasty.success(context, "Sync Completed", Toast.LENGTH_SHORT).show();
            snackbar.dismiss();
            H.restartActivity(context);
        } else {
            Toasty.error(context, "Not Synced Properly.Try Again Later", Toast.LENGTH_LONG).show();
            noDataTV.setText(View.VISIBLE);
            noDataTV.setText("Not Synced Properly.Try Again Later");
            snackbar.dismiss();
        }
    }


    void loadData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Student std;
                stdList = DAO.getStudentListFor_A_School(schID);
                stdListLenth = stdList.length;
                Arrays.sort(stdList);

                for (int i = 0; i < stdListLenth; i++) {
                    std = stdList[i];
                }

                adapter = new StdListAdapter(context, stdList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listview.setAdapter(adapter);
                pb.setVisibility(View.GONE);
                searchContainer.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);
                noDataTV.setVisibility(View.GONE);
            }
        }.execute();
    }

    public void onClickSearch(View vw) {
        String searchTerm = searchET.getText().toString();
        String tempName;
        stdListSearchL = new ArrayList<>();
        int index;
        for (int i = 0; i < stdListLenth; i++) {
            tempName = H.multipleSpacetoSingleSpace(stdList[i].name).toLowerCase();/***/
            index = tempName.indexOf(searchTerm.toLowerCase());
            if (index >= 0) {
                stdListSearchL.add(stdList[i]);
            }
        }
        stdListSearch = stdListSearchL.toArray(new Student[stdListSearchL.size()]);


       /* //Debug_______start
        for (int j = 0; j < stdListSearch.length; j++) {
            if (stdListSearch[j] != null)
                Log.d(TAG, stdListSearch[j].toString());
        }
        //Debug_______end*/

        if (stdListSearch.length == 0 || stdListSearch[0] == null) {
            Toast.makeText(this, "Not Found " + searchTerm, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, StdListSearchActivity.class);
            intent.putExtra("schID", schID);
           /* Bundle b = new Bundle();
            b.putParcelableArray("stdListSearch", stdListSearch);*/
            Temp.stdListSearch = stdListSearch;
            startActivity(intent);
            searchET.setText("");
        }
    }


    //======================= Unused ==================================================================================================

/*


    //================================ Start sync============================================================================================
    APInterface api = Client.getAPInterface();
    List<StdMdl> stdMdls;
    public String maxStd;


    public void studentSync() {
        pb.setVisibility(View.VISIBLE);
        api.getStdList(A.getU(), A.getP(), "0").enqueue(new Callback<StdMdlList>() {
            @Override
            public void onResponse(Call<StdMdlList> call, Response<StdMdlList> response) {
                A.calcDwnload(response.body().toString().length());
                maxStd = response.body().getTotal();
                api.getStdList(A.getU(), A.getP(), maxStd).enqueue(new Callback<StdMdlList>() {
                    @Override
                    public void onResponse(Call<StdMdlList> call, Response<StdMdlList> response) {
                        A.calcDwnload(response.body().toString().length());
                        List<StdMdl> t = response.body().getModel();
                        DAO.insertStudent_ListCall(t);
                        studentDetails(t);
                    }

                    @Override
                    public void onFailure(Call<StdMdlList> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<StdMdlList> call, Throwable t) {

            }
        });
    }

    int i;

    public void studentDetails(final List<StdMdl> stdMdls) {
        for (i = 0; i < stdMdls.size(); i++) {
            api.getStdByID(stdMdls.get(i).getId(), A.getU(), A.getP()).enqueue(new Callback<StdMdl_id_Res>() {
                @Override
                public void onResponse(Call<StdMdl_id_Res> call, Response<StdMdl_id_Res> response) {
                    A.calcDwnload(response.body().toString().length());
                    if (response.body().getModel() != null) {
                        StdMdl_id t = response.body().getModel();
                        DAO.insertAStudent_idCall(t.getId(), t.getInstituteId(), t.getRoll(), t.getTransferredSchoolId(),
                                t.getDateOfBirth(), t.getMotherName(), t.getGradeId());
                    }


                }

                @Override
                public void onFailure(Call<StdMdl_id_Res> call, Throwable t) {

                }
            });
        }


        if (i == stdMdls.size()) {
            Log.d(TAG, String.valueOf(i));
            H.restartActivity(context);
            pb.setVisibility(View.GONE);
        }

    }

    //================================End sync============================================================================================
*/


}
