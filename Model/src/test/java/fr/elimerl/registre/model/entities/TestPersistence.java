package fr.elimerl.registre.model.entities;

import static fr.elimerl.registre.entities.Movie.Support.BRD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fr.elimerl.registre.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.elimerl.registre.entities.Actor;
import fr.elimerl.registre.entities.Movie.Support;
import fr.elimerl.registre.entities.Reference.Field;

/**
 * Dans ce test JUnit, on vérifie le mapping Hibernate, le schéma de la base
 * de données, et qu’ils correspondent entre eux.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestPersistence {

    /** Nom d’un utilisateur qui n’existe pas dans la base de données. */
    private static final String UTILISATEUR = "Etienne Miret";

    /** Adresse email n’appartenant à aucun utilisateur enregistré en base. */
    private static final String EMAIL = "etienne.miret@ens-lyon.org";

    /** Nom qui ne doit appartenir à aucun {@link Named} en base. */
    private static final String NOM = "Test de persistence JUnit";

    /** Mot qui ne doit pas exister dans le dictionnaire de la base. */
    private static final String MOT = "Test JUnit";

    /** L’entier 0, défini comme une constante. */
    private static final int ZÉRO = 0;

    /** L’entier 1, défini comme une constante. */
    private static final int UN = 1;

    /** L’entier 2, défini comme une constante. */
    private static final int DEUX = 2;

    /** L’entier 3, défini comme une constante. */
    private static final int TROIS = 3;

    /** L’entier 4, défini comme une constante. */
    private static final int QUATRE = 4;

    /** L’entier 5, défini comme une constante. */
    private static final int CINQ = 5;

    /** L’entier 6, défini comme une constante. */
    private static final int SIX = 6;

    /** L’entier 7, défini comme une constante. */
    private static final int SEPT = 7;

    /** L’entier 8, défini comme une constante. */
    private static final int HUIT = 8;

    /** L’entier 9, défini comme une constante. */
    private static final int NEUF = 9;

    /** L’entier 12, défini comme une constante. */
    private static final int DOUZE = 12;

    /** Loggeur de cette classe. */
    private static final Logger logger =
	    LoggerFactory.getLogger(TestPersistence.class);

    /**
     * Usine à {@link EntityManager} fournie par Spring.
     */
    @Resource(name = "embeddedEmf")
    private EntityManagerFactory emf;

    /**
     * Gestionnaire d’entité utilisé en guise de DAO.
     */
    private EntityManager em;

    /**
     * Fabrique de requêtes.
     */
    private CriteriaBuilder builder;

    /**
     * Prépare l’environnement pour les tests.
     */
    @Before
    public void setUp() {
	builder = emf.getCriteriaBuilder();
	em = emf.createEntityManager();
	em.getTransaction().begin();
    }

    /**
     * Nettoye l’environnement de test. En particulier, fait un retour arrière
     * sur la transaction en cours.
     */
    @After
    public void tearDown() {
	em.getTransaction().rollback();
	em.close();
    }

    /**
     * Teste qu’on peut enregistrer en base le {@code Named} donné en argument.
     * Cette méthode est appelée pour tester l’enregistrement en base de chaque
     * implémentation de {@code Named}.
     *
     * @param nommé nommé pas encore en base.
     */
    private void enregistrementNommé(final Named nommé) {
	final String nom = nommé.getName();
	assertNull(nommé.getId());
	final Named nomméEnregistré = em.merge(nommé);
	em.flush();
	assertNotNull(nomméEnregistré.getId());
	assertEquals(nom, nomméEnregistré.getName());
    }

    /**
     * Teste qu’on peut enregistrer un acteur en base.
     */
    @Test
    public void enregistrementActeur() {
	logger.info("Test d’enregistrement d’un acteur.");
	enregistrementNommé(new Actor(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un auteur en base.
     */
    @Test
    public void enregistrementAuteur() {
	logger.info("Test d’enregistrement d’un auteur.");
	enregistrementNommé(new Author(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un compositeur en base.
     */
    @Test
    public void enregistrementCompositeur() {
	logger.info("Test d’enregistrement d’un compositeur.");
	enregistrementNommé(new Composer(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un dessinateur en base.
     */
    @Test
    public void enregistrementDessinateur() {
	logger.info("Test d’enregistrement d’un dessinateur.");
	enregistrementNommé(new Cartoonist(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un emplacement en base.
     */
    @Test
    public void enregistrementEmplacement() {
	logger.info("Test d’enregistrement d’un emplacement.");
	enregistrementNommé(new Location(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un propriétaire en base.
     */
    @Test
    public void enregistrementPropriétaire() {
	logger.info("Test d’enregistrement d’un propriétaire.");
	enregistrementNommé(new Owner(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un réalisateur en base.
     */
    @Test
    public void enregistrementRéalisateur() {
	logger.info("Test d’enregistrement d’un réalisateur.");
	enregistrementNommé(new Director(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un scénariste en base.
     */
    @Test
    public void enregistrementScénariste() {
	logger.info("Test d’enregistrement d’un scénariste.");
	enregistrementNommé(new ScriptWriter(NOM));
    }

    /**
     * Teste qu’on peut enregistrer une série en base.
     */
    @Test
    public void enregistrementSérie() {
	logger.info("Test d’enregistrement d’une série.");
	enregistrementNommé(new Series(NOM));
    }

    /**
     * Teste qu’on peut enregistrer un utilisateur en base.
     */
    @Test
    public void enregistrementUtilisateur() {
	logger.info("Test d’enregistrement d’un utilisateur.");
	User utilisateur = new User(UTILISATEUR, EMAIL);
	assertNull(utilisateur.getId());
	utilisateur = em.merge(utilisateur);
	em.flush();
	assertNotNull(utilisateur.getId());
	assertEquals(UTILISATEUR, utilisateur.getName());
	assertEquals(EMAIL, utilisateur.getEmail());
    }

    /**
     * Teste qu’on peut enregistrer une session en base.
     */
    @Test
    public void enregistrementSession() {
	logger.info("Test d’enregistrement d’une session.");
	User utilisateur = new User(UTILISATEUR, EMAIL);
	utilisateur = em.merge(utilisateur);
	Session session = new Session(utilisateur, 0L);
	final String clef = session.getKey();
	final Date expiration = session.getExpiration();
	session = em.merge(session);
	assertEquals(utilisateur, session.getUser());
	assertEquals(clef, session.getKey());
	assertEquals(expiration, session.getExpiration());
    }

    /**
     * Teste qu’on peut enregistrer une bande-dessinée en base. On vérifie tous
     * les champs d’un objet {@code Comic}, y compris ceux hérités de
     * {@code Record}.
     */
    @Test
    public void enregistrementBandeDessinée() {
	logger.info("Test d’enregistrement d’une bande dessinée");

	final String titre = "Une super BD";
	User créateur = new User("Créateur", "createur@email");
	User éditeur = new User("Éditeur", "editeur@email");
	créateur = em.merge(créateur);
	éditeur = em.merge(éditeur);
	Comic bandeDessinée = new Comic(titre, créateur);
	final Date création = bandeDessinée.getCreation();
	assertNull(bandeDessinée.getId());

	bandeDessinée = em.merge(bandeDessinée);
	em.flush();

	assertNotNull(bandeDessinée.getId());
	assertEquals(titre, bandeDessinée.getTitle());
	assertNull(bandeDessinée.getSeries());
	assertNull(bandeDessinée.getComment());
	assertNull(bandeDessinée.getPicture());
	assertNull(bandeDessinée.getOwner());
	assertNull(bandeDessinée.getLocation());
	assertEquals(créateur, bandeDessinée.getCreator());
	assertEquals(création, bandeDessinée.getCreation());
	assertEquals(créateur, bandeDessinée.getLastModifier());
	assertEquals(création, bandeDessinée.getLastModification());
	assertNull(bandeDessinée.getCartoonist());
	assertNull(bandeDessinée.getScriptWriter());
	assertNull(bandeDessinée.getNumber());

	final Long id = bandeDessinée.getId();
	Series série = new Series(NOM);
	série = em.merge(série);
	final String commentaire = "Bonjour les gens !";
	final String image = UUID.randomUUID().toString();
	Owner propriétaire = new Owner(NOM);
	propriétaire = em.merge(propriétaire);
	Location emplacement = new Location(NOM);
	emplacement = em.merge(emplacement);
	Cartoonist dessinateur = new Cartoonist(NOM);
	dessinateur = em.merge(dessinateur);
	ScriptWriter scénariste = new ScriptWriter(NOM);
	scénariste = em.merge(scénariste);
	final Integer numéro = Integer.valueOf(12);

	bandeDessinée.setSeries(série);
	bandeDessinée.setComment(commentaire);
	bandeDessinée.setPicture(image);
	bandeDessinée.setOwner(propriétaire);
	bandeDessinée.setLocation(emplacement);
	bandeDessinée.setCartoonist(dessinateur);
	bandeDessinée.setScriptWriter(scénariste);
	bandeDessinée.setNumber(numéro);
	bandeDessinée.toucher(éditeur);
	final Date édition = bandeDessinée.getLastModification();

	bandeDessinée = em.merge(bandeDessinée);

	assertEquals(id, bandeDessinée.getId());
	assertEquals(titre, bandeDessinée.getTitle());
	assertEquals(série, bandeDessinée.getSeries());
	assertEquals(commentaire, bandeDessinée.getComment());
	assertEquals(image, bandeDessinée.getPicture());
	assertEquals(propriétaire, bandeDessinée.getOwner());
	assertEquals(emplacement, bandeDessinée.getLocation());
	assertEquals(créateur, bandeDessinée.getCreator());
	assertEquals(création, bandeDessinée.getCreation());
	assertEquals(éditeur, bandeDessinée.getLastModifier());
	assertEquals(édition, bandeDessinée.getLastModification());
	assertEquals(dessinateur, bandeDessinée.getCartoonist());
	assertEquals(scénariste, bandeDessinée.getScriptWriter());
	assertEquals(numéro, bandeDessinée.getNumber());
    }

    /**
     * Teste qu’on peut enregistrer un film en base. On ne vérifie pas les
     * champs hérités de {@code Record}, car l’enregistrement de ceux-ci est
     * vérifié dans {@link #enregistrementBandeDessinée()}.
     */
    @Test
    public void enregistrementFilm() {
	logger.info("Test d’enregistrement d’un film.");

	final String titre = "Un super film";
	User créateur = new User("Créateur", "createur@email");
	User éditeur = new User("Éditeur", "editeur@email");
	créateur = em.merge(créateur);
	éditeur = em.merge(éditeur);
	Movie film = new Movie(titre, créateur, BRD);

	film = em.merge(film);

	assertEquals(BRD, film.getSupport());
	assertNull(film.getDirector());
	assertNotNull(film.getActors());
	assertTrue(film.getActors().isEmpty());
	assertNull(film.getComposer());
	assertNull(film.getActionStyle());
	assertNull(film.getDocumentaryStyle());
	assertNull(film.getFantasyStyle());
	assertNull(film.getWarStyle());
	assertNull(film.getTrueStoryStyle());
	assertNull(film.getHistoricalStyle());
	assertNull(film.getHumorStyle());
	assertNull(film.getDetectiveStyle());
	assertNull(film.getRomanticStyle());
	assertNull(film.getSfStyle());

	Director réalisateur = new Director(NOM);
	réalisateur = em.merge(réalisateur);
	Actor acteur1 = new Actor("Très bon acteur");
	acteur1 = em.merge(acteur1);
	Actor acteur2 = new Actor("Acteur moyen");
	acteur2 = em.merge(acteur2);
	Actor acteur3 = new Actor("Mauvais acteur");
	acteur3 = em.merge(acteur3);
	Composer compositeur = new Composer(NOM);
	compositeur = em.merge(compositeur);

	film.setDirector(réalisateur);
	film.getActors().add(acteur1);
	film.getActors().add(acteur2);
	film.getActors().add(acteur3);
	film.setComposer(compositeur);
	film.setActionStyle(Boolean.TRUE);
	film.setDocumentaryStyle(Boolean.FALSE);
	film.setFantasyStyle(Boolean.TRUE);
	film.setWarStyle(Boolean.FALSE);
	film.setTrueStoryStyle(Boolean.TRUE);
	film.setHistoricalStyle(Boolean.FALSE);
	film.setHumorStyle(Boolean.TRUE);
	film.setDetectiveStyle(Boolean.FALSE);
	film.setRomanticStyle(Boolean.TRUE);
	film.setSfStyle(Boolean.FALSE);
	film.toucher(éditeur);

	film = em.merge(film);

	assertEquals(réalisateur, film.getDirector());
	assertNotNull(film.getActors());
	assertEquals(TROIS, film.getActors().size());
	assertTrue(film.getActors().contains(acteur1));
	assertTrue(film.getActors().contains(acteur2));
	assertTrue(film.getActors().contains(acteur3));
	assertEquals(compositeur, film.getComposer());
	assertEquals(Boolean.TRUE, film.getActionStyle());
	assertEquals(Boolean.FALSE, film.getDocumentaryStyle());
	assertEquals(Boolean.TRUE, film.getFantasyStyle());
	assertEquals(Boolean.FALSE, film.getWarStyle());
	assertEquals(Boolean.TRUE, film.getTrueStoryStyle());
	assertEquals(Boolean.FALSE, film.getHistoricalStyle());
	assertEquals(Boolean.TRUE, film.getHumorStyle());
	assertEquals(Boolean.FALSE, film.getDetectiveStyle());
	assertEquals(Boolean.TRUE, film.getRomanticStyle());
	assertEquals(Boolean.FALSE, film.getSfStyle());
    }

    /**
     * Teste qu’on peut enregistrer un livre en base. On ne vérifie pas les
     * champs hérités de {@code Record} car l’enregistrement de ceux-ci est
     * vérifié dans {@link #enregistrementBandeDessinée()}.
     */
    @Test
    public void enregistrementLivre() {
	logger.info("Test d’enregistrement d’un livre.");

	final String titre = "L’Assassin royal";
	User créateur = new User("Créateur", "createur@email");
	User éditeur = new User("Éditeur", "editeur@email");
	créateur = em.merge(créateur);
	éditeur = em.merge(éditeur);
	Book livre = new Book(titre, créateur);

	livre = em.merge(livre);

	assertNull(livre.getAuthor());
	assertNull(livre.getFantasyStyle());
	assertNull(livre.getTrueStoryStyle());
	assertNull(livre.getHistoricalStyle());
	assertNull(livre.getHumorStyle());
	assertNull(livre.getDetectiveStyle());
	assertNull(livre.getRomanticStyle());
	assertNull(livre.getSfStyle());

	Author auteur = new Author("Robin Hobb");
	auteur = em.merge(auteur);

	livre.setAuthor(auteur);
	livre.setFantasyStyle(Boolean.TRUE);
	livre.setTrueStoryStyle(Boolean.FALSE);
	livre.setHistoricalStyle(Boolean.TRUE);
	livre.setHumorStyle(Boolean.FALSE);
	livre.setDetectiveStyle(Boolean.TRUE);
	livre.setRomanticStyle(Boolean.FALSE);
	livre.setSfStyle(Boolean.TRUE);

	livre = em.merge(livre);

	assertEquals(auteur, livre.getAuthor());
	assertEquals(Boolean.TRUE, livre.getFantasyStyle());
	assertEquals(Boolean.FALSE, livre.getTrueStoryStyle());
	assertEquals(Boolean.TRUE, livre.getHistoricalStyle());
	assertEquals(Boolean.FALSE, livre.getHumorStyle());
	assertEquals(Boolean.TRUE, livre.getDetectiveStyle());
	assertEquals(Boolean.FALSE, livre.getRomanticStyle());
	assertEquals(Boolean.TRUE, livre.getSfStyle());
    }

    /**
     * Teste qu’on peut enregistrer un mot en base.
     */
    @Test
    public void enregistrementMot() {
	logger.info("Test d’enregistrement d’un mot.");
	Word mot = new Word(MOT);
	assertNull(mot.getId());
	mot = em.merge(mot);
	em.flush();
	assertNotNull(mot.getId());
	assertEquals(MOT, mot.getValue());
    }

    /**
     * Teste qu’on peut enregistrer une référence en base.
     */
    @Test
    public void enregistrementRéférence() {
	logger.info("Test d’enregistrement d’une référence.");

	final String titre = "Titre";
	final Field champ = Field.TITLE;
	User créateur = new User(UTILISATEUR, EMAIL);
	créateur = em.merge(créateur);
	Record fiche = new Movie(titre, créateur, BRD);
	fiche = em.merge(fiche);
	Word mot = new Word(MOT);
	mot = em.merge(mot);
	Reference référence = new Reference(mot, champ, fiche);
	assertNull(référence.getId());

	référence = em.merge(référence);
	em.flush();

	assertNotNull(référence.getId());
	assertEquals(mot, référence.getWord());
	assertEquals(champ, référence.getField());
	assertEquals(fiche, référence.getRecord());
    }

    /**
     * Teste l’enregistrement de deux acteurs ayant le même nom. Comme deux
     * acteurs ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxActeursIdentiques() {
	logger.info("Test de l’enregistrement de deux acteurs identiques.");
	em.merge(new Actor(NOM));
	em.merge(new Actor(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux auteurs ayant le même nom. Comme deux
     * auteurs ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxAuteursIdentiques() {
	logger.info("Test de l’enregistrement de deux auteurs identiques.");
	em.merge(new Author(NOM));
	em.merge(new Author(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux compositeurs ayant le même nom. Comme deux
     * compositeurs ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxCompositeursIdentiques() {
	logger.info("Test de l’enregistrement de deux compositeurs "
		+ "identiques.");
	em.merge(new Composer(NOM));
	em.merge(new Composer(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux dessinateurs ayant le même nom. Comme deux
     * dessinateurs ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxDessinateursIdentiques() {
	logger.info("Test de l’enregistrement de deux dessinateurs "
		+ "identiques");
	em.merge(new Cartoonist(NOM));
	em.merge(new Cartoonist(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux emplacements ayant le même nom. Comme deux
     * emplacements ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxEmplacementsIdentiques() {
	logger.info("Test de l’enregistrement de deux emplacements "
		+ "identiques");
	em.merge(new Location(NOM));
	em.merge(new Location(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux propriétaires ayant le même nom. Comme
     * deux propriétaires ne peuvent avoir le même nom, une erreur doit se
     * produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxPropriétairesIdentiques() {
	logger.info("Test de l’enregistrement de deux propriétaires "
		+ "identiques.");
	em.merge(new Owner(NOM));
	em.merge(new Owner(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux réalisateurs ayant le même nom. Comme deux
     * réalisateurs ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxRéalisateursIdentiques() {
	logger.info("Test de l’enregistrement de deux réalisateurs "
		+ "identiques.");
	em.merge(new Director(NOM));
	em.merge(new Director(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux scénaristes ayant le même nom. Comme deux
     * scénaristes ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxScénaristesIdentiques() {
	logger.info("Test de l’enregistrement de deux scénaristes "
		+ "identiques.");
	em.merge(new ScriptWriter(NOM));
	em.merge(new ScriptWriter(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux séries ayant le même nom. Comme deux
     * séries ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxSériesIdentiques() {
	logger.info("Test de l’enregistrement de deux séries identiques.");
	em.merge(new Series(NOM));
	em.merge(new Series(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux utilisateurs ayant le même nom. Comme deux
     * utilisateurs ne peuvent avoir le même nom, une erreur doit se produire.
     */
    @Test(expected = PersistenceException.class)
    public void deuxUtilisateursIdentiques() {
	logger.info("Test de l’enregistrement de deux utilisateurs "
		+ "identiques.");
	em.merge(new User(UTILISATEUR, "email1@email"));
	em.merge(new User(UTILISATEUR, "email2@email"));
	em.flush();
    }

    /**
     * Teste l’enregistrement de nommés ayant le même nom mais venant tous
     * d’implémentations différentes. Dans ce cas, il ne doit y avoir aucune
     * erreur.
     */
    @Test
    public void nommésDifférentsAvecLeMêmeNom() {
	logger.info("Test de l’enregistrement de nommés différents mais avec "
		+ "le même nom.");
	em.merge(new Actor(NOM));
	em.merge(new Author(NOM));
	em.merge(new Composer(NOM));
	em.merge(new Cartoonist(NOM));
	em.merge(new Location(NOM));
	em.merge(new Owner(NOM));
	em.merge(new Director(NOM));
	em.merge(new ScriptWriter(NOM));
	em.merge(new Series(NOM));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux mots identiques. La base de données doit
     * générer une erreur dans ce cas.
     */
    @Test(expected = PersistenceException.class)
    public void deuxMotsIdentiques() {
	logger.info("Test de l’enregistrement de deux mots identiques.");
	em.merge(new Word(MOT));
	em.merge(new Word(MOT));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux références identiques. La base de données
     * doit générer une erreur dans ce cas.
     */
    @Test(expected = PersistenceException.class)
    public void deuxRéférencesIdentiques() {
	logger.info("Test de l’enregistrement de deux références identiques.");

	final String titre = "Titre";
	final Field champ = Reference.Field.TITLE;
	User créateur = new User(UTILISATEUR, EMAIL);
	créateur = em.merge(créateur);
	Record fiche = new Movie(titre, créateur, BRD);
	fiche = em.merge(fiche);
	Word mot = new Word(MOT);
	mot = em.merge(mot);

	em.merge(new Reference(mot, champ, fiche));
	em.merge(new Reference(mot, champ, fiche));
	em.flush();
    }

    /**
     * Teste l’enregistrement de deux fiches ayant le même titre. C’est autorisé
     * et la base de données ne doit donc générer aucune erreur.
     */
    @Test
    public void deuxFichesMêmeTitre() {
	logger.info("Test de l’enregistrement de deux fiches ayant le même"
		+ " titre.");

	final String titre = "Lettres d’Iwo Jima";
	final User créateur =
		em.merge(new User(UTILISATEUR, EMAIL));
	final Record fiche1 = new Movie(titre, créateur, BRD);
	final Record fiche2 = new Comic(titre, créateur);

	em.merge(fiche1);
	em.merge(fiche2);
	em.flush();
    }

    /**
     * Charge de la base de données tous les objets dont le type est donné en
     * argument.
     *
     * @param type
     *            classe des objets à charger.
     * @param <T>
     *            type des objets à charger.
     * @param ordre
     *            attribut selon lequel trier les objets à charger.
     * @return tous les objets de la classe {@code type} présents en base, triés
     *         suivant leur attribut {@code ordre} croissant.
     */
    private <T> Iterator<T> charger(final Class<T> type, final String ordre) {
	final CriteriaQuery<T> query = builder.createQuery(type);
	final Root<T> root = query.from(type);
	query.orderBy(builder.asc(root.get(ordre)));
	return em.createQuery(query).getResultList().iterator();
    }

    /**
     * Charge de la base de donnée tous les nommés dont le type est donné en
     * argument.
     *
     * @param type
     *            classe des objets à charger.
     * @param <T>
     *            type des objets à charger.
     * @return tous les objets de la classe {@code type} présents en base, triés
     *         par noms croissants.
     */
    private <T extends Named> Iterator<T> chargerNommés(final Class<T> type) {
	return charger(type, "name");
    }

    /**
     * Teste le chargement des acteurs insérés dans la base de données par le
     * fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerActeurs() {
	logger.info("Chargement des acteurs de test-data.sql.");
	final Iterator<Actor> acteurs = chargerNommés(Actor.class);

	final Actor antonyHead = acteurs.next();
	assertEquals(CINQ, antonyHead.getId().intValue());
	assertEquals("Anthony Head", antonyHead.getName());

	final Actor bradleyJames = acteurs.next();
	assertEquals(SEPT, bradleyJames.getId().intValue());
	assertEquals("Bradley James", bradleyJames.getName());

	final Actor colinMorgan = acteurs.next();
	assertEquals(HUIT, colinMorgan.getId().intValue());
	assertEquals("Colin Morgan", colinMorgan.getName());

	final Actor emmaWatson = acteurs.next();
	assertEquals(TROIS, emmaWatson.getId().intValue());
	assertEquals("Emma Watson", emmaWatson.getName());

	final Actor evaMendes = acteurs.next();
	assertEquals(UN, evaMendes.getId().intValue());
	assertEquals("Eva Mendes", evaMendes.getName());

	final Actor georgeClooney = acteurs.next();
	assertEquals(DEUX, georgeClooney.getId().intValue());
	assertEquals("George Clooney", georgeClooney.getName());

	final Actor marilynMonroe = acteurs.next();
	assertEquals(SIX, marilynMonroe.getId().intValue());
	assertEquals("Marilyn Monroe", marilynMonroe.getName());

	final Actor scarlettJohansson = acteurs.next();
	assertEquals(QUATRE, scarlettJohansson.getId().intValue());
	assertEquals("Scarlett Johansson", scarlettJohansson.getName());

	final Actor willSmith = acteurs.next();
	assertEquals(ZÉRO, willSmith.getId().intValue());
	assertEquals("Will Smith", willSmith.getName());

	assertFalse(acteurs.hasNext());
    }

    /**
     * Teste le chargement des auteurs insérés dans la base de données par le
     * fichier src/test/resources/test-data.sql.
     */
    @Test
    public void charcherAuteurs() {
	logger.info("Chargement des auteurs de test-data.sql.");
	final Iterator<Author> auteurs = chargerNommés(Author.class);

	final Author gavThorpe = auteurs.next();
	assertEquals(TROIS, gavThorpe.getId().intValue());
	assertEquals("Gav Thorpe", gavThorpe.getName());

	final Author asimov = auteurs.next();
	assertEquals(UN, asimov.getId().intValue());
	assertEquals("Isaac Asimov", asimov.getName());

	final Author grisham = auteurs.next();
	assertEquals(DEUX, grisham.getId().intValue());
	assertEquals("John Grisham", grisham.getName());

	final Author noickKyme = auteurs.next();
	assertEquals(QUATRE, noickKyme.getId().intValue());
	assertEquals("Noick Kyme", noickKyme.getName());

	final Author clancy = auteurs.next();
	assertEquals(ZÉRO, clancy.getId().intValue());
	assertEquals("Tom Clancy", clancy.getName());

	assertFalse(auteurs.hasNext());
    }

    /**
     * Teste le chargement des compositeurs insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerCompositeurs() {
	logger.info("Chargement des compositeurs de test-data.sql.");
	final Iterator<Composer> compositeurs =
		chargerNommés(Composer.class);

	final Composer hansZimmer = compositeurs.next();
	assertEquals(UN, hansZimmer.getId().intValue());
	assertEquals("Hans Zimmer", hansZimmer.getName());

	final Composer howardShore = compositeurs.next();
	assertEquals(ZÉRO, howardShore.getId().intValue());
	assertEquals("Howard Shore", howardShore.getName());

	final Composer lisaGerrard = compositeurs.next();
	assertEquals(DEUX, lisaGerrard.getId().intValue());
	assertEquals("Lisa Gerrard", lisaGerrard.getName());

	assertFalse(compositeurs.hasNext());
    }

    /**
     * Teste le chargement des dessinateurs insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerDessinateurs() {
	logger.info("Chargement des dessinateurs de test-data.sql.");
	final Iterator<Cartoonist> dessinateurs =
		chargerNommés(Cartoonist.class);

	final Cartoonist alainHenriet = dessinateurs.next();
	assertEquals(UN, alainHenriet.getId().intValue());
	assertEquals("Alain Henriet", alainHenriet.getName());

	final Cartoonist jigounov = dessinateurs.next();
	assertEquals(ZÉRO, jigounov.getId().intValue());
	assertEquals("Jigounov", jigounov.getName());

	final Cartoonist morris = dessinateurs.next();
	assertEquals(DEUX, morris.getId().intValue());
	assertEquals("Morris", morris.getName());

	assertFalse(dessinateurs.hasNext());
    }

    /**
     * Teste le chargement des emplacements insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerEmplacement() {
	logger.info("Chargement des emplacements de test-data.sql.");
	final Iterator<Location> emplacements =
		chargerNommés(Location.class);

	final Location laRocheSurYon = emplacements.next();
	assertEquals(UN, laRocheSurYon.getId().intValue());
	assertEquals("La Roche sur Yon", laRocheSurYon.getName());

	final Location lyon = emplacements.next();
	assertEquals(DEUX, lyon.getId().intValue());
	assertEquals("Lyon", lyon.getName());

	final Location poissy = emplacements.next();
	assertEquals(QUATRE, poissy.getId().intValue());
	assertEquals("Poissy", poissy.getName());

	final Location singapour = emplacements.next();
	assertEquals(TROIS, singapour.getId().intValue());
	assertEquals("Singapour", singapour.getName());

	final Location verneuil = emplacements.next();
	assertEquals(ZÉRO, verneuil.getId().intValue());
	assertEquals("Verneuil", verneuil.getName());

	assertFalse(emplacements.hasNext());
    }

    /**
     * Teste le chargement des propriétaires insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerPropriétaires() {
	logger.info("Chargement des propriétaires de test-data.sql.");
	final Iterator<Owner> propriétaires =
		chargerNommés(Owner.class);

	final Owner claire = propriétaires.next();
	assertEquals(DEUX, claire.getId().intValue());
	assertEquals("Claire", claire.getName());

	final Owner etienne = propriétaires.next();
	assertEquals(ZÉRO, etienne.getId().intValue());
	assertEquals("Etienne", etienne.getName());

	final Owner grégoire = propriétaires.next();
	assertEquals(UN, grégoire.getId().intValue());
	assertEquals("Grégoire", grégoire.getName());

	assertFalse(propriétaires.hasNext());
    }

    /**
     * Teste le chargement des réalisateurs insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerRéalisateurs() {
	logger.info("Chargement des réalisateurs de test-data.sql.");
	final Iterator<Director> réalisateurs =
		chargerNommés(Director.class);

	final Director georgeLucas = réalisateurs.next();
	assertEquals(UN, georgeLucas.getId().intValue());
	assertEquals("George Lucas", georgeLucas.getName());

	final Director lucBesson = réalisateurs.next();
	assertEquals(DEUX, lucBesson.getId().intValue());
	assertEquals("Luc Besson", lucBesson.getName());

	final Director stevenSpielberg = réalisateurs.next();
	assertEquals(ZÉRO, stevenSpielberg.getId().intValue());
	assertEquals("Steven Spielberg", stevenSpielberg.getName());

	assertFalse(réalisateurs.hasNext());
    }

    /**
     * Teste le chargement des scénaristes insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerScénaristes() {
	logger.info("Chargement des scénaristes de test-data.sql.");
	final Iterator<ScriptWriter> scénaristes =
		chargerNommés(ScriptWriter.class);

	final ScriptWriter vanHamme = scénaristes.next();
	assertEquals(ZÉRO, vanHamme.getId().intValue());
	assertEquals("Jean Van Hamme", vanHamme.getName());

	final ScriptWriter renard = scénaristes.next();
	assertEquals(UN, renard.getId().intValue());
	assertEquals("Renard", renard.getName());

	final ScriptWriter renéGoscinny = scénaristes.next();
	assertEquals(DEUX, renéGoscinny.getId().intValue());
	assertEquals("René Goscinny", renéGoscinny.getName());

	assertFalse(scénaristes.hasNext());
    }

    /**
     * Teste le chargement des séries insérées dans la base de données par le
     * fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerSéries() {
	logger.info("Chargement des séries de test-data.sql.");
	final Iterator<Series> séries = chargerNommés(Series.class);

	final Series bouleEtBill = séries.next();
	assertEquals(ZÉRO, bouleEtBill.getId().intValue());
	assertEquals("Boule et Bill", bouleEtBill.getName());

	final Series harryPotter = séries.next();
	assertEquals(TROIS, harryPotter.getId().intValue());
	assertEquals("Harry Potter", harryPotter.getName());

	final Series lukyLuke = séries.next();
	assertEquals(QUATRE, lukyLuke.getId().intValue());
	assertEquals("Luky Luke", lukyLuke.getName());

	final Series merlin = séries.next();
	assertEquals(UN, merlin.getId().intValue());
	assertEquals("Merlin", merlin.getName());

	final Series warhammer40k = séries.next();
	assertEquals(DEUX, warhammer40k.getId().intValue());
	assertEquals("Warhammer 40,000", warhammer40k.getName());

	assertFalse(séries.hasNext());
    }

    /**
     * Teste le chargement des utilisateurs insérés dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerUtilisateurs() {
	logger.info("Chargement des utilisateurs de test-data.sql.");
	final Iterator<User> utilisateurs =
		charger(User.class, "id");

	final User etienne = utilisateurs.next();
	assertEquals(ZÉRO, etienne.getId().intValue());
	assertEquals("Etienne", etienne.getName());
	assertEquals("etienne@email", etienne.getEmail());

	final User grégoire = utilisateurs.next();
	assertEquals(UN, grégoire.getId().intValue());
	assertEquals("Grégoire", grégoire.getName());
	assertEquals("gregoire@email", grégoire.getEmail());

	final User claire = utilisateurs.next();
	assertEquals(DEUX, claire.getId().intValue());
	assertEquals("Claire", claire.getName());
	assertEquals("claire@email", claire.getEmail());

	assertFalse(utilisateurs.hasNext());
    }

    /**
     * Teste le chargement des fiches insérées dans la base de données par le
     * fichier src/test/resources/test-data.sql. On teste tous les champs pour
     * une BD, un film et un livre (chaque implémentation de {@link Record}).
     * Pour les autres on se contente de l’id et du titre.
     *
     * @throws ParseException
     *             Jamais.
     */
    @Test
    public void chargerFiches() throws ParseException {
	logger.info("Chargement des fiches de test-data.sql.");
	final Iterator<Record> fiches = charger(Record.class, "title");
	logger.debug("Fiches chargées.");

	final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	final Comic alerteAuxPiedsBleus = (Comic) fiches.next();
	assertEquals(SIX, alerteAuxPiedsBleus.getId().intValue());
	assertEquals("Alerte aux Pieds-Bleus", alerteAuxPiedsBleus.getTitle());

	final Comic bouleEtBill = (Comic) fiches.next();
	assertEquals(ZÉRO, bouleEtBill.getId().intValue());
	assertEquals("Globe-trotters", bouleEtBill.getTitle());
	assertEquals("Boule et Bill", bouleEtBill.getSeries().getName());
	assertNull(bouleEtBill.getComment());
	assertNull(bouleEtBill.getPicture());
	assertEquals("Claire", bouleEtBill.getOwner().getName());
	assertEquals("Verneuil", bouleEtBill.getLocation().getName());
	assertEquals("Etienne", bouleEtBill.getCreator().getName());
	assertEquals(df.parse("2012-12-25 22:18:30"),
		bouleEtBill.getCreation());
	assertEquals("Grégoire", bouleEtBill.getLastModifier().getName());
	assertEquals(df.parse("2013-02-16 22:19:58"),
		bouleEtBill.getLastModification());
	assertEquals("Jigounov", bouleEtBill.getCartoonist().getName());
	assertEquals("Renard", bouleEtBill.getScriptWriter().getName());
	assertEquals(DOUZE, bouleEtBill.getNumber().intValue());

	final Book laChuteDeDamnos = (Book) fiches.next();
	assertEquals(CINQ, laChuteDeDamnos.getId().intValue());
	assertEquals("La Chute de Damnos", laChuteDeDamnos.getTitle());

	final Comic laFiancéeDeLukyLuke = (Comic) fiches.next();
	assertEquals(SEPT, laFiancéeDeLukyLuke.getId().intValue());
	assertEquals("La Fiancée de Luky Luke", laFiancéeDeLukyLuke.getTitle());

	final Book laPurgeDeKadillus = (Book) fiches.next();
	assertEquals(QUATRE, laPurgeDeKadillus.getId().intValue());
	assertEquals("La Purge de Kadillus", laPurgeDeKadillus.getTitle());

	final Comic lePonyExpress = (Comic) fiches.next();
	assertEquals(HUIT, lePonyExpress.getId().intValue());
	assertEquals("Le Pony Express", lePonyExpress.getTitle());

	final Movie lucy = (Movie) fiches.next();
	assertEquals(TROIS, lucy.getId().intValue());
	assertEquals("Lucy", lucy.getTitle());

	final Movie lukyMarines = (Movie) fiches.next();
	assertEquals(NEUF, lukyMarines.getId().intValue());
	assertEquals("Luky Marines", lukyMarines.getTitle());

	final Movie merlin = (Movie) fiches.next();
	assertEquals(UN, merlin.getId().intValue());
	assertEquals("Merlin, Saison 1", merlin.getTitle());
	assertEquals("Merlin", merlin.getSeries().getName());
	assertEquals("Une super série !", merlin.getComment());
	assertNull(merlin.getPicture());
	assertEquals("Etienne", merlin.getOwner().getName());
	assertEquals("Verneuil", merlin.getLocation().getName());
	assertEquals("Etienne", merlin.getCreator().getName());
	assertEquals(df.parse("2012-12-25 22:21:29"), merlin.getCreation());
	assertEquals("Claire", merlin.getLastModifier().getName());
	assertEquals(df.parse("2013-02-26 22:22:06"),
		merlin.getLastModification());
	assertEquals(Support.BRD, merlin.getSupport());
	assertNull(merlin.getDirector());
	assertEquals("Howard Shore", merlin.getComposer().getName());
	assertEquals(DEUX, merlin.getActors().size());

	final Book rainbowSix = (Book) fiches.next();
	assertEquals(DEUX, rainbowSix.getId().intValue());
	assertEquals("Rainbow Six", rainbowSix.getTitle());
	assertNull(rainbowSix.getSeries());
	assertNull(rainbowSix.getComment());
	assertNull(rainbowSix.getPicture());
	assertEquals("Etienne", rainbowSix.getOwner().getName());
	assertEquals("Verneuil", rainbowSix.getLocation().getName());
	assertEquals("Etienne", rainbowSix.getCreator().getName());

	assertFalse(fiches.hasNext());
    }

    /**
     * Teste le chargement des mots insérés dans la base de données par le
     * fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerMots() {
	logger.info("Chargement des mots de test-data.sql.");
	final Iterator<Word> mots = charger(Word.class, "value");

	final Word série = mots.next();
	assertEquals(DEUX, série.getId().intValue());
	assertEquals("série", série.getValue());

	final Word _super = mots.next();
	assertEquals(UN, _super.getId().intValue());
	assertEquals("super", _super.getValue());

	final Word une = mots.next();
	assertEquals(ZÉRO, une.getId().intValue());
	assertEquals("une", une.getValue());

	assertFalse(mots.hasNext());
    }

    /**
     * Teste le chargement des références insérées dans la base de données par
     * le fichier src/test/resources/test-data.sql.
     */
    @Test
    public void chargerRéférences() {
	logger.info("Chargement des références de test-data.sql.");
	final Iterator<Reference> références = charger(Reference.class, "id");
	logger.debug("Références chargées.");

	{
	    final Reference référence = références.next();
	    assertEquals(ZÉRO, référence.getId().intValue());
	    assertEquals("une", référence.getWord().getValue());
	    assertEquals(Field.COMMENT, référence.getField());
	    assertEquals(UN, référence.getRecord().getId().intValue());
	}

	{
	    final Reference référence = références.next();
	    assertEquals(UN, référence.getId().intValue());
	    assertEquals("super", référence.getWord().getValue());
	    assertEquals(Field.COMMENT, référence.getField());
	    assertEquals(UN, référence.getRecord().getId().intValue());
	}

	{
	    final Reference référence = références.next();
	    assertEquals(DEUX, référence.getId().intValue());
	    assertEquals("série", référence.getWord().getValue());
	    assertEquals(Field.COMMENT, référence.getField());
	    assertEquals(UN, référence.getRecord().getId().intValue());
	}

	assertFalse(références.hasNext());
    }

    /**
     * Teste la désindexation d’une fiche via la requête nommée
     * « désindexerFiche » définie dans la classe {@link Reference}.
     */
    @Test
    public void désindexerFiches() {
	logger.info("Désindexations des fiches.");

	/* Désindexation de la fiche 0, qui n’a pas de références. */
	final Record fiche0 = em.getReference(Record.class, Long.valueOf(ZÉRO));
	final Query désindexerFiche0 = em.createNamedQuery("unindexRecord");
	désindexerFiche0.setParameter("record", fiche0);
	assertEquals(ZÉRO, désindexerFiche0.executeUpdate());

	/* Désindexation de la fiche 1, qui a trois références. */
	final Record fiche1 = em.getReference(Record.class, Long.valueOf(UN));
	final Query désindexerFiche1 = em.createNamedQuery("unindexRecord");
	désindexerFiche1.setParameter("record", fiche1);
	assertEquals(TROIS, désindexerFiche1.executeUpdate());
    }

}