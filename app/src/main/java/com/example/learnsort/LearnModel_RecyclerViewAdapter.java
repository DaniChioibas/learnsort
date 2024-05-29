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

public class LearnModel_RecyclerViewAdapter extends RecyclerView.Adapter<LearnModel_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<QuizModel> quizModels;

    public LearnModel_RecyclerViewAdapter(Context context, ArrayList<QuizModel> quizModels)
    {
        this.context=context;
        this.quizModels=quizModels;
    }

    @NonNull
    @Override
    public LearnModel_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_view_row2,parent,false);
        return new LearnModel_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnModel_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewName.setText(quizModels.get(position).getTitle());
        holder.textViewDesc.setText(quizModels.get(position).getSubtitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizModel clickedQuiz = quizModels.get(holder.getAdapterPosition());
                Context context= holder.itemView.getContext();

                Intent intent = new Intent(context,LessonActivity.class);
                intent.putExtra("lessonID", clickedQuiz.getId());
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

        }
    }
}
