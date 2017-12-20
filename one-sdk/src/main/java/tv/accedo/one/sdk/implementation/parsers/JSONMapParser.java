/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.implementation.parsers;

import java.util.Map;

import tv.accedo.one.sdk.implementation.utils.net.restclient.Response.ThrowingParser;
import tv.accedo.one.sdk.model.AccedoOneException;
import tv.accedo.one.sdk.implementation.utils.net.restclient.Response;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public class JSONMapParser implements ThrowingParser<Response, Map<String, String>, AccedoOneException> {
    @Override
    public Map<String, String> parse(Response input) throws AccedoOneException {
        return new JSONMapByteParser().parse(input.getRawResponse());
    }
}