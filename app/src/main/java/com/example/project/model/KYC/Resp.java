package com.example.project.model.KYC;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by SW11 on 7/24/2017.
 */
@Root(name = "Resp")
public class Resp {

    @Attribute(name = "status", required = false)
    public String status;

    @Attribute(name = "ko", required = false)
    public String ko;

    @Attribute(name = "ret", required = false)
    public String ret;

    @Attribute(name = "code", required = false)
    public String code;

    @Attribute(name = "txn", required = false)
    public String txn;

    @Attribute(name = "ts", required = false)
    public String ts;

    @Attribute(name = "err", required = false)
    public String err;

    @Element(name = "kycRes", required = false)
    public kycRes2 kycRes;
}
