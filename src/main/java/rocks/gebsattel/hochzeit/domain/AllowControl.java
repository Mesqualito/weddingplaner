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

    @ManyToOne
    private UserExtra controlGroup;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "allow_control_controlled_group",
               joinColumns = @JoinColumn(name="allow_controls_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="controlled_groups_id", referencedColumnName="id"))
    private Set<UserExtra> controlledGroups = new HashSet<>();

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

    public UserExtra getControlGroup() {
        return controlGroup;
    }

    public AllowControl controlGroup(UserExtra userExtra) {
        this.controlGroup = userExtra;
        return this;
    }

    public void setControlGroup(UserExtra userExtra) {
        this.controlGroup = userExtra;
    }

    public Set<UserExtra> getControlledGroups() {
        return controlledGroups;
    }

    public AllowControl controlledGroups(Set<UserExtra> userExtras) {
        this.controlledGroups = userExtras;
        return this;
    }

    public AllowControl addControlledGroup(UserExtra userExtra) {
        this.controlledGroups.add(userExtra);
        userExtra.getAllowedUsers().add(this);
        return this;
    }

    public AllowControl removeControlledGroup(UserExtra userExtra) {
        this.controlledGroups.remove(userExtra);
        userExtra.getAllowedUsers().remove(this);
        return this;
    }

    public void setControlledGroups(Set<UserExtra> userExtras) {
        this.controlledGroups = userExtras;
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
