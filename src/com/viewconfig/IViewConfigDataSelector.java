package com.viewconfig;

import java.util.Map;

/**
 * The Interface ITemplateSelector.
 */
public interface IViewConfigDataSelector {
    
    /**
     * Select template.
     *
     * @param requestParams the request params
     * @param requestWeblabMap the request weblab map
     * @return the view config view
     */
    ViewConfigData selectViewConfigData(
            Map<String, String> requestParams,
            Map<String, String> requestWeblabMap);
}
