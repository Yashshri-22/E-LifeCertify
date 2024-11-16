package com.example.project.model.KYC;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by SW11 on 7/21/2017.
 */

@Root(name = "Kyc")
public class KycReq {

    @Attribute(name = "ver", required = true)
    public String ver;

    @Attribute(name = "ra", required = true)
    public String ra;

    @Attribute(name = "rc", required = true)
    public String rc;

    @Attribute(name = "lr", required = true)
    public String lr;

    @Attribute(name = "de", required = true)
    public String de;

    @Attribute(name = "pfr", required = true)
    public String pfr;

    @Element(name = "Rad", required = true)
    public Rad rad;

}
