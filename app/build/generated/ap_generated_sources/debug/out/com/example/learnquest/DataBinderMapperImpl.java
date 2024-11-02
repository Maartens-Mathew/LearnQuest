package com.example.learnquest;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.learnquest.databinding.ActivityDashboardBindingImpl;
import com.example.learnquest.databinding.ActivityGroupViewBindingImpl;
import com.example.learnquest.databinding.ActivityRegisterStudentBindingImpl;
import com.example.learnquest.databinding.FragmentManageGroupsBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYDASHBOARD = 1;

  private static final int LAYOUT_ACTIVITYGROUPVIEW = 2;

  private static final int LAYOUT_ACTIVITYREGISTERSTUDENT = 3;

  private static final int LAYOUT_FRAGMENTMANAGEGROUPS = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.learnquest.R.layout.activity_dashboard, LAYOUT_ACTIVITYDASHBOARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.learnquest.R.layout.activity_group_view, LAYOUT_ACTIVITYGROUPVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.learnquest.R.layout.activity_register_student, LAYOUT_ACTIVITYREGISTERSTUDENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.learnquest.R.layout.fragment_manage_groups, LAYOUT_FRAGMENTMANAGEGROUPS);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYDASHBOARD: {
          if ("layout/activity_dashboard_0".equals(tag)) {
            return new ActivityDashboardBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_dashboard is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPVIEW: {
          if ("layout/activity_group_view_0".equals(tag)) {
            return new ActivityGroupViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_view is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYREGISTERSTUDENT: {
          if ("layout/activity_register_student_0".equals(tag)) {
            return new ActivityRegisterStudentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_register_student is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMANAGEGROUPS: {
          if ("layout/fragment_manage_groups_0".equals(tag)) {
            return new FragmentManageGroupsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_manage_groups is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "user");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_dashboard_0", com.example.learnquest.R.layout.activity_dashboard);
      sKeys.put("layout/activity_group_view_0", com.example.learnquest.R.layout.activity_group_view);
      sKeys.put("layout/activity_register_student_0", com.example.learnquest.R.layout.activity_register_student);
      sKeys.put("layout/fragment_manage_groups_0", com.example.learnquest.R.layout.fragment_manage_groups);
    }
  }
}
