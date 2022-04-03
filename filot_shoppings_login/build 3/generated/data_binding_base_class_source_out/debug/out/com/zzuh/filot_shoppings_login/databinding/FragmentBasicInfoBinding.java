// Generated by view binder compiler. Do not edit!
package com.zzuh.filot_shoppings_login.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.zzuh.filot_shoppings_login.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentBasicInfoBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextInputEditText checkPasswordEt;

  @NonNull
  public final AppCompatButton emailBtn;

  @NonNull
  public final AppCompatEditText emailEt;

  @NonNull
  public final TextInputEditText passwordEt;

  private FragmentBasicInfoBinding(@NonNull LinearLayout rootView,
      @NonNull TextInputEditText checkPasswordEt, @NonNull AppCompatButton emailBtn,
      @NonNull AppCompatEditText emailEt, @NonNull TextInputEditText passwordEt) {
    this.rootView = rootView;
    this.checkPasswordEt = checkPasswordEt;
    this.emailBtn = emailBtn;
    this.emailEt = emailEt;
    this.passwordEt = passwordEt;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentBasicInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentBasicInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_basic_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentBasicInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.check_password_et;
      TextInputEditText checkPasswordEt = ViewBindings.findChildViewById(rootView, id);
      if (checkPasswordEt == null) {
        break missingId;
      }

      id = R.id.email_btn;
      AppCompatButton emailBtn = ViewBindings.findChildViewById(rootView, id);
      if (emailBtn == null) {
        break missingId;
      }

      id = R.id.email_et;
      AppCompatEditText emailEt = ViewBindings.findChildViewById(rootView, id);
      if (emailEt == null) {
        break missingId;
      }

      id = R.id.password_et;
      TextInputEditText passwordEt = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt == null) {
        break missingId;
      }

      return new FragmentBasicInfoBinding((LinearLayout) rootView, checkPasswordEt, emailBtn,
          emailEt, passwordEt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
