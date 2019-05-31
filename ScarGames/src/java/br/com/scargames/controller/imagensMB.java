package br.com.scargames.controller;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

@ManagedBean(name = "imagensMB")
public class imagensMB {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 6; i++) {
            images.add("wallpaper" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
