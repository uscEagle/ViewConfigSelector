package com.viewconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.lang3.StringUtils;

import com.viewconfig.utils.CommonUtils;

/**
 * The Class TemplateSelector.
 */
public class SimpleViewConfigDataSelector implements IViewConfigDataSelector {
    
    private Map<String, Set<ViewConfigData>> viewConfigMap;
    private List<ViewConfigParam> viewConrigParamList;
    
    /**
     * Instantiates a new template selector.
     *
     * @param configurationBuilder the configuration builder
     * @param appConfigViewMap the app config view map
     * @param appConfigViewParams the app config view params
     */
    public SimpleViewConfigDataSelector(IConfigurationBuilder configurationBuilder,
            Map<String, Set<Map<String, String>>> appConfigViewMap,
            List<Map<String, String>> appConfigViewParams) {
        viewConrigParamList = configurationBuilder.buildViewConfigParamList(appConfigViewParams);
        viewConfigMap = configurationBuilder.buildViewConfigViewMap(appConfigViewMap, viewConrigParamList);
    }
    
    @Override
    public ViewConfigData selectViewConfigData(
        Map<String, String> requestParams,
        Map<String, String> requestWeblabMap) {
        //we select the viewConfig set
        Set<ViewConfigData> viewConfigViewSet =  selectViewConfigViewSet(requestParams);
        //then based on the weblab, we select the template
        return selectTemplateFromViewConfigViewSet(viewConfigViewSet, requestWeblabMap);
    }
    
    
    /**
     * Select view config view set.
     *
     * @param requestParams the request params
     * @return the sets the
     */
    private Set<ViewConfigData> selectViewConfigViewSet(final Map<String, String> requestParams) {
        //we try to use the requestParams to generate the viewConfigView
        String viewConfigMapKey = CommonUtils.buildViewConfigMapKey(requestParams, viewConrigParamList);
        //if we do not have the requestParams in the configMap, we roll back to default value from the last to the first
        if (!viewConfigMap.containsKey(viewConfigMapKey)) {
            Map<String, String> rollBackParams = new HashMap<String, String>();
            rollBackParams.putAll(requestParams);
            for (int i = viewConrigParamList.size() - 1; i >= 0; i--) {
                ViewConfigParam param = viewConrigParamList.get(i);
                //we only rollback if the request params value is different than the default one
                if (!StringUtils.equals(rollBackParams.get(param.getName()), param.getDefaultValue())) {
                    rollBackParams.put(param.getName(), param.getDefaultValue());
                    viewConfigMapKey = CommonUtils.buildViewConfigMapKey(rollBackParams, viewConrigParamList);
                    if (viewConfigMap.containsKey(viewConfigMapKey)) {
                        break;
                    }
                }
            }
        }
        return viewConfigMap.get(viewConfigMapKey);
    }
    
    /**
     * Select template from view config view set.
     *
     * @param viewConfigViewSet the view config view set
     * @param requestWeblabMap the request weblab map
     * @return the string
     */
    private ViewConfigData selectTemplateFromViewConfigViewSet(
        Set<ViewConfigData> viewConfigViewSet, Map<String, String> requestWeblabMap) {
        for (ViewConfigData viewConfigView : viewConfigViewSet) {
            if (CommonUtils.validateWeblab(requestWeblabMap, viewConfigView.getWeblabMap())) {
                return viewConfigView;
            }
        }
        return null;
    }
}
