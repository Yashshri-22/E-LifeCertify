package com.example.project.model.KYC;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by software 121216 on 7/24/2017.
 */
@Root(name = "Rar")
public class Rar {

    @Text(required = false)
    public String value;

}
