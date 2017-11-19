package fr.elimerl.registre.search.grammar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.elimerl.registre.entities.Record;

/**
 * Type d’expression du langage de recherche qui représente une requête entre
 * parenthèse.
 */
public final class RequêteEntreParenthèse extends Expression {

    /** La requête contenue dans cette expression. */
    private final Requête requête;

    /**
     * Construit une requête entre parenthèse à partir d’une requête.
     *
     * @param requête
     *            la requête à mettre entre parenthèse.
     */
    public RequêteEntreParenthèse(final Requête requête) {
	this.requête = requête;
    }

    @Override
    public Predicate createPredicate(final CriteriaBuilder constructeur,
	    final CriteriaQuery<Record> requête, final Root<Record> fiche) {
	return this.requête.créerPrédicat(constructeur, requête, fiche);
    }

    @Override
    public boolean equals(final Object objet) {
	final boolean résultat;
	if (objet == this) {
	    résultat = true;
	} else if (objet instanceof RequêteEntreParenthèse) {
	    final RequêteEntreParenthèse autre = (RequêteEntreParenthèse) objet;
	    if (requête == null) {
		résultat = (autre.requête == null);
	    } else {
		résultat = requête.equals(autre.requête);
	    }
	} else {
	    résultat = false;
	}
	return résultat;
    }

    @Override
    public int hashCode() {
	return (requête == null ? 0 : requête.hashCode());
    }

    @Override
    public String toString() {
	return "(" + requête + ")";
    }

}
