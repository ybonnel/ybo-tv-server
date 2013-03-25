package fr.ybo.modele;

import com.google.common.primitives.Ints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class Channel implements Serializable, Comparable<Channel> {

    private String id;
    private String displayName;
    private String icon;
    private Integer numero;


    private static final Logger logger = LoggerFactory.getLogger(Channel.class);


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    private transient Programme currentProgramme;

    public Programme getCurrentProgramme() {
        return currentProgramme;
    }

    public void setCurrentProgramme(Programme currentProgramme) {
        this.currentProgramme = currentProgramme;
    }

    @Override
    public int compareTo(Channel o) {
        int result = Ints.compare(numero, o.numero);
        if (result == 0) {
            result = id.compareTo(o.id);
        }
        return result;
    }
}
