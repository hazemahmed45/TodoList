package com.example.hazem.todolist.activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.hazem.todolist.Data.TodoContract;
import com.example.hazem.todolist.R;
import com.example.hazem.todolist.adapter.TodoAdapter;

public class Main extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int Loader_ID=0;
    private ListView listView;
    TodoAdapter adapter;
    View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_todolist);
        emptyView=findViewById(R.id.empty_view);


        Cursor cursor=getContentResolver().query(TodoContract.TodoEntry.CONTENT_URI,null,null,null,null);
        adapter=new TodoAdapter(this,cursor);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(Main.this,EditorScreen.class);
                intent.setData(ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI,id));
                startActivity(intent);
            }
        });
        listView.setEmptyView(emptyView);

        getSupportLoaderManager().initLoader(Loader_ID,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_todo:
            {
                Intent intent=new Intent(this,EditorScreen.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id)
        {
            case Loader_ID:
            {
                return new android.support.v4.content.CursorLoader(this, TodoContract.TodoEntry.CONTENT_URI,null,null,null,null);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
