package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.studyResource.StudyResource;
import com.example.learnquest.model.studyResource.Temp.LeafNode;
import com.example.learnquest.model.studyResource.Temp.TreeNode;

public class ResourceViewHolder extends RecyclerView.ViewHolder{


    StudyResource studyResource;
    ImageView imageView;
    TextView txtName;
    TextView txtDataAdded;
    TextView txtPerson;

    TreeNode treeNode;

    public ResourceViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_icon);
        txtName = itemView.findViewById(R.id.txt_resourceName);
        txtDataAdded = itemView.findViewById(R.id.txt_DateAdded);
        txtPerson = itemView.findViewById(R.id.txt_person);

    }

    public void bind(TreeNode treeNode, OnNodeClickListener listener){
        this.treeNode = treeNode;
        itemView.setOnClickListener(listener.onNodeClick(treeNode));

        if (treeNode.isLeaf()) {
            studyResource = ((LeafNode)treeNode).getCargo();
            designFile();
        }else{
            designFolder();
        }

    }



    private void designFile() {
        txtName.setText(studyResource.getFileName());
        String fileExtension = studyResource.getFileType();
        setImageUI(fileExtension);

        txtDataAdded.setText(studyResource.getDateAdded());
        txtPerson.setText(studyResource.getPerson());

    }

    private void designFolder(){
        txtName.setText(treeNode.getName());

        setImageUI("folder");

        txtDataAdded.setText("");
        txtPerson.setText("");

    }

    public void setImageUI(String fileExtension){
        switch(fileExtension){
            case "jpg":
            case"jpeg":
            case "png":
            case"gif":
                imageView.setImageResource(R.drawable.image);
                break;
            case "pdf":
                imageView.setImageResource(R.drawable.pdf);
                break;
            case "mp4":
            case "mov":
            case "avi":
                imageView.setImageResource(R.drawable.video);
                break;
            case "docx":
            case "doc":
                imageView.setImageResource(R.drawable.word);
                break;
            case "pptx":
            case "ppt":
                imageView.setImageResource(R.drawable.powerpoint);
                break;
            case "xlsx":
            case "xls":
                imageView.setImageResource(R.drawable.spreadsheet);
                break;
            case "zip":
            case "rar":
                imageView.setImageResource(R.drawable.zip);
                break;
            case "folder":
                imageView.setImageResource(R.drawable.folder);
                break;
            default:
                imageView.setImageResource(R.drawable.text);
        }
    }


}
