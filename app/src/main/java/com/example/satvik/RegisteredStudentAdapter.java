package com.example.satvik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegisteredStudentAdapter extends RecyclerView.Adapter<RegisteredStudentAdapter.ViewHolder> {

    private List<RegisteredStudent> registeredStudentList;
    private Context context;

    public RegisteredStudentAdapter(List<RegisteredStudent> registeredStudentList, Context context) {
        this.registeredStudentList = registeredStudentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_student_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegisteredStudent student = registeredStudentList.get(position);
        holder.studentNameTextView.setText(student.getName());


    }

    @Override
    public int getItemCount() {
        return registeredStudentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTextView;

        public Button Vieww;

        public ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.student_name);
//            Vieww = itemView.findViewById(R.id.student_button);

        }
    }
}
