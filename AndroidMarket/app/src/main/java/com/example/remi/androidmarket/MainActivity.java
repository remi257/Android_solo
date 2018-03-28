package com.example.remi.androidmarket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditDialogFragment.OnFragmentInteractionListener {
    private String filename = "AMfile";
    MyRAdapter adapter;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                EditDialogFragment edf = EditDialogFragment.newInstance(new EditDialogFragment.OKClickAction() {
                    @Override
                    public void OnOKClick(String str) {
                        adapter.AddNewItem(str);
                    }
                });
                edf.show(fm, "tag");
            }
        });
        ArrayList<String> data = new ArrayList<String>();
        try{
            FileInputStream stream = openFileInput(filename);
            int c;
            String temp="";
            while( (c = stream.read()) != -1){
                if((char)c == '|')
                {
                    data.add(temp);
                    temp = "";
                    continue;
                }
                temp = temp + Character.toString((char)c);
            }

//string temp contains all the data of the file.
            stream.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }





        RecyclerView rview = (RecyclerView) findViewById(R.id.my_recycler_view);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder) {
                //int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlags);
            }
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.DeleteItem(viewHolder.getLayoutPosition());

            }

        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(rview);
        rview.setHasFixedSize(true);

        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setLongClickable(true);
        rview.setClickable(true);


        adapter = new MyRAdapter(data, new MyRAdapter.LongClickAction() {
            @Override
            public void OnLongClick(final int position, View v) {
//                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                EditDialogFragment edf = EditDialogFragment.newInstance(new EditDialogFragment.OKClickAction() {
                    @Override
                    public void OnOKClick(String str) {
                        adapter.EditItem(position, str);
                    }
                });
                edf.show(fm, "tag");


            }
        });


        rview.setAdapter(adapter);
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
    protected void onDestroy() {

        FileOutputStream outputStream;
        StringBuilder sb = new StringBuilder();

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            for (String s : adapter.data)
            {
                sb.append(s).append("|");
            }

            outputStream.write(sb.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}



