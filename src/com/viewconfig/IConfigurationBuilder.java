package com.viewconfig;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * IConfigurationBuilder builder interface.
 *
 * @author tengfeil
 */
public interface IConfigurationBuilder {
    
    /**
     * Builds the view config view map.
     *
     * @param appConfigViewMap the app config view map
     * @param paramList the paramList
     * @return the map
     */
    Map<String, Set<ViewConfigData>> buildViewConfigViewMap(
            Map<String, Set<Map<String, String>>> appConfigViewMap, List<ViewConfigParam> paramList);
    
    /**
     * Builds the view config param list.
     *
     * @param appConfigViewParams the app config view params
     * @return the list
     */
    List<ViewConfigParam> buildViewConfigParamList(
            List<Map<String, String>> appConfigViewParams);
}
