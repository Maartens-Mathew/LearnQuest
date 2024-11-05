package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.AnalyzePDFActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.studyResource.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    List<Message> messages;

    public MessageAdapter(){
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_message, parent, false);
        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.setMessage(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void add(Message message){
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void clear(){
        messages.clear();
        notifyDataSetChanged();
    }

    public void remove(){
        messages.remove(messages.size() - 1);
        notifyItemRemoved(messages.size());
    }
}
