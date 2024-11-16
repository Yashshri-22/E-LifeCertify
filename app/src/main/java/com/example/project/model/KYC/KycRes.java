package com.example.project.model.KYC;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by SW11 on 7/24/2017.
 */
@Root(name = "KycRes", strict = false)
public class KycRes {

    @Attribute(name = "code", required = false)
    public String code;

    @Attribute(name = "ret", required = false)
    public String ret;

    @Attribute(name = "txn", required = false)
    public String txn;

    @Attribute(name = "ts", required = false)
    public String ts;

    @Attribute(name = "ttl", required = false)
    public String ttl;

    @Attribute(name = "actn", required = false)
    public String actn;

    @Attribute(name = "err", required = false)
    public String err;

    @Element(name = "Rar", required = false)
    public Rar rar;

}
