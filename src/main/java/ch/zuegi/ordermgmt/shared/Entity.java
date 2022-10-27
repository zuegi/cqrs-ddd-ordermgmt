package ch.zuegi.ordermgmt.shared;

import java.io.Serializable;

public interface Entity<E, RandomUUID extends Serializable> {
    /**
     * Entities compare by identity, not by attributes.
     *
     * @param other The other entity.
     * @return true if the identities are the same, regardles of other attributes.
     */
    boolean sameIdentityAs(E other);

    RandomUUID id();
}
