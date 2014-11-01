ViewConfigSelector
Summary:
The viewConfigSelector is a package to help the user config their views based on the configuration, people would have different look and feel for different of device and types.

Problems we have now:
1. When we build a website, we would like to support different kinds of devices. For example, we could like to give user "mobile" view when user use Smartphone to browse our page, give user "tablet" view when user use ipad to browse our page. Even, we could specify the "mobile" to "ios", "android" or something. While, to save out time, we could like to have the same viewModel code across all the devices but only select the view based on configuration.
2. beside the device, we may even have more factors to select the view. For example, in amazon, you would like to give "prime" user different "user experience" other than the normal. Another example is, for different kinds of sellers in amazon, we may want to have different features in the page. So, we may need a generic viewConfig model to select the view for us.
3. One interesting part is we may have different input in the selector and the user may not pass all the viewConfigParams into the selector, we need some kind of default value to fall back.
4. another challenging problem is we may have different params as input, like "device", "sellerType", but for the config we may need some priority.  

Our solution and one example
## sample view-Config
*.*.sample.viewParams = (
    {
        name = "country";
        priority = "3";
        default = "US";
    },
    {
        name = "sellerType";
        priority = "2";
        default = "default";
    },
    {
        name = "deviceType";
        priority = "1";
        default = "desktop";
    },
);
*.*.sample.viewConfig = {
    deviceType-desktop&country-US&sellerType-default = (
        {
            template = "sellerDefaultViewV0";
            weblab = "welab_sellerviewv1_flag:C";
            pageType = "sellerView";
            subPageType = "defaultV0";
            action = "showSellerView";
        }, 
        {
            template = "sellerDefaultViewV1";
            weblab = "welab_sellerviewv1_flag:T1";
            pageType = "sellerView";
            subPageType = "defaultV1";
            action = "showSellerViewV1";
        },  
    );
    deviceType-desktop&country-CN&sellerType-default = (
        {
            template = "sellerCNView";
            pageType = "sellerView";
            subPageType = "cnView";
            action = "showCNSellerView";
        },  
    );
    deviceType-desktop&country-US&sellerType-wine = (
        {
            template = "sellerWineView";
            pageType = "sellerView";
            subPageType = "sellerWineView";
            action = "showWineSellerView";
        },  
    );
	
	deviceType-mobile&country-US&sellerType-default = (
        {
            template = "sellerMobileView";
            pageType = "sellerMobileView";
            subPageType = "sellerMobileView";
            action = "showSellerMobileView";
        },  
    );
   
    deviceType-tablet&country-US&sellerType-default = (
        {
            template = "sellerTabletView";
            pageType = "sellerTabletView";
            subPageType = "sellerTabletView";
            action = "showSellerTabletView";
        },  
    );
};

our code structure
src/com/viewconfig/
IConfigurationBuilder
    configurationBuilderInterface
IViewConfigDataSelector
    viewConfigDataSelector
PrioritizedConfigurationBuilder
    Based on the config file, construct the list of the viewConfigParam and a config map, key is the generatedString key like "deviceType-tablet&country-US&sellerType-default", and the value is the viewConfigData
SimpleViewConfigDataSelector
    based on the params from the request and our viewConfigParam list, viewConfigDataMap, we return the first mapped viewConfigData
ViewConfigData
    The return of the viewConfigDataSelector, we would return the template, pageType, subPageType, action for the actual view and metrics.  
ViewConfigParam
    The viewConfigParams contains the viewConfigName, default value and priority. Also, it implements the comparable interface by the priority
src/com/viewconfig/utils
CommonUtils
    Utils class 
	    build the string key based on the request params
		reorder the key
		validateWeblab
		buildTheViewConfigData
		
would provide demo soon