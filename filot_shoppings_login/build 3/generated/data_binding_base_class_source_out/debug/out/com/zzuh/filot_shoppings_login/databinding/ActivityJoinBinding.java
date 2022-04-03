// Generated by view binder compiler. Do not edit!
package com.zzuh.filot_shoppings_login.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.tabs.TabLayout;
import com.zzuh.filot_shoppings_login.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityJoinBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppCompatButton cancelBtn;

  @NonNull
  public final TextView categoryTitleTextView;

  @NonNull
  public final Toolbar headerToolbar;

  @NonNull
  public final AppCompatButton joinBtn;

  @NonNull
  public final FrameLayout joinFragmentLayout;

  @NonNull
  public final TabLayout joinTabLayout;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView txtError;

  private ActivityJoinBinding(@NonNull LinearLayout rootView, @NonNull AppCompatButton cancelBtn,
      @NonNull TextView categoryTitleTextView, @NonNull Toolbar headerToolbar,
      @NonNull AppCompatButton joinBtn, @NonNull FrameLayout joinFragmentLayout,
      @NonNull TabLayout joinTabLayout, @NonNull ProgressBar progressBar,
      @NonNull TextView txtError) {
    this.rootView = rootView;
    this.cancelBtn = cancelBtn;
    this.categoryTitleTextView = categoryTitleTextView;
    this.headerToolbar = headerToolbar;
    this.joinBtn = joinBtn;
    this.joinFragmentLayout = joinFragmentLayout;
    this.joinTabLayout = joinTabLayout;
    this.progressBar = progressBar;
    this.txtError = txtError;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityJoinBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityJoinBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_join, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityJoinBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cancel_btn;
      AppCompatButton cancelBtn = ViewBindings.findChildViewById(rootView, id);
      if (cancelBtn == null) {
        break missingId;
      }

      id = R.id.category_title_text_view;
      TextView categoryTitleTextView = ViewBindings.findChildViewById(rootView, id);
      if (categoryTitleTextView == null) {
        break missingId;
      }

      id = R.id.header_toolbar;
      Toolbar headerToolbar = ViewBindings.findChildViewById(rootView, id);
      if (headerToolbar == null) {
        break missingId;
      }

      id = R.id.join_btn;
      AppCompatButton joinBtn = ViewBindings.findChildViewById(rootView, id);
      if (joinBtn == null) {
        break missingId;
      }

      id = R.id.join_fragment_layout;
      FrameLayout joinFragmentLayout = ViewBindings.findChildViewById(rootView, id);
      if (joinFragmentLayout == null) {
        break missingId;
      }

      id = R.id.join_tab_layout;
      TabLayout joinTabLayout = ViewBindings.findChildViewById(rootView, id);
      if (joinTabLayout == null) {
        break missingId;
      }

      id = R.id.progress_bar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.txt_error;
      TextView txtError = ViewBindings.findChildViewById(rootView, id);
      if (txtError == null) {
        break missingId;
      }

      return new ActivityJoinBinding((LinearLayout) rootView, cancelBtn, categoryTitleTextView,
          headerToolbar, joinBtn, joinFragmentLayout, joinTabLayout, progressBar, txtError);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
