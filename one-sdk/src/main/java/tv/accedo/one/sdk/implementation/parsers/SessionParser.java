/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.implementation.parsers;

import android.annotation.SuppressLint;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tv.accedo.one.sdk.implementation.utils.net.restclient.Response.ThrowingParser;
import tv.accedo.one.sdk.model.AccedoOneException;
import tv.accedo.one.sdk.model.AccedoOneException.StatusCode;
import tv.accedo.one.sdk.implementation.utils.net.restclient.Response;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
public class SessionParser implements ThrowingParser<Response, Pair<String, Long>, AccedoOneException> {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdfSession = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ssZ");
    
    @Override
    public Pair<String, Long> parse(Response response) throws AccedoOneException {
        try {
            JSONObject jsonObject = new JSONObject(response.getText());
            Date expiration = sdfSession.parse(jsonObject.getString("expiration"));
            
            String session = jsonObject.getString("sessionKey");
            long sessionExpiration = expiration.getTime();
            return new Pair<String, Long>(session, sessionExpiration);
            
        } catch (ParseException | JSONException e) {
            throw new AccedoOneException(StatusCode.NO_SESSION, e);
        }
    }
}