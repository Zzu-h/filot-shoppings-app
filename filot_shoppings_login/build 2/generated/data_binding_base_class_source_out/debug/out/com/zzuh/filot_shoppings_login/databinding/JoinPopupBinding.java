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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.zzuh.filot_shoppings_login.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class JoinPopupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton cancelBtn;

  @NonNull
  public final LinearLayout certificateCodeLayout;

  @NonNull
  public final AppCompatButton checkBtn;

  @NonNull
  public final LinearLayout joinFailed;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout loadingJoin;

  @NonNull
  public final AppCompatEditText nameEt;

  @NonNull
  public final AppCompatButton verifyCheckBtn;

  @NonNull
  public final LinearLayout verifyLayout;

  @NonNull
  public final AppCompatButton verifyResendBtn;

  private JoinPopupBinding(@NonNull ConstraintLayout rootView, @NonNull AppCompatButton cancelBtn,
      @NonNull LinearLayout certificateCodeLayout, @NonNull AppCompatButton checkBtn,
      @NonNull LinearLayout joinFailed, @NonNull LinearLayout linearLayout,
      @NonNull LinearLayout loadingJoin, @NonNull AppCompatEditText nameEt,
      @NonNull AppCompatButton verifyCheckBtn, @NonNull LinearLayout verifyLayout,
      @NonNull AppCompatButton verifyResendBtn) {
    this.rootView = rootView;
    this.cancelBtn = cancelBtn;
    this.certificateCodeLayout = certificateCodeLayout;
    this.checkBtn = checkBtn;
    this.joinFailed = joinFailed;
    this.linearLayout = linearLayout;
    this.loadingJoin = loadingJoin;
    this.nameEt = nameEt;
    this.verifyCheckBtn = verifyCheckBtn;
    this.verifyLayout = verifyLayout;
    this.verifyResendBtn = verifyResendBtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static JoinPopupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static JoinPopupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.join_popup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static JoinPopupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cancel_btn;
      AppCompatButton cancelBtn = ViewBindings.findChildViewById(rootView, id);
      if (cancelBtn == null) {
        break missingId;
      }

      id = R.id.certificate_code_layout;
      LinearLayout certificateCodeLayout = ViewBindings.findChildViewById(rootView, id);
      if (certificateCodeLayout == null) {
        break missingId;
      }

      id = R.id.check_btn;
      AppCompatButton checkBtn = ViewBindings.findChildViewById(rootView, id);
      if (checkBtn == null) {
        break missingId;
      }

      id = R.id.join_failed;
      LinearLayout joinFailed = ViewBindings.findChildViewById(rootView, id);
      if (joinFailed == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.loading_join;
      LinearLayout loadingJoin = ViewBindings.findChildViewById(rootView, id);
      if (loadingJoin == null) {
        break missingId;
      }

      id = R.id.name_et;
      AppCompatEditText nameEt = ViewBindings.findChildViewById(rootView, id);
      if (nameEt == null) {
        break missingId;
      }

      id = R.id.verify_check_btn;
      AppCompatButton verifyCheckBtn = ViewBindings.findChildViewById(rootView, id);
      if (verifyCheckBtn == null) {
        break missingId;
      }

      id = R.id.verify_layout;
      LinearLayout verifyLayout = ViewBindings.findChildViewById(rootView, id);
      if (verifyLayout == null) {
        break missingId;
      }

      id = R.id.verify_resend_btn;
      AppCompatButton verifyResendBtn = ViewBindings.findChildViewById(rootView, id);
      if (verifyResendBtn == null) {
        break missingId;
      }

      return new JoinPopupBinding((ConstraintLayout) rootView, cancelBtn, certificateCodeLayout,
          checkBtn, joinFailed, linearLayout, loadingJoin, nameEt, verifyCheckBtn, verifyLayout,
          verifyResendBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
