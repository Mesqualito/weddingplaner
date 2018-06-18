package rocks.gebsattel.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    @Column(name = "message_short_description", length = 80, nullable = false)
    private String messageShortDescription;

    @Column(name = "message_init_time")
    private Instant messageInitTime;

    @Lob
    @Column(name = "message_text")
    private String messageText;

    @Column(name = "message_valid_from")
    private Instant messageValidFrom;

    @Column(name = "message_valid_until")
    private Instant messageValidUntil;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "message_user_extra",
               joinColumns = @JoinColumn(name="messages_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_extras_id", referencedColumnName="user_id"))
    private Set<UserExtra> userExtras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageShortDescription() {
        return messageShortDescription;
    }

    public Message messageShortDescription(String messageShortDescription) {
        this.messageShortDescription = messageShortDescription;
        return this;
    }

    public void setMessageShortDescription(String messageShortDescription) {
        this.messageShortDescription = messageShortDescription;
    }

    public Instant getMessageInitTime() {
        return messageInitTime;
    }

    public Message messageInitTime(Instant messageInitTime) {
        this.messageInitTime = messageInitTime;
        return this;
    }

    public void setMessageInitTime(Instant messageInitTime) {
        this.messageInitTime = messageInitTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public Message messageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Instant getMessageValidFrom() {
        return messageValidFrom;
    }

    public Message messageValidFrom(Instant messageValidFrom) {
        this.messageValidFrom = messageValidFrom;
        return this;
    }

    public void setMessageValidFrom(Instant messageValidFrom) {
        this.messageValidFrom = messageValidFrom;
    }

    public Instant getMessageValidUntil() {
        return messageValidUntil;
    }

    public Message messageValidUntil(Instant messageValidUntil) {
        this.messageValidUntil = messageValidUntil;
        return this;
    }

    public void setMessageValidUntil(Instant messageValidUntil) {
        this.messageValidUntil = messageValidUntil;
    }

    public Set<UserExtra> getUserExtras() {
        return userExtras;
    }

    public Message userExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
        return this;
    }

    public Message addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.getMessages().add(this);
        return this;
    }

    public Message removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.getMessages().remove(this);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", messageShortDescription='" + getMessageShortDescription() + "'" +
            ", messageInitTime='" + getMessageInitTime() + "'" +
            ", messageText='" + getMessageText() + "'" +
            ", messageValidFrom='" + getMessageValidFrom() + "'" +
            ", messageValidUntil='" + getMessageValidUntil() + "'" +
            "}";
    }
}
