package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.shared.RandomUUID;

public class ArticleId  extends RandomUUID {
    @Override
    protected String getPrefix() {
        return "ART-%s";
    }
}
