package fr.elimerl.registre.modèle.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.elimerl.registre.modèle.recherche.grammaire.Expression;
import fr.elimerl.registre.modèle.recherche.grammaire.Requête;
import fr.elimerl.registre.modèle.recherche.signes.Champ;
import fr.elimerl.registre.modèle.recherche.signes.MotClé;
import fr.elimerl.registre.modèle.recherche.signes.Opérateur;
import fr.elimerl.registre.modèle.recherche.signes.Parenthèse;
import fr.elimerl.registre.modèle.recherche.signes.Signe;

/**
 * Classe singleton chargée de parser les recherches des utilisateurs.
 */
public class ParseurDeRecherches {

    /** Journal SLF4J de cette classe. */
    private static final Logger journal =
	    LoggerFactory.getLogger(ParseurDeRecherches.class);

    /**
     * Réalise l’analyse lexicale de la requête utilisateur passée en paramètre.
     *
     * @param requête
     *            requête fournie par l’utilisateur. Peut contenir n’importe
     *            quoi.
     * @return la file de symboles contenue dans la requête.
     */
    public Queue<Signe> analyserLexicalement(final String requête) {
	final Queue<Signe> résultat = new LinkedList<Signe>();
	int i = 0;
	while (i < requête.length()) {
	    Signe signe = null;
	    {
		final Matcher comparateur =
			Parenthèse.OUVRANTE.getMotif().matcher(requête);
		if (comparateur.find(i)) {
		    signe = Parenthèse.OUVRANTE;
		    i = comparateur.end();
		}
	    }
	    if (signe == null) {
		final Matcher comparateur =
			Parenthèse.FERMANTE.getMotif().matcher(requête);
		if (comparateur.find(i)) {
		    signe = Parenthèse.FERMANTE;
		    i = comparateur.end();
		}
	    }
	    final Iterator<Opérateur> opérateurs = Opérateur.tous().iterator();
	    while (signe == null && opérateurs.hasNext()) {
		final Opérateur opérateur = opérateurs.next();
		final Matcher comparateur =
			opérateur.getMotif().matcher(requête);
		if (comparateur.find(i)) {
		    signe = opérateur;
		    i = comparateur.end();
		}
	    }
	    final Iterator<Champ> champs = Champ.tous().iterator();
	    while (signe == null && champs.hasNext()) {
		final Champ champ = champs.next();
		final Matcher comparateur = champ.getMotif().matcher(requête);
		if (comparateur.find(i)) {
		    signe = champ;
		    i = comparateur.end();
		}
	    }
	    if (signe == null) {
		final Matcher comparateur = MotClé.MOTIF.matcher(requête);
		if (comparateur.find(i)) {
		    signe = new MotClé(comparateur.group(1).toLowerCase());
		    i = comparateur.end();
		}
	    }
	    if (signe == null) {
		i++;
	    } else {
		résultat.add(signe);
	    }
	}
	return résultat;
    }

    /**
     * Construit une requête à partir des signes résultants de l’analyse
     * lexicale. Consomme les signes de la file qui ont un sens, et s’arrête dès
     * qu’il n’est plus possible de construire la requête.
     *
     * @param signes
     *            la file de signes obtenue par l’analyse lexicale du texte
     *            entré par l’utilisateur.
     * @return la requête résultant de l’analyse grammaticale des signes
     *         données.
     */
    public Requête analyserGrammaticalement(final Queue<Signe> signes) {
	final List<Expression> expressions = new ArrayList<Expression>();
	boolean conjonction;
	try {
	    expressions.add(analyserExpression(signes));
	    conjonction = signes.peek() != Opérateur.OU;
	} catch (final ParseException e) {
	    journal.info("Grammaire de la requête incorrecte :", e);
	    conjonction = true; // Peu importe.
	    signes.clear(); // Force l’arrêt de l’analyse.
	}
	while (!signes.isEmpty() && signes.peek() != Parenthèse.FERMANTE) {
	    try {
		expressions.add(analyserExpression(signes));
		/*
		 * Si on est une disjonction et qu’on est pas à la fin, on doit
		 * trouver un opérateur « ou ».
		 */
		if (!conjonction
			&& !signes.isEmpty()
			&& signes.peek() != Parenthèse.FERMANTE
			&& signes.peek() != Opérateur.OU) {
		    journal.info("Grammaire de la requête incorrecte,"
			    + " « ou » attendu, {} trouvé.", signes.peek());
		    signes.clear(); // Forece l’arrêt de l’analyse.
		}
	    } catch (final ParseException e) {
		journal.info("Grammaire de la requête incorrecte :", e);
		signes.clear(); // Force l’arrêt de l’analyse.
	    }
	}
	return new Requête(conjonction, expressions.toArray(new Expression[0]));
    }

    private Expression analyserExpression(final Queue<Signe> signes)
	    throws ParseException {
	// TODO Auto-generated method stub
	return null;
    }

}
