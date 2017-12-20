/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.implementation.parsers;

import org.json.JSONObject;

import tv.accedo.one.sdk.implementation.utils.net.restclient.Response.ThrowingParser;
import tv.accedo.one.sdk.model.AccedoOneException;
import tv.accedo.one.sdk.implementation.utils.net.restclient.Response;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public class JSONObjectParser implements ThrowingParser<Response, JSONObject, AccedoOneException> {
    @Override
    public JSONObject parse(Response input) throws AccedoOneException {
        return new JSONObjectByteParser().parse(input.getRawResponse());
    }
}