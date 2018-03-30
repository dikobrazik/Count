package com.weblancer.dikobrazzz.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Table> tables;
    private DatabaseAdapter adapter;
    private View v;
    private EditText textBox;
    private TextView tv;

    DataAdapter(Context context, List<Table> tables) {
        this.tables = tables;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ricycler_item_list, parent, false);
        adapter = new DatabaseAdapter(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder holder, final int position) {
        holder.position.setText(String.valueOf(position + 1));
        holder.firstTime.setText(tables.get(position).getFirstClock());
        holder.secondTime.setText(tables.get(position).getSecondClock());
        holder.len.setText(tables.get(position).getFirstTimer());
        holder.inter.setText(tables.get(position).getSecondTimer());
        adapter.open();
        holder.note.setVisibility(View.VISIBLE);
        holder.note.setText(String.valueOf(adapter.getNote(tables.get(position).getId())));
        adapter.close();
        if(holder.note.getText().equals("")){
            holder.note.setVisibility(View.GONE);
        }
        holder.notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View contView = view;
                final PopupMenu menu = new PopupMenu(view.getContext(), view);
                menu.inflate(R.menu.popupmenu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.noteup:
                                v = inflater.inflate(R.layout.note, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contView.getRootView().getContext());
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setView(v);
                                textBox = (EditText) v.findViewById(R.id.textBox);
                                tv = (TextView) v.findViewById(R.id.note);
                                adapter.open();
                                textBox.setText(adapter.getNote(tables.get(position).getId()));
                                tv.setText(adapter.getNote(tables.get(position).getId()));
                                adapter.close();
                                alertDialogBuilder.setPositiveButton("Сохранить",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                tables.get(position).setNotes(String.valueOf(textBox.getText().toString()));
                                                adapter.open();
                                                if(adapter.update(tables.get(position)) != 0){
                                                    Toast.makeText(contView.getContext(), "Заметка успешно добавлена!", Toast.LENGTH_SHORT).show();
                                                }
                                                adapter.close();
                                                holder.note.setVisibility(View.VISIBLE);
                                                holder.note.setText(textBox.getText().toString());
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("Отмена",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                return true;
                            case R.id.delete:
                                adapter.open();
                                adapter.delete(tables.get(position).getId());
                                tables = adapter.getUsers();
                                notifyDataSetChanged();
                                adapter.close();
                                return true;
                            default:
                                return false;
                    }
                };
                });
                menu.show();
            }

        });
        int first = Integer.parseInt(tables.get(position).getFirstTimer().split(":")[0])*60 + Integer.parseInt(tables.get(position).getFirstTimer().split(":")[1]);
        int second = Integer.parseInt(tables.get(position).getSecondTimer().split(":")[0])*60 + Integer.parseInt(tables.get(position).getSecondTimer().split(":")[1]);
         if(first>60 && (second-first)<300){
             holder.status.setVisibility(View.VISIBLE);
             holder.status.setText("Пора ехать в родильный дом!");
         }
         else if(first>=300){
             holder.status.setVisibility(View.VISIBLE);
             holder.status.setText("Пора ехать в родильный дом!");
         }
    }



    @Override
    public int getItemCount() {
        return tables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView position, firstTime, secondTime, inter, len, status, note;
        final ImageButton notes;
        ViewHolder(View view){
            super(view);
            position = (TextView) view.findViewById(R.id.position);
            firstTime = (TextView) view.findViewById(R.id.firstTime);
            secondTime = (TextView) view.findViewById(R.id.secondTime);
            inter = (TextView) view.findViewById(R.id.inter);
            len = (TextView) view.findViewById(R.id.len);
            notes = (ImageButton) view.findViewById(R.id.notes);
            status = (TextView) view.findViewById(R.id.status);
            note = (TextView) view.findViewById(R.id.listNote);
        }
    }
}