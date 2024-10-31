package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.view.View;

import com.example.learnquest.model.group.Group;

public interface onHolderClick<T> {

    View.OnClickListener onClick(T item);

}
