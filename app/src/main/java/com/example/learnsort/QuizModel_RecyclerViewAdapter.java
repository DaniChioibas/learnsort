package com.example.learnsort;

import android.content.Context;
import android.content.Intent;
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
        holder.textViewTime.setText(quizModels.get(position).getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizModel clickedQuiz = quizModels.get(holder.getAdapterPosition());
                Context context= holder.itemView.getContext();
                Intent intent = new Intent(context,QuestionsActivity.class);
                intent.putExtra("quizID", clickedQuiz.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewDesc,textViewTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewTime = itemView.findViewById(R.id.textViewTime);

        }
    }
}
