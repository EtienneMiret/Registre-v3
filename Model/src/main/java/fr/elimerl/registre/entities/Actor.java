package fr.elimerl.registre.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A movie actor.
 */
@Entity
@Table(name = "actors")
public class Actor extends Named {

    /**
     * Register a new actor with the given name. There must be no such actor in
     * the database.
     *
     * @param name
     * 		name of the new actor.
     */
    public Actor(final String name) {
	super(name);
    }

    /**
     * No-args constructor. Required by Hibernate.
     */
    protected Actor() {
	super();
    }

}
