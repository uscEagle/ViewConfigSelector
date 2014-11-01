package com.viewconfig;



import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author tengfeil
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ViewConfigParam implements Comparable<ViewConfigParam> {
    private String name;
    private String defaultValue;
    private int priority;
    @Override
    public int compareTo(ViewConfigParam other) {
        int res = 0;
        if (this.priority != other.priority) {
            res = priority - other.priority;
        } else if (!StringUtils.equals(name, other.name)) {
            res = name.compareTo(other.name);
        } else {
            res = defaultValue.compareTo(other.defaultValue);
        }
        return res;
    }

}
