package com.example.project.model.KYC;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by SW11 on 7/24/2017.
 */
@Root(name="kycRes")
public class kycRes2 {

    @Text(required = false)
    public String value;

}
