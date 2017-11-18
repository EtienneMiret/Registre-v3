package fr.elimerl.registre.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Record kind for comics.
 */
@Entity
@Table(name = "bandes_dessinees")
public class Comic extends Fiche {

    /**
     * Cartoonist of this comic.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dessinateur")
    private Cartoonist cartoonist;

    /**
     * Script writer of this comic.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenariste")
    private Scénariste scriptWriter;

    /**
     * Number of this comic within its series.
     */
    @Column(name = "numero")
    private Integer number;

    /**
     * Register a new comic.
     * @param title
     * 		title for this comic.
     * @param creator
     * 		user who registered this comic.
     */
    public Comic(final String title, final Utilisateur creator) {
	super(title, creator);
    }

    /**
     * No-args constructor. Required by Hibernate.
     */
    protected Comic() {
	super();
    }

    @Override
    public List<String> getAutresChamps() {
	final List<String> result = super.getAutresChamps();
	result.add("BD");
	result.add("bande-déssinée");
	if (cartoonist != null) {
	    result.add(cartoonist.getNom());
	}
	if (scriptWriter != null) {
	    result.add(scriptWriter.getNom());
	}
	if (number != null) {
	    result.add(number.toString());
	}
	return result;
    }

    /**
     * Returns the cartoonist of this comic.
     * @return the cartoonist of this comic, or {@code null} if unknown.
     * @see #cartoonist
     */
    public Cartoonist getCartoonist() {
        return cartoonist;
    }

    /**
     * Set the cartoonist of this comic.
     *
     * @param cartoonist
     * 		cartoonist of this comic, or {@code null} if unknown.
     * @see #cartoonist
     */
    public void setCartoonist(final Cartoonist cartoonist) {
        this.cartoonist = cartoonist;
    }

    /**
     * Returns the script writer of this comic.
     *
     * @return the script writer of this comic, or {@code null} if unknown.
     * @see #scriptWriter
     */
    public Scénariste getScriptWriter() {
        return scriptWriter;
    }

    /**
     * Set the script writer of this comic.
     *
     * @param scriptWriter
     * 		script writer of this comic, or {@code null} if unknown.
     * @see #scriptWriter
     */
    public void setScriptWriter(final Scénariste scriptWriter) {
        this.scriptWriter = scriptWriter;
    }

    /**
     * Returns the number of this comic within its series.
     * @return the number of this comic within its series, or {@code null} if
     * 		unknown.
     * @see #number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Définit le numéro de cette bande-dessinée dans sa série.
     *
     * @param number
     *            numéro de cette bande-dessinée dans sa série, ou {@code null}
     *            s’il est inconnu.
     * @see #number
     */
    /**
     * Set the number of this comic within its series.
     *
     * @param number
     * 		number of this comic within its series, or {@code null} if
     * 		unknown.
     * @see #number
     */
    public void setNumber(final Integer number) {
        this.number = number;
    }

}