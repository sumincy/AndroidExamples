package com.yung.android.examples.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.yung.android.common.entity.PageItem;
import com.yung.android.common.ui.adapter.PageItemsAdapter;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.examples.R;
import com.yung.android.examples.databinding.ActivityMainBinding;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private PageItemsAdapter mainListAdapter;

    private final String TAG = "examples";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        initViews();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvMain.setLayoutManager(linearLayoutManager);

        List<PageItem> pageItems = PageItem.getPageItems(getApplicationContext(), "main_example.json");

        mainListAdapter = new PageItemsAdapter(this, pageItems);
        binding.rcvMain.setAdapter(mainListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyFloat.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EasyFloat.hide(TAG);
        EasyFloat.dismiss();
        Logger.getInstance().hideLogger();
    }
}