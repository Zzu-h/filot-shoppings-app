// Generated by view binder compiler. Do not edit!
package com.zzuh.filot_shoppings.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.zzuh.filot_shoppings.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class DrawerCategoryListItemBinding implements ViewBinding {
  @NonNull
  private final TextView rootView;

  @NonNull
  public final TextView categoryTv;

  private DrawerCategoryListItemBinding(@NonNull TextView rootView, @NonNull TextView categoryTv) {
    this.rootView = rootView;
    this.categoryTv = categoryTv;
  }

  @Override
  @NonNull
  public TextView getRoot() {
    return rootView;
  }

  @NonNull
  public static DrawerCategoryListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DrawerCategoryListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.drawer_category_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DrawerCategoryListItemBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    TextView categoryTv = (TextView) rootView;

    return new DrawerCategoryListItemBinding((TextView) rootView, categoryTv);
  }
}
