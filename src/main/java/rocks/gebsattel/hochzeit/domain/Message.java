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

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "message_to",
               joinColumns = @JoinColumn(name="messages_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tos_id", referencedColumnName="user_id"))
    private Set<UserExtra> tos = new HashSet<>();

    @ManyToOne(optional = false)
    // @NotNull
    private UserExtra from;

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

    public byte[] getImage() {
        return image;
    }

    public Message image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Message imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<UserExtra> getTos() {
        return tos;
    }

    public Message tos(Set<UserExtra> userExtras) {
        this.tos = userExtras;
        return this;
    }

    public Message addTo(UserExtra userExtra) {
        this.tos.add(userExtra);
        userExtra.getReceivedMessages().add(this);
        return this;
    }

    public Message removeTo(UserExtra userExtra) {
        this.tos.remove(userExtra);
        userExtra.getReceivedMessages().remove(this);
        return this;
    }

    public void setTos(Set<UserExtra> userExtras) {
        this.tos = userExtras;
    }

    public UserExtra getFrom() {
        return from;
    }

    public Message from(UserExtra userExtra) {
        this.from = userExtra;
        return this;
    }

    public void setFrom(UserExtra userExtra) {
        this.from = userExtra;
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
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
