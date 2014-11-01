package com.viewconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.viewconfig.utils.CommonUtils;

/**
 *
 * @author tengfeil
 */
public class PrioritizedConfigurationBuilder implements IConfigurationBuilder {
    
    @Override
    public Map<String, Set<ViewConfigData>> buildViewConfigViewMap(
            Map<String, Set<Map<String, String>>> appConfigViewMap, List<ViewConfigParam> paramList) {
        Map<String, Set<ViewConfigData>> configMap = new HashMap<String, Set<ViewConfigData>>();
        for (Entry<String, Set<Map<String, String>>> enty : appConfigViewMap.entrySet()) {
            Set<Map<String, String>> appViewSet = enty.getValue();
            Set<ViewConfigData> viewConfigViewSet = new HashSet<ViewConfigData>();
            for (Map<String, String> appDetailViewInfoMap : appViewSet) {
                viewConfigViewSet.add(CommonUtils.buildViewConfigView(appDetailViewInfoMap));
            }
            String reorderedKey = CommonUtils.reorderViewKey(enty.getKey(), paramList);
            configMap.put(reorderedKey, viewConfigViewSet);
        }
        return configMap;
    }
    
    @Override
    public List<ViewConfigParam> buildViewConfigParamList(
            List<Map<String, String>> appConfigViewParams) {
        List<ViewConfigParam> viewConfigParamList = new ArrayList<ViewConfigParam>();
        for (Map<String, String> appConfigParamMap : appConfigViewParams) {
            viewConfigParamList.add(CommonUtils.buildViewConfigParam(appConfigParamMap));
        }
        Collections.sort(viewConfigParamList);
        return viewConfigParamList;
    }
}
