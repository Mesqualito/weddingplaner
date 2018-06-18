package rocks.gebsattel.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import rocks.gebsattel.hochzeit.domain.enumeration.AllowGroup;

/**
 * A AllowControl.
 */
@Entity
@Table(name = "allow_control")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "allowcontrol")
public class AllowControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "allow_group")
    private AllowGroup allowGroup;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "allow_control_user_extra",
               joinColumns = @JoinColumn(name="allow_controls_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_extras_id", referencedColumnName="user_id"))
    private Set<UserExtra> userExtras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AllowGroup getAllowGroup() {
        return allowGroup;
    }

    public AllowControl allowGroup(AllowGroup allowGroup) {
        this.allowGroup = allowGroup;
        return this;
    }

    public void setAllowGroup(AllowGroup allowGroup) {
        this.allowGroup = allowGroup;
    }

    public Set<UserExtra> getUserExtras() {
        return userExtras;
    }

    public AllowControl userExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
        return this;
    }

    public AllowControl addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.getAllowControls().add(this);
        return this;
    }

    public AllowControl removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.getAllowControls().remove(this);
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
        AllowControl allowControl = (AllowControl) o;
        if (allowControl.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allowControl.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AllowControl{" +
            "id=" + getId() +
            ", allowGroup='" + getAllowGroup() + "'" +
            "}";
    }
}
