package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EqualSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing; // the space to set between items, in pixels

    public EqualSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Position of the item in the adapter
        int position = parent.getChildAdapterPosition(view);
        int spanCount = 1; // default for LinearLayoutManager
        boolean isGridLayout = parent.getLayoutManager() instanceof GridLayoutManager;

        if (isGridLayout) {
            spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        }

        // Calculate offsets
        int column = position % spanCount; // item column in the grid

        // Set spacing for each side
        outRect.left = spacing - column * spacing / spanCount;
        outRect.right = (column + 1) * spacing / spanCount;

        if (position < spanCount) { // top edge
            outRect.top = spacing;
        }
        outRect.bottom = spacing; // item bottom
    }
}
