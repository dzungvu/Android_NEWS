package com.software.dzungvu.model;

import java.lang.annotation.ElementType;
import java.util.List;

/**
 * Created by DzungVu on 7/7/2017.
 */

public class item {
    List<NewsElements> elementsList;

    public item(List<NewsElements> elementsList) {
        this.elementsList = elementsList;
    }

    public item() {
    }

    public List<NewsElements> getElementsList() {
        return elementsList;
    }

    public void setElementsList(List<NewsElements> elementsList) {
        this.elementsList = elementsList;
    }
}
