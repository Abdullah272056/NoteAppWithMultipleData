package com.example.filetest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PdfViewHolder extends RecyclerView.ViewHolder {
   public TextView textViewName;
    public CardView container;
    public PdfViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName=itemView.findViewById(R.id.textView_Name);
        container=itemView.findViewById(R.id.container);

    }
}
