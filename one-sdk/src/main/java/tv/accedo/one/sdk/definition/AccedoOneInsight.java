/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.definition;

import tv.accedo.one.sdk.definition.async.Cancellable;
import tv.accedo.one.sdk.model.LogLevel;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public interface AccedoOneInsight {
    /**
     * Analytics app-start call.
     */
    Cancellable applicationStart();

    /**
     * Analytics app-quit call.
     */
    Cancellable applicationQuit();

    /**
     * Analytics app-quit call.
     * @param retentionSeconds time spent in the application in seconds.
     */
    Cancellable applicationQuit(int retentionSeconds);
}