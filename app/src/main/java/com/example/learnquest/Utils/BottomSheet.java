package com.example.learnquest.Utils;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learnquest.R;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment;

import java.util.List;

public class BottomSheet {

    public static class Builder{
        List<String> itemTitles;
        List<Class<?>> classes;
        Activity activity;
        int layout_menu;
        Boolean hasPermission;
        public Builder with(Activity activity){
            this.activity = activity;
            return this;
        }


        public Builder getItemTitles(String... titles){
            itemTitles = List.of(titles);
            return this;
        }

        public Builder andClasses(Class<?>... classes){
            this.classes = List.of(classes);
            return this;
        }

        public Builder hasPermission(Boolean hasPermission){
            this.hasPermission= hasPermission;
            return this;
        }

        public Builder andLastly_theLayout(int layout_menu){
            this.layout_menu = layout_menu;
            return this;

        }

        public BottomSheetMenuDialogFragment build(){
            return new BottomSheetMenuDialogFragment.Builder(activity)
                    .setSheet(layout_menu)
                    .setTitle("Options:")
                    .setListener(getListener(classes))
                    .create();
        }

        public BottomSheetListener getListener(List<Class<?>> classes){
            return new BottomSheetListener() {
                @Override
                public void onSheetShown(@NonNull BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, @Nullable Object o) {


                }

                @Override
                public void onSheetItemSelected(@NonNull BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, @NonNull MenuItem menuItem, @Nullable Object o) {

                    String title = itemTitles.get(menuItem.getOrder());
                    if (title.contentEquals(menuItem.getTitle())){
                        Intent intent = new Intent(activity, classes.get(menuItem.getOrder()));
                        activity.startActivity(intent);
                    }
                }

                @Override
                public void onSheetDismissed(@NonNull BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, @Nullable Object o, int i) {

                }


            };
        }
    }


}
