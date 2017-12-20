/*
 * Copyright (c) 2016 - present Accedo Broadband AB. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package tv.accedo.one.sdk.implementation;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tv.accedo.one.sdk.BuildConfig;
import tv.accedo.one.sdk.implementation.utils.ObjectToFile;
import tv.accedo.one.sdk.implementation.utils.Utils;
import tv.accedo.one.sdk.implementation.utils.net.restclient.Response;
import tv.accedo.one.sdk.implementation.utils.net.restclient.Response.ThrowingParser;
import tv.accedo.one.sdk.implementation.utils.net.restclient.RestClient;
import tv.accedo.one.sdk.model.AccedoOneException;
import tv.accedo.one.sdk.model.AccedoOneException.StatusCode;

/**
 * @author Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>
 */
class IfModifiedTask {
    private static final String FILENAME_VERSIONCODE = "OneSdkVersion";
    private static final int LAST_CACHEBREAKING_VERSION_UPDATE = 100; //1.0(.0)

    private static final SimpleDateFormat sdfIfModifiedSince = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

    private AccedoOneImpl accedoOneImpl;
    private Context context;
    private String url;
    
    public IfModifiedTask(AccedoOneImpl accedoOneImpl, Context context, String url){
        this.accedoOneImpl = accedoOneImpl;
        this.context = context;
        this.url = url;
    }
    
    @SuppressWarnings("unchecked")
    public <O> O run(ThrowingParser<byte[], O, AccedoOneException> parser) throws AccedoOneException {
        cleanup(context, false);

        RestClient restClient = accedoOneImpl.createRestClient(url);

        String key = getCacheKey(restClient.getUrl(), accedoOneImpl.getAppKey(), accedoOneImpl.getGid());
        String timestampKey = getTimestampCacheKey(restClient.getUrl(), accedoOneImpl.getAppKey(), accedoOneImpl.getGid());

        //Try to fetch a response.. Remember, even createSessionedRestClient() might fail if we are offline
        Throwable caughtException = null;
        Response response = null;
        try{
            //If-Modified-Since
            if(ObjectToFile.exists(context, timestampKey)){
                Long lastFetchedAt = (Long) ObjectToFile.read(context, timestampKey);
                if(lastFetchedAt!=null){
                    restClient.addHeader("If-Modified-Since", sdfIfModifiedSince.format(new Date(lastFetchedAt)));
                }
            }
            
            //Connect
            response = restClient.addHeader(Constants.HEADER_SESSION, accedoOneImpl.getSession()).connect(new AccedoOneResponseChecker());
            
        }catch(AccedoOneException e){
            Utils.log(e);
            Utils.log(Log.INFO, "Something went wrong. Going into offline mode for: "+restClient.getUrl());
            caughtException = e.getCause();
        }
        
        //Process response
        if (response != null && response.isSuccess()) {
            //Store if we've successfuly fetched something
            O result = parser.parse(response.getRawResponse());
            ObjectToFile.write(context, response.getRawResponse(), key);
            ObjectToFile.write(context, response.getServerTime(), timestampKey);
            Utils.log(Log.INFO, "Storing in offline cache: "+restClient.getUrl());
            return result;
        
        } else {
            //Try from offline cache
            Object result = null;
            if(ObjectToFile.exists(context, key)){
                result = parser.parse((byte[]) ObjectToFile.read(context, key));
            }
            if(result!=null){
                //We got something, lets try to cast and return it
                try{
                    O o = (O) result;
                    Utils.log(Log.INFO, "Serving from offline cache: "+restClient.getUrl());
                    return o;
                }catch(ClassCastException e){
                    Utils.log(Log.WARN, "Failed to serve from offline cache: "+restClient.getUrl());
                    ObjectToFile.delete(context, key);
                    ObjectToFile.delete(context, timestampKey);
                    throw new AccedoOneException(StatusCode.CACHE_ERROR, e);
                }    
            }else{
                //Sorry, no luck
                Utils.log(Log.WARN, "Failed to serve from offline cache: "+restClient.getUrl());
                throw new AccedoOneException(StatusCode.CACHE_MISS, caughtException);
            }
        }
    }
    
    public static String getCacheKey(String url, String appKey, String gid){
        return "ONE"+ Utils.md5Hash(removeSession(url) + appKey + gid);
    }
    public static String getTimestampCacheKey(String url, String appKey, String gid){
        return "ONE"+ Utils.md5Hash(removeSession(url) + appKey + gid)+".t";
    }
    private static String removeSession(String url){
        if (url.contains("sessionKey")) {
            try {
                URI uri = new URI(url);
                return new URI(uri.getScheme(),
                        uri.getAuthority(),
                        uri.getPath(),
                        null, // ignore the query part of the input url, contains sessionKey
                        uri.getFragment()).toString();
            } catch (URISyntaxException e) {
                Utils.log(e);
            }
        }

        return url;
    }

    static void cleanup(Context context, boolean force) {
        Integer versionCode = (Integer) ObjectToFile.read(context, FILENAME_VERSIONCODE);

        if (force || versionCode == null || versionCode.intValue() < LAST_CACHEBREAKING_VERSION_UPDATE) {
            for (File file : context.getFilesDir().listFiles()) {
                if (file.getName().startsWith("ONE")) {
                    file.delete();
                }
            }
        }

        ObjectToFile.write(context, new Integer(BuildConfig.VERSION_CODE), FILENAME_VERSIONCODE);
    }
}