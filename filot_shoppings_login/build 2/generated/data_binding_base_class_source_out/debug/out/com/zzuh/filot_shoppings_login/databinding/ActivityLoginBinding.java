// Generated by view binder compiler. Do not edit!
package com.zzuh.filot_shoppings_login.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.zzuh.filot_shoppings_login.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatImageButton backBtn;

  @NonNull
  public final AppCompatEditText emailEt;

  @NonNull
  public final TextInputLayout etPasswordLayout;

  @NonNull
  public final TextView findTv;

  @NonNull
  public final TextView joinTv;

  @NonNull
  public final AppCompatButton loginBtn;

  @NonNull
  public final TextInputEditText passwordEt;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatImageButton backBtn, @NonNull AppCompatEditText emailEt,
      @NonNull TextInputLayout etPasswordLayout, @NonNull TextView findTv, @NonNull TextView joinTv,
      @NonNull AppCompatButton loginBtn, @NonNull TextInputEditText passwordEt) {
    this.rootView = rootView;
    this.backBtn = backBtn;
    this.emailEt = emailEt;
    this.etPasswordLayout = etPasswordLayout;
    this.findTv = findTv;
    this.joinTv = joinTv;
    this.loginBtn = loginBtn;
    this.passwordEt = passwordEt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back_btn;
      AppCompatImageButton backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.email_et;
      AppCompatEditText emailEt = ViewBindings.findChildViewById(rootView, id);
      if (emailEt == null) {
        break missingId;
      }

      id = R.id.etPasswordLayout;
      TextInputLayout etPasswordLayout = ViewBindings.findChildViewById(rootView, id);
      if (etPasswordLayout == null) {
        break missingId;
      }

      id = R.id.find_tv;
      TextView findTv = ViewBindings.findChildViewById(rootView, id);
      if (findTv == null) {
        break missingId;
      }

      id = R.id.join_tv;
      TextView joinTv = ViewBindings.findChildViewById(rootView, id);
      if (joinTv == null) {
        break missingId;
      }

      id = R.id.login_btn;
      AppCompatButton loginBtn = ViewBindings.findChildViewById(rootView, id);
      if (loginBtn == null) {
        break missingId;
      }

      id = R.id.password_et;
      TextInputEditText passwordEt = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, backBtn, emailEt,
          etPasswordLayout, findTv, joinTv, loginBtn, passwordEt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
