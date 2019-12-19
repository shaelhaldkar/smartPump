package com.iot.smartpump.utils;

import android.content.Intent;

/**
 * @ Created by Neeraj on 11-05-2016.
 * @ 2016 CitySpidey All Rights Reserved.
 */
public interface OnActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
