package fr.elimerl.registre.controllers;

import fr.elimerl.registre.commands.RecordCommand;
import fr.elimerl.registre.entities.Book;
import fr.elimerl.registre.entities.Comic;
import fr.elimerl.registre.entities.Movie;
import fr.elimerl.registre.entities.Record;
import fr.elimerl.registre.entities.User;
import fr.elimerl.registre.security.RAuthenticationToken;
import fr.elimerl.registre.services.RegistreEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller responsible for record management: create, edit, display.
 */
@Controller
@RequestMapping("/Fiche")
public class RecordManager {

  private static final Logger logger =
      LoggerFactory.getLogger (RecordManager.class);

  /**
   * JPA entity manager, provided by Spring.
   */
  @PersistenceContext(name = "Registre")
  private EntityManager em;

  @Autowired
  private RegistreEntityManager rem;

  /**
   * Display a record in read-only mode.
   *
   * @param id
   *          id of the record to display.
   * @param response
   *          the HTTP response to send to the UA.
   * @return the name of the view to display.
   */
  @RequestMapping(value = "/{id}", method = GET)
  @Transactional(readOnly = true)
  public ModelAndView display (@PathVariable final Long id,
      final HttpServletResponse response) {
    final Record record = em.find (Record.class, id);
    final Map<String, Object> model = new HashMap<> ();
    model.put ("record", record);
    final String view;
    if (record == null) {
      view = "records/notFound";
      response.setStatus (SC_NOT_FOUND);
    } else if (record instanceof Movie) {
      view = "records/movie";
    } else if (record instanceof Book) {
      view = "records/book";
    } else if (record instanceof Comic) {
      view = "records/comic";
    } else {
      view = "records/unknownType";
      response.setStatus (SC_NOT_IMPLEMENTED);
    }
    return new ModelAndView (view, model);
  }

  /**
   * Get the record editor, empty.
   *
   * @return the name of the view to display.
   */
  @GetMapping
  public ModelAndView getEditor () {
    return new ModelAndView ("records/editor");
  }

  @PostMapping
  @Transactional
  public ModelAndView create (
      RecordCommand command,
      @AuthenticationPrincipal RAuthenticationToken token
  ) {
    User user = token.getPrincipal ();
    logger.info ("{} creates a new {}.", user, command.getType ());
    logger.debug ("EntityManger: {}", em);
    Record record;
    switch (command.getType ()) {
      case movie:
        record = createMovie (command, user);
        break;
      case comic:
        record = createComic (command, user);
        break;
      case book:
        record = createBook (command, user);
        break;
      default:
        throw new RuntimeException ("Unknown type: " + command.getType ());
    }
    if (isNotBlank (command.getSeries ())) {
      record.setSeries (rem.supplySeries (command.getSeries ()));
    }
    if (isNotBlank (command.getOwner ())) {
      record.setOwner (rem.supplyOwner (command.getOwner ()));
    }
    if (isNotBlank (command.getLocation ())) {
      record.setLocation (rem.supplyLocation (command.getLocation ()));
    }
    record.setComment (command.getComment ());
    record = em.merge (record);
    return new ModelAndView (
        "records/editor",
        singletonMap ("record", record)
    );
  }

  private Record createMovie (RecordCommand command, User user) {
    Movie movie = new Movie (command.getTitle (), user, command.getSupport ());
    if (isNotBlank (command.getDirector ())) {
      movie.setDirector (rem.supplyDirector (command.getDirector ()));
    }
    if (command.getActors () != null) {
      command.getActors ()
          .stream ()
          .filter (this::isNotBlank)
          .map (rem::supplyActor)
          .forEach (movie.getActors ()::add);
    }
    if (isNotBlank (command.getComposer ())) {
      movie.setComposer (rem.supplyComposer (command.getComposer ()));
    }
    return movie;
  }

  private Record createComic (RecordCommand command, User user) {
    Comic comic = new Comic (command.getTitle (), user);
    if (isNotBlank (command.getCartoonist ())) {
      comic.setCartoonist (rem.supplyCartoonist (command.getCartoonist ()));
    }
    if (isNotBlank (command.getScriptWriter ())) {
      comic.setScriptWriter (rem.supplyScriptWriter (command.getScriptWriter ()));
    }
    return comic;
  }

  private Record createBook (RecordCommand command, User user) {
    Book book = new Book (command.getTitle (), user);
    if (isNotBlank (command.getAuthor ())) {
      book.setAuthor (rem.supplyAuthor (command.getAuthor ()));
    }
    return book;
  }

  private boolean isNotBlank (String string) {
    return string != null && !string.trim ().isEmpty ();
  }

}
