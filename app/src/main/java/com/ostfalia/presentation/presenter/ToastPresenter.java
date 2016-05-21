package com.ostfalia.presentation.presenter;

import android.content.Context;
import android.widget.Toast;

/**
 * Class to Present a toast in the view
 */
public final class ToastPresenter {
  /**
   * Presents a toast in the given context
   * @param text text of the toast
   * @param context context on which the toast is presented
   */
  public static void makeToast(String text, Context context) {
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
  }
}
