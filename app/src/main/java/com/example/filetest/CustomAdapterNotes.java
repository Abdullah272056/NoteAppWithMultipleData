package com.example.filetest;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class CustomAdapterNotes extends RecyclerView.Adapter<CustomAdapterNotes.MyViewHolder> {

    Context context;
    private List<NotesData> notesDataList;
    private NotesImageSelectListener listener;

    public CustomAdapterNotes(Context context, List<NotesData> notesDataList, NotesImageSelectListener listener) {
        this.context = context;
        this.notesDataList = notesDataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomAdapterNotes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.notes_recyclerview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterNotes.MyViewHolder holder, final int position) {

        holder.tv_title.setText(notesDataList.get(position).getTitle());
        holder.tv_des.setText(notesDataList.get(position).getDes());
        if (notesDataList.get(position).getUri()!=null){
            if (notesDataList.get(position).getUri().endsWith(".jpg")||notesDataList.get(position).getUri().endsWith(".png")){
                holder.imageView.setImageURI(Uri.parse(notesDataList.get(position).getUri()));
            }else if (notesDataList.get(position).getUri().endsWith(".pdf")){
                holder.imageView.setImageResource(R.drawable.ic_pdf_file_icon);
                //holder.imageView.setVisibility(View.GONE);
            }else {
                holder.imageView.setImageResource(R.drawable.ic_baseline_add_24);
            }


        }else {
            holder.layout_img_container.setVisibility(View.GONE);
        }


        holder.layout_text_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNotesItemSelected(notesDataList.get(position));
            }
        });
        holder.layout_img_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNotesItemImageSelected(notesDataList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return notesDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_des;
        ImageView imageView;
        LinearLayout layout_img_container,layout_text_container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_des=itemView.findViewById(R.id.tv_des);
            imageView=itemView.findViewById(R.id.imageView);
            layout_img_container=itemView.findViewById(R.id.layout_img_container);
            layout_text_container=itemView.findViewById(R.id.layout_text_container);

        }
    }


}
