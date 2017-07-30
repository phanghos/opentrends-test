package org.taitasciore.android.opentrendstest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by roberto on 30/07/17.
 */

public final class ActivityUtils {

    public static void launchBrowser(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
