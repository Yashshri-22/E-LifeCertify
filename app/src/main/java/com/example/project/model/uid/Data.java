package com.example.project.model.uid;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by SW11 on 9/3/2015.
 */
public class Data {

    public Data() {
    }

    @Attribute(name = "type", required = false)
    public String type;

    @Text(required = false)
    public String value;

}
