package com.example.learnquest.GroupStuff;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class GroupDiffUtil extends DiffUtil.Callback {
    List<Group> oldList;
    List<Group> newList;

    public GroupDiffUtil(List<Group> oldList, List<Group> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldList.get(oldItemPosition).equals(newList.get(newItemPosition)));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Group oldItem = oldList.get(oldItemPosition);
        Group newItem = newList.get(newItemPosition);
        return (oldItem.topic.equals(newItem.topic) && oldItem.description.equals(newItem.description)
                && oldItem.groupType.equals(newItem.groupType));
    }
}
