package com.edward.stockmanagement.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.edward.stockmanagement.DATABASE.DB_INIT;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.GLOBAL.Global_Variables;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.R;
import com.edward.stockmanagement.VIEWS.Stock_Pager_Adapter;
import com.edward.stockmanagement.VIEWS.warning_item_view_dialog;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static boolean report_ready = false;
    public static Context m_Context;
    private Handler app_handler = new Handler();
    private Runnable stock_mTimer;
    public static ViewPager spPager;
    public static Stock_Pager_Adapter spPager_Adapter;

    public static ArrayList<String> warnings_list_items=new ArrayList<String>();
    public static ArrayAdapter<String> warnings_list_adapter;
    public static ListView warnings_list_view;

    public static Context get_M_Context() {
        return m_Context;
    }

    public static void set_M_Context(Context m_ontext) {
        MainActivity.m_Context = m_ontext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        set_M_Context(this.getApplicationContext());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        spPager = (ViewPager)findViewById(R.id.viewpager_stock);
        spPager_Adapter = new Stock_Pager_Adapter(getSupportFragmentManager());
        spPager.setAdapter(spPager_Adapter);
        Setpage(0);
        if (!DataBaseHandler.doesDatabaseExist(get_M_Context())) {
            DB_INIT db_init = new DB_INIT();
            db_init.init();
        }
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            Setpage(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Setpage(6);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_data_view) {
            Setpage(1);

        } else if (id == R.id.nav_edit) {
            Setpage(2);

        }  else if (id == R.id.nav_send) {
            if (report_ready){


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_SUBJECT, "Low Stock in Clinics");
                db = new DataBaseHandler(get_M_Context());
                int stock_count = db.get_stock_count();

                String message = "";
                for (int i = 0; i < stock_count;i++){
                    STOCK stock = db.get_stock(i + 1);
                    if (stock.getS_stock_count() < 5 ){
                        CLINIC clinic = db.get_clinic(stock.getS_clinic());
                        MEDICATION medication = db.get_medication(stock.getS_medication());
                        message += clinic.getC_name() + " is low on " + medication.getM_name() + " stock left : " + stock.getS_stock_count() + " \n";


                        Log.d("MESSAGE", message);
                        report_ready = true;
                    }
                    intent.putExtra(Intent.EXTRA_TEXT, message + " ");
                }


                startActivity(Intent.createChooser(intent, "Send Email"));
            }
            else{
                Toast.makeText(MainActivity.get_M_Context(),"No Items in the Stock Database is low",Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        stock_mTimer = new Runnable(){
            @Override
            public void run() {
                check_stock();
            }
        };
        app_handler.postDelayed(stock_mTimer,30000);
        //Update Warnings every 30 Seconds
    }
    private static  DataBaseHandler db;
    private static FragmentManager fragmentManager;
    public static void check_stock(){
        db = new DataBaseHandler(get_M_Context());
        int stock_count = db.get_stock_count();
        warnings_list_items.clear();
        int [] warning_list_array = new int[stock_count];
        int warning_count = 0;
        for (int i = 0; i < stock_count;i++){
            STOCK stock = db.get_stock(i + 1);
            if (stock.getS_stock_count() < 5 ){
                CLINIC clinic = db.get_clinic(stock.getS_clinic());
                MEDICATION medication = db.get_medication(stock.getS_medication());
                String message = clinic.getC_name() + " is low on " + medication.getM_name() + " stock left : " + stock.getS_stock_count();
                warnings_list_items.add(message);
                warning_list_array[warning_count] = stock.getS_id();
                warning_count++;
                Log.d("MESSAGE", message);
                report_ready = true;
            }
        }
        if (warnings_list_items.size() == 0){
            String message = "No Stock Issues";
            warnings_list_items.add(message);
            report_ready = false;
        }
        warnings_list_adapter.notifyDataSetChanged();
        db.close();
    }
    @Override
    public void onPause(){
        app_handler.removeCallbacks(stock_mTimer);
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
        app_handler.removeCallbacks(stock_mTimer);
    }

    public static void Setpage(int num){
        spPager.setCurrentItem(num);
    }
}

