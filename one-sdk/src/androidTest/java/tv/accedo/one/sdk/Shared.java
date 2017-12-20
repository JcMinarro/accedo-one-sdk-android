/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

public class Shared {
    public static final String API_URL = "https://appgrid-api.cloud.accedo.tv";
    public static final String APP_ID = "59bbbe991de1c4000dfff803";
    public static final String DEVICE_ID = "test";

    public static Context getContext(){
        return InstrumentationRegistry.getTargetContext();
    }
}