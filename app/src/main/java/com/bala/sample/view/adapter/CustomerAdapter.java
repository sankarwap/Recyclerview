package com.bala.sample.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bala.sample.R;
import com.bala.sample.model.util.Customer_model;
import com.bala.sample.view.activity.UpdateActivity;
import com.bala.sample.view.activity.dataActivity;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.TodoViewHolder> {
    private ArrayList<Customer_model> mTodos;
    Context context;


    public CustomerAdapter(Context mcontext, ArrayList<Customer_model> data) {
        this.mTodos = data;
        this.context = mcontext;

    }


    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, final int position) {
        final Customer_model todo = mTodos.get(position);
        holder.mTvName.setText(todo.getName());
        holder.mTvDate.setText(todo.getPlace());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataActivity.customer_presenter.delete(mTodos.get(position).getId());
                dataActivity.dialog.show();
            }
        });
        holder.data_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to=new Intent(context, UpdateActivity.class);
                to.putExtra("name",todo.getName());
                to.putExtra("id",todo.getId());
                to.putExtra("phone",todo.getPhone());
//                to.putExtra("lat",todo.getLat());
                to.putExtra("lats",Double.parseDouble(todo.getLat()));
                to.putExtra("longs",Double.parseDouble(todo.getLongi()));
//                to.putExtra("long",todo.getLongi());
                to.putExtra("place",todo.getPlace());
                to.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(to);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvName;
        private final TextView mTvDate;
        RelativeLayout data_lay;
        Button del;

        TodoViewHolder(View itemView) {
            super(itemView);
            data_lay=itemView.findViewById(R.id.data_lay);
            mTvName = itemView.findViewById(R.id.tvName);
            mTvDate = itemView.findViewById(R.id.tvDate);
            del = itemView.findViewById(R.id.del);
        }

    }
}


