package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.AnalyzePDFActivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.studyResource.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder {
        // Cached references to views.
        public View loLeft;
        public TextView txtHandleLeft;
        public TextView txtTimeStampLeft;
        public TextView txtMessageLeft;

        public View loRight;
        public TextView txtHandleRight;
        public TextView txtTimeStampRight;
        public TextView txtMessageRight;

        public TextView txtLogMessage;

        // Data to display.
        public Message message;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get cached references.
            loLeft = itemView.findViewById(R.id.loLeft);

            txtMessageLeft = itemView.findViewById(R.id.txtMessageLeft);

            loRight = itemView.findViewById(R.id.loRight);

            txtMessageRight = itemView.findViewById(R.id.txtMessageRight);

            txtLogMessage = itemView.findViewById(R.id.txtLogMessage);
        }

        /**
         * Sets the text in the relevant text views based on
         * who sent the message.
         * @param message The message to display.
         */
        public void setMessage(Message message) {
            // Cache the message to display.
            this.message = message;
            
            if (message.owner == Message.Owner.ME) {
                // Right message.
                loLeft.setVisibility(View.GONE);
                loRight.setVisibility(View.VISIBLE);
                txtLogMessage.setVisibility(View.GONE);

                
                txtMessageRight.setText(message.message);

            } else {
                // Left message.
                loLeft.setVisibility(View.VISIBLE);
                loRight.setVisibility(View.GONE);
                txtLogMessage.setVisibility(View.GONE);

              
                txtMessageLeft.setText(message.message);
            }
        }
    }
