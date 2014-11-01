package com.viewconfig.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.viewconfig.ViewConfigParam;
import com.viewconfig.ViewConfigData;

/**
 * The Class CommonUtils.
 */
public final class CommonUtils {
    
    /**
     *  CommonUtils builder.
     */
    private CommonUtils(){}
    
    private static final String TEMPLATE_KEY = "template";
    private static final String WEBLAB_KEY = "weblab";
    private static final String PAGETYPE_KEY = "pageType";
    private static final String SUBPAGETYPE_KEY = "subPageType";
    private static final String ACTION_KEY = "action";
    
    
    private static final String DEFAULT_KEY = "default";
    private static final String NAME_KEY = "name";
    private static final String PRIORITY_KEY = "priority";
    
    /**
     * Builds the view config view.
     *
     * @param appConfigView the app config view
     * @return the view config view
     */
    public static ViewConfigData buildViewConfigView(@NonNull Map<String, String> appConfigView) {
        String template = appConfigView.get(TEMPLATE_KEY);
        String pageType = appConfigView.get(PAGETYPE_KEY);
        String subPageType = appConfigView.get(SUBPAGETYPE_KEY);
        String action = appConfigView.get(ACTION_KEY);
        Map<String, String> weblabMap = buildWeblabMap(appConfigView.get(WEBLAB_KEY));
        return new ViewConfigData(template, pageType, subPageType, action, weblabMap);
    }
    
    
    /**
     * Builds the view config param.
     *
     * @param appConfigViewParamMap the app config view param map
     * @return the view config param
     */
    public static ViewConfigParam buildViewConfigParam(@NonNull Map<String, String> appConfigViewParamMap) {
        String name = appConfigViewParamMap.get(NAME_KEY);
        String defaultValue = appConfigViewParamMap.get(DEFAULT_KEY);
        int priority = Integer.parseInt(appConfigViewParamMap.get(PRIORITY_KEY));
        return new ViewConfigParam(name, defaultValue, priority);
    }
    
    /**
     * Validate weblab.
     *
     * @param requestWeblabMap the request weblab map
     * @param configWeblabMap the config weblab map
     * @return true, if successful
     */
    public static boolean validateWeblab(
            @NonNull Map<String, String> requestWeblabMap,
            @NonNull Map<String, String> configWeblabMap) {
        for (Entry<String, String> entry : configWeblabMap.entrySet()) {
            if (!StringUtils.equals(entry.getValue(), requestWeblabMap.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * Reorder view key.
     *
     * @param appConfigViewKey the app config view key
     * @param paramOrderList the param order list
     * @return the string
     */
    public static String reorderViewKey(
            @NonNull String appConfigViewKey,
            @NonNull List<ViewConfigParam> paramOrderList) {
        Map<String, String> configKeyMap = new HashMap<String, String>();
        if (!StringUtils.isEmpty(appConfigViewKey)) {
            String[] configKeyPairArr = StringUtils.split(appConfigViewKey, "&");
            if (!ArrayUtils.isEmpty(configKeyPairArr)) {
                for (String weblab : configKeyPairArr) {
                    String[] configKeyPair = StringUtils.split(weblab, "-");
                    configKeyMap.put(configKeyPair[0], configKeyPair[1]);
                } 
            }
        }
        return buildViewConfigMapKey(configKeyMap, paramOrderList);
    }
    
    /**
     * Builds the view config map key.
     *
     * @param requestParams the request params
     * @param viewConrigParamList the viewConrigParamList
     * @return the string
     */
    public static String buildViewConfigMapKey(
            @NonNull Map<String, String> requestParams, 
            @NonNull List<ViewConfigParam> viewConrigParamList) {
        //loop all the params in the list
        List<String> strList = new ArrayList<String>();
        for (ViewConfigParam param : viewConrigParamList) {
            //if we have it in the requestParams, we set it, otherwise we go to default
            String strKey = param.getName();
            String strValue = param.getDefaultValue();
            if (requestParams.containsKey(param.getName())) {
                strValue = requestParams.get(strKey);
            }
            strList.add(strKey + "-" + strValue); 
        }
        return StringUtils.join(strList, "&");
    }
    
    /**
     * Builds the weblab map.
     *
     * @param weblabStr the weblab str
     * @return the map
     */
    private static Map<String, String> buildWeblabMap(String weblabStr) {
        Map<String, String> weblabMap = new HashMap<String, String>();
        if (!StringUtils.isEmpty(weblabStr)) {
            String[] weblabStrs = StringUtils.split(weblabStr, "|");
            if (!ArrayUtils.isEmpty(weblabStrs)) {
                for (String weblab : weblabStrs) {
                    String[] weblabPair = StringUtils.split(weblab, ":");
                    weblabMap.put(weblabPair[0], weblabPair[1]);
                } 
            }
        }
        return weblabMap;
    }
}
