package com.example.learnsort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuizModel_RecyclerViewAdapter extends RecyclerView.Adapter<QuizModel_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<QuizModel> quizModels;

    public QuizModel_RecyclerViewAdapter(Context context, ArrayList<QuizModel> quizModels)
    {
        this.context=context;
        this.quizModels=quizModels;
    }

    @NonNull
    @Override
    public QuizModel_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new QuizModel_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizModel_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewName.setText(quizModels.get(position).getTitle());
        holder.textViewDesc.setText(quizModels.get(position).getSubtitle());

    }

    @Override
    public int getItemCount() {
        return quizModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewDesc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
        }
    }
}
