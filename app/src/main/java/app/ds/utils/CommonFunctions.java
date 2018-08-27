package app.ds.utils;

import android.content.Context;
import android.widget.Toast;

public class CommonFunctions {
    private static String TAG = CommonFunctions.class.getSimpleName();

    // TODO : Common toast
    public static void showToast(Context context, String msg) {
        try {
            Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
