package com.example.hazem.todolist.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.todolist.Data.TodoContract;
import com.example.hazem.todolist.R;

public class EditorScreen extends AppCompatActivity {

    private EditText Title;
    private EditText Desc;
    private Button Save;
    private boolean edit=false;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_screen);
        setIDs();

        if(getIntent()!=null && getIntent().getData()!=null)
        {
            Log.i("HERE","here");
            edit=true;
            getSupportActionBar().setTitle(getString(R.string.edit_todo));
            Save.setText(getString(R.string.edit));
            uri=getIntent().getData();

            Cursor cursor=getContentResolver().query(uri,null,null,null,null);

            int idIndex=cursor.getColumnIndex(TodoContract.TodoEntry._ID);
            int titleIndex=cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_TITLE);
            int descIndex=cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_DESC);
            while (cursor.moveToNext())
            {
                String title=cursor.getString(titleIndex);
                String desc=cursor.getString(descIndex);
                Title.setText(title);
                Desc.setText(desc);
            }
            cursor.close();
        }

        else
        {
            getSupportActionBar().setTitle(getString(R.string.new_Todo));
        }
        setEvents();
    }
    void setIDs()
    {
        this.Save = (Button) findViewById(R.id.btn_editor);
        this.Desc = (EditText) findViewById(R.id.et_editor_desc);
        this.Title = (EditText) findViewById(R.id.et_editor_title);
        uri=TodoContract.TodoEntry.CONTENT_URI;

    }
    void setEvents()
    {
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=Title.getText().toString().trim();
                String desc=Desc.getText().toString().trim();

                ContentValues values=new ContentValues();
                values.put(TodoContract.TodoEntry.COLUMN_TODO_TITLE,title);
                values.put(TodoContract.TodoEntry.COLUMN_TODO_DESC,desc);

                if(edit==true)
                {
                    int check=getContentResolver().update(uri,values,null,null);
                    if(check==0)
                    {
                        Toast.makeText(EditorScreen.this, "No todo was updated", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(EditorScreen.this, "Todo was updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Uri check=getContentResolver().insert(TodoContract.TodoEntry.CONTENT_URI,values);
                    if(check==null)
                    {
                        Toast.makeText(EditorScreen.this, "No todo was saved", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(EditorScreen.this, "Todo was saved successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
