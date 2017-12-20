/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.implementation.parsers;

import android.util.Pair;

import org.json.JSONObject;

import tv.accedo.one.sdk.implementation.utils.net.restclient.Response.ThrowingParser;
import tv.accedo.one.sdk.model.AccedoOneException;
import tv.accedo.one.sdk.model.AccedoOneException.StatusCode;
import tv.accedo.one.sdk.model.LogLevel;
import tv.accedo.one.sdk.implementation.utils.net.restclient.Response;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public class LogLevelParser implements ThrowingParser<Response, Pair<LogLevel, Long>, AccedoOneException> {
    private long logLevelInvalidation;

    public LogLevelParser(long logLevelInvalidation) {
        this.logLevelInvalidation = logLevelInvalidation;
    }

    @Override
    public Pair<LogLevel, Long> parse(Response response) throws AccedoOneException {
        try {
            JSONObject jsonObject = new JSONObject(response.getText());
            LogLevel logLevel = LogLevel.valueOf(jsonObject.getString("logLevel"));
            long expiration = response.getServerTime() + logLevelInvalidation;

            return new Pair<LogLevel, Long>(logLevel, expiration);
        } catch (Exception e) {
            throw new AccedoOneException(StatusCode.INVALID_RESPONSE, e);
        }
    }
}