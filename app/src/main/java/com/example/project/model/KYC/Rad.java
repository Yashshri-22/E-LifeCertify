package com.example.project.model.KYC;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by SW11 on 7/21/2017.
 */
@Root(name = "Rad")
public class Rad {

    @Text(required = true)
    public String value;
}
