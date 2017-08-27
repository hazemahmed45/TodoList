package com.example.hazem.todolist.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hazem.todolist.Data.TodoContract;
import com.example.hazem.todolist.R;

/**
 * Created by Hazem on 8/26/2017.
 */

public class TodoAdapter extends CursorAdapter{
    /**
     * @param context
     * @param c
     * @deprecated
     */
    public TodoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view=null;
        TodoViewHolder holder;

        if(view==null)
        {
            view=LayoutInflater.from(context).inflate(R.layout.item_todo,viewGroup,false);
            holder=new TodoViewHolder(view);
            view.setTag(holder);
        }

        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TodoViewHolder holder= (TodoViewHolder) view.getTag();
        holder.title.setText(cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_TITLE)));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI,getItemId(cursor.getPosition()));
                context.getContentResolver().delete(uri,null,null);
            }
        });

    }
    class TodoViewHolder
    {
        ImageView delete ;
        TextView title ;
        public TodoViewHolder(View view) {
            delete = (ImageView) view.findViewById(R.id.iv_item_todo_delete);
            title = (TextView) view.findViewById(R.id.tv_item_todo_title);
        }
    }
}
