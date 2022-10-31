package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.shared.RandomUUID;

public class AuthorId  extends RandomUUID {
    @Override
    protected String getPrefix() {
        return "AUT-%s";
    }
}
