/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.definition.async;

/**
 * Generic interface defined for cancellable operations, such as AsyncTasks, SafeAsyncTasks, CancellableAsyncTasks.
 *
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public interface Cancellable {
    public void cancel();
}
