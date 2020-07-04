package com.example.myprofilepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyView>{

    private OnItemClickListener mlistener;

    public void SetOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    ArrayList job,com;
    Context context;

    public Adapter(Context ct, ArrayList s1, ArrayList s2){

        context=ct;
        job= s1;
        com=s2;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.myview, parent, false);
        return new MyView(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.job_.setText(job.get(position).toString().trim());
        holder.com_.setText(com.get(position).toString().trim());

    }

    @Override
    public int getItemCount() {

        return job.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        TextView job_,com_;


        public MyView(@NonNull View itemView, final OnItemClickListener listener) {

            super(itemView);
            job_= itemView.findViewById(R.id.job_name);
            com_=itemView.findViewById(R.id.company_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        int position= getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }

                }
            });


        }
    }


}
