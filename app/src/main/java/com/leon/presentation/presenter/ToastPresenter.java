package com.leon.presentation.presenter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kevin on 05/03/2016.
 */
public final class ToastPresenter {
  public static void makeToast(String text, Context context) {
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
  }
}
