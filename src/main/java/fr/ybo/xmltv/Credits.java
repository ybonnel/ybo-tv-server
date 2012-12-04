
package fr.ybo.xmltv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "director",
    "actor",
    "writer",
    "adapter",
    "producer",
    "composer",
    "editor",
    "presenter",
    "commentator",
    "guest"
})
@XmlRootElement(name = "credits")
public class Credits implements Serializable {

    @JsonIgnore
    protected List<Director> director;

    @JsonProperty("directors")
    public List<String> getDirectors() {
        List<String> directors = new ArrayList<String>(getDirector().size());
        for (Director oneDirector : getDirector()) {
            directors.add(oneDirector.getvalue());
        }
        return directors;
    }


    @JsonIgnore
    protected List<Actor> actor;

    @JsonProperty("actors")
    public List<String> getActors() {
        List<String> actors = new ArrayList<String>(getActor().size());
        for (Actor oneActor : getActor()) {
            actors.add(oneActor.getvalue());
        }
        return actors;
    }

    @JsonIgnore
    protected List<Writer> writer;

    @JsonProperty("writers")
    public List<String> getWriters() {
        List<String> writers = new ArrayList<String>(getWriter().size());
        for (Writer oneWriter : getWriter()) {
            writers.add(oneWriter.getvalue());
        }
        return writers;
    }

    @JsonIgnore
    protected List<Adapter> adapter;

    @JsonProperty("adapters")
    public List<String> getAdapters() {
        List<String> adapters = new ArrayList<String>(getAdapter().size());
        for (Adapter oneAdapter : getAdapter()) {
            adapters.add(oneAdapter.getvalue());
        }
        return adapters;
    }

    @JsonIgnore
    protected List<Producer> producer;

    @JsonProperty("producers")
    public List<String> getProducers() {
        List<String> producers = new ArrayList<String>(getProducer().size());
        for (Producer oneProducer : getProducer()) {
            producers.add(oneProducer.getvalue());
        }
        return producers;
    }

    @JsonIgnore
    protected List<Composer> composer;

    @JsonProperty("composers")
    public List<String> getComposers() {
        List<String> composers = new ArrayList<String>(getComposer().size());
        for (Composer oneComposer : getComposer()) {
            composers.add(oneComposer.getvalue());
        }
        return composers;
    }

    @JsonIgnore
    protected List<Editor> editor;

    @JsonProperty("editors")
    public List<String> getEditors() {
        List<String> editors = new ArrayList<String>(getEditor().size());
        for (Editor oneEditor : getEditor()) {
            editors.add(oneEditor.getvalue());
        }
        return editors;
    }

    @JsonIgnore
    protected List<Presenter> presenter;

    @JsonProperty("presenters")
    public List<String> getPresenters() {
        List<String> presenters = new ArrayList<String>(getPresenter().size());
        for (Presenter onePresenter : getPresenter()) {
            presenters.add(onePresenter.getvalue());
        }
        return presenters;
    }

    @JsonIgnore
    protected List<Commentator> commentator;

    @JsonProperty("commentators")
    public List<String> getCommentators() {
        List<String> commentators = new ArrayList<String>(getCommentator().size());
        for (Commentator oneCommentator : getCommentator()) {
            commentators.add(oneCommentator.getvalue());
        }
        return commentators;
    }

    @JsonIgnore
    protected List<Guest> guest;

    @JsonProperty("guests")
    public List<String> getGuests() {
        List<String> guests = new ArrayList<String>(getGuest().size());
        for (Guest oneGuest : getGuest()) {
            guests.add(oneGuest.getvalue());
        }
        return guests;
    }


    /**
     * Gets the value of the director property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the director property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirector().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Director }
     * 
     * 
     */
    public List<Director> getDirector() {
        if (director == null) {
            director = new ArrayList<Director>();
        }
        return this.director;
    }

    /**
     * Gets the value of the actor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Actor }
     * 
     * 
     */
    public List<Actor> getActor() {
        if (actor == null) {
            actor = new ArrayList<Actor>();
        }
        return this.actor;
    }

    /**
     * Gets the value of the writer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the writer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWriter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Writer }
     * 
     * 
     */
    public List<Writer> getWriter() {
        if (writer == null) {
            writer = new ArrayList<Writer>();
        }
        return this.writer;
    }

    /**
     * Gets the value of the adapter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adapter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdapter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Adapter }
     * 
     * 
     */
    public List<Adapter> getAdapter() {
        if (adapter == null) {
            adapter = new ArrayList<Adapter>();
        }
        return this.adapter;
    }

    /**
     * Gets the value of the producer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the producer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProducer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Producer }
     * 
     * 
     */
    public List<Producer> getProducer() {
        if (producer == null) {
            producer = new ArrayList<Producer>();
        }
        return this.producer;
    }

    /**
     * Gets the value of the composer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the composer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComposer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Composer }
     * 
     * 
     */
    public List<Composer> getComposer() {
        if (composer == null) {
            composer = new ArrayList<Composer>();
        }
        return this.composer;
    }

    /**
     * Gets the value of the editor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the editor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEditor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Editor }
     * 
     * 
     */
    public List<Editor> getEditor() {
        if (editor == null) {
            editor = new ArrayList<Editor>();
        }
        return this.editor;
    }

    /**
     * Gets the value of the presenter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the presenter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPresenter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Presenter }
     * 
     * 
     */
    public List<Presenter> getPresenter() {
        if (presenter == null) {
            presenter = new ArrayList<Presenter>();
        }
        return this.presenter;
    }

    /**
     * Gets the value of the commentator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commentator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommentator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Commentator }
     * 
     * 
     */
    public List<Commentator> getCommentator() {
        if (commentator == null) {
            commentator = new ArrayList<Commentator>();
        }
        return this.commentator;
    }

    /**
     * Gets the value of the guest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Guest }
     * 
     * 
     */
    public List<Guest> getGuest() {
        if (guest == null) {
            guest = new ArrayList<Guest>();
        }
        return this.guest;
    }

}
