package com.viewconfig;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author tengfeil
 *
 */
@AllArgsConstructor
@Getter
public class ViewConfigData {
    private String template;
    private String pageType;
    private String subPageType;
    private String action;
    private Map<String, String> weblabMap;
}
