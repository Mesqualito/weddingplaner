package rocks.gebsattel.hochzeit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PartyFood.
 */
@Entity
@Table(name = "party_food")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "partyfood")
public class PartyFood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    @Column(name = "food_name", length = 80, nullable = false)
    private String foodName;

    @Size(min = 20, max = 1024)
    @Column(name = "food_short_description", length = 1024)
    private String foodShortDescription;

    @Lob
    @Column(name = "food_long_description")
    private String foodLongDescription;

    @Column(name = "food_quantity_persons")
    private Integer foodQuantityPersons;

    @Column(name = "food_best_serve_time")
    private Instant foodBestServeTime;

    @Column(name = "food_proposal_accepted")
    private Boolean foodProposalAccepted;

    @ManyToOne
    @JsonIgnoreProperties("partyFoods")
    private UserExtra userExtra;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public PartyFood foodName(String foodName) {
        this.foodName = foodName;
        return this;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodShortDescription() {
        return foodShortDescription;
    }

    public PartyFood foodShortDescription(String foodShortDescription) {
        this.foodShortDescription = foodShortDescription;
        return this;
    }

    public void setFoodShortDescription(String foodShortDescription) {
        this.foodShortDescription = foodShortDescription;
    }

    public String getFoodLongDescription() {
        return foodLongDescription;
    }

    public PartyFood foodLongDescription(String foodLongDescription) {
        this.foodLongDescription = foodLongDescription;
        return this;
    }

    public void setFoodLongDescription(String foodLongDescription) {
        this.foodLongDescription = foodLongDescription;
    }

    public Integer getFoodQuantityPersons() {
        return foodQuantityPersons;
    }

    public PartyFood foodQuantityPersons(Integer foodQuantityPersons) {
        this.foodQuantityPersons = foodQuantityPersons;
        return this;
    }

    public void setFoodQuantityPersons(Integer foodQuantityPersons) {
        this.foodQuantityPersons = foodQuantityPersons;
    }

    public Instant getFoodBestServeTime() {
        return foodBestServeTime;
    }

    public PartyFood foodBestServeTime(Instant foodBestServeTime) {
        this.foodBestServeTime = foodBestServeTime;
        return this;
    }

    public void setFoodBestServeTime(Instant foodBestServeTime) {
        this.foodBestServeTime = foodBestServeTime;
    }

    public Boolean isFoodProposalAccepted() {
        return foodProposalAccepted;
    }

    public PartyFood foodProposalAccepted(Boolean foodProposalAccepted) {
        this.foodProposalAccepted = foodProposalAccepted;
        return this;
    }

    public void setFoodProposalAccepted(Boolean foodProposalAccepted) {
        this.foodProposalAccepted = foodProposalAccepted;
    }

    public UserExtra getUserExtra() {
        return userExtra;
    }

    public PartyFood userExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
        return this;
    }

    public void setUserExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
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
        PartyFood partyFood = (PartyFood) o;
        if (partyFood.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyFood.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyFood{" +
            "id=" + getId() +
            ", foodName='" + getFoodName() + "'" +
            ", foodShortDescription='" + getFoodShortDescription() + "'" +
            ", foodLongDescription='" + getFoodLongDescription() + "'" +
            ", foodQuantityPersons=" + getFoodQuantityPersons() +
            ", foodBestServeTime='" + getFoodBestServeTime() + "'" +
            ", foodProposalAccepted='" + isFoodProposalAccepted() + "'" +
            "}";
    }
}
