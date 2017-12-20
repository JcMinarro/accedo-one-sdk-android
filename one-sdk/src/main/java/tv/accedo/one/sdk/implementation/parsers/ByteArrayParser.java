/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.implementation.parsers;

import tv.accedo.one.sdk.implementation.utils.net.restclient.Response.ThrowingParser;
import tv.accedo.one.sdk.model.AccedoOneException;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public class ByteArrayParser implements ThrowingParser<byte[], byte[], AccedoOneException> {
    @Override
    public byte[] parse(byte[] input) throws AccedoOneException {
        return input;
    }
}