# README #

Drop-in AppGrid integration library. Currently implemented features:

* AppStart, AppStop
* Logging
* Application status
* Metadata
* Assets
* User specific settings
* CMS
* Prefetching and offline access

## Dependencies ##
```
repositories {
    flatDir{
        dirs 'libs'
    }
}
dependencies {
    compile 'tv.accedo.vdk:vdk-appgrid:3.4.1@aar'
}
```

## Documentation ##
Detailed documentation for this library can be found on Confluence:
https://accedobroadband.jira.com/wiki/pages/viewpage.action?pageId=132153969

## Download ##
This library is available in our Artifactory repository. For accessing artifactory, please consult the following Confluence page:
https://accedobroadband.jira.com/wiki/display/VDKAND/Getting+started#Gettingstarted-UsingVDKcomponents

The library is also available in binary form, here:
https://bitbucket.org/accedo/vdkmob-android/src/HEAD/binaries/?at=master

## Who do I talk to? ##
* Pásztor Tibor Viktor <tibor.pasztor@accedo.tv>

## Changelog ##

3.4.1 (2017-11-09)

* AppGridException now contains exceptions caught by RestClient as a cause.

3.4 (2017-10-02)

* !! interface change. AppGridService.getApplicationStatus() doesn't throw anymore. Apps should not bail when appstatus cannot be fetched. They should assume ACTIVE.
* Fixing cache issue when theres no session available. Now AppGridService will try to serve from cache even if the service or the internet is down.
* AppGridException.detailMessage added, where you can find the complete AppGrid response JSON.
* toString() added to model objects AppGridException, ApplicationStatus, CmsLocale, Profile.

3.3.4 (2017-08-28)

* Added AppGridService.cache().clear()

3.3.3 (2017-07-10)

* LoggingService handler now running on main thread. Should fix Handler runtime exception about missing prepare() on set-top-boxes.

3.3.2 (2017-05-30)

* Servertime now stored in appgridservice, and usable by anyone.
* Appkey and gid added to cache-key of service.
* Tests fixed.

v3.3.1 (2017-02-20)

* Dependency cleanup, dependency on IOUtils removed.

v3.3 (2017-02-02)

* !! interface change. Moved gid to service constructor.
* !! interface change. Renamed offline() to cache() service, removed prefetch calls as they were duplicates to getAllMetadataRaw() and getAllAssetsRaw().
* !! interface change. Separated logging() service. Logging calls are now true fire-and-forget, and they support bulk sending logs to appgrid, with configurable sending period.
* Added getProfile() call to AppGridService returning A/B testing info and received Profile.
* CMS now fetchable by Aliases in both AppGridService and MockGridService.
* CMS added getAvailableLocales() call.
* Fix in how assets are cached, added cache clear logic for old corrupted entries.
* Test and package hierarchy cleanup.

v3.2 (2017-01-28)

* Added retentionTime to appGrid quit events.
* !! Minor CMS interface change. OptionalParams and PaginationParams are now separated.
* CMS calls now support localisation.

v3.1.2 (2016-11-20)

* Added proper ResponseChecker for AppGridService
* Fix on assets with wrong keys

v3.1.1 (2016-09-12)

* Every service now uses the reason field when throwing custom exceptions.

v3.1 (2016-09-06)

* Added MockGridService that implements the AppGridService interface and:
  * Provides AppGrid asset, metadata and CMS data from static json files in the assets folder
  * Can provide AppGrid asset, metadata and CMS data from any source (online or offline)

v3.0 (2016-08-31)

* Added proper separation and dependency injection to subservices

v2.8.2 (2016-08-24)

* AppGrid maintenance mode fix for locales such as Azerbaijani.

v2.8.1 (2016-08-10)

* Exceptions refactored. Now they can hold a reason exception.

v2.8 (2016-08-01)

* Added GID support for metadata calls
* Library now caches raw responses, not parsed responses
* Added raw getAllMetadata() calls that return JSONObject, not Map<String, String>

v2.7 (2016-06-13)

* !!! Minor interface change in CMS calls !!!
* CMS fetch-all calls without OptionalParams now return JSONArray and not PagedResponse
* Now they actually return all the enties, even if there are more than the default page-size

v2.6 (2016-05-04)

* CMS support added

v2.5.2 (2015-10-26)

* Artifactory deploy scripts added

v2.5.1 (2015-08-31)

* User setting deletion fixed, now works with null input aswell, and disabled for single value (as per API doc)
* AppGridService metadata and asset prefetching and offline access added

v2.5 (2015-08-27)

* User specific settings service added
* Maintenance mode parsing fix
* Samples AppGridFragment removed, instead tests have been added

v2.4.1 (2015-06-30)

* Package hierarchy changes, async now uses CallbackAsyncTask

v2.4 (2015-06-25)

* AppGridService separated into standalone module

v2.3.1 (2015-06-11)

* No or minor AppGridService changes (the library was still monolitic at this point)

v2.3 (2015-06-11)

* New AppGridService, with sample code added. Supports both synchronous and asynchronous calling, offline mode, and if-modified-since.
* RestClient response caching option added
* AppGridService able to use core's DeviceIdentifier generator

## Copyright ##

Copyright (c) 2016 - Present Accedo Broadband AB, All Rights Reserved.

All information contained herein is, and remains the property of Accedo Broadband AB. Proprietary and confidential.
ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC PERFORMANCE,
OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS SOURCE CODE WITHOUT THE EXPRESS WRITTEN CONSENT OF COMPANY IS STRICTLY PROHIBITED.THE RECEIPT OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS
TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.