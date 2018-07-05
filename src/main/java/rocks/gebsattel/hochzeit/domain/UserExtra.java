package rocks.gebsattel.hochzeit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import rocks.gebsattel.hochzeit.domain.enumeration.Gender;

import rocks.gebsattel.hochzeit.domain.enumeration.AgeGroup;

/**
 * 'User' is a predesigned special entity
 * and can not have additional attributes etc.
 *
 * List to see User-fields (without constraints, pattern...)
 * entity User {
 * login String
 * password String
 * firstName String
 * lastName String
 * email String
 * activated Boolean
 * langKey String
 * imageUrl String
 * activationKey String
 * resetKey String
 * resetDate Instant
 * }
 */
@ApiModel(description = "'User' is a predesigned special entity and can not have additional attributes etc. List to see User-fields (without constraints, pattern...) entity User { login String password String firstName String lastName String email String activated Boolean langKey String imageUrl String activationKey String resetKey String resetDate Instant }")
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userextra")
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 15)
    @Column(name = "code", length = 15, nullable = false)
    private String code;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "business_phone_nr")
    private String businessPhoneNr;

    @Column(name = "private_phone_nr")
    private String privatePhoneNr;

    @Column(name = "mobile_phone_nr")
    private String mobilePhoneNr;

    @Column(name = "guest_invitation_date")
    private LocalDate guestInvitationDate;

    @Column(name = "guest_committed")
    private Boolean guestCommitted;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group")
    private AgeGroup ageGroup;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "controlGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllowControl> owners = new HashSet<>();

    @OneToMany(mappedBy = "userExtra")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyFood> partyFoods = new HashSet<>();

    @OneToMany(mappedBy = "from")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> ownedMessages = new HashSet<>();

    @ManyToMany(mappedBy = "controlledGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllowControl> allowedUsers = new HashSet<>();

    @ManyToMany(mappedBy = "tos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> receivedMessages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public UserExtra code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public UserExtra addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public UserExtra addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public UserExtra city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public UserExtra zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public UserExtra country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessPhoneNr() {
        return businessPhoneNr;
    }

    public UserExtra businessPhoneNr(String businessPhoneNr) {
        this.businessPhoneNr = businessPhoneNr;
        return this;
    }

    public void setBusinessPhoneNr(String businessPhoneNr) {
        this.businessPhoneNr = businessPhoneNr;
    }

    public String getPrivatePhoneNr() {
        return privatePhoneNr;
    }

    public UserExtra privatePhoneNr(String privatePhoneNr) {
        this.privatePhoneNr = privatePhoneNr;
        return this;
    }

    public void setPrivatePhoneNr(String privatePhoneNr) {
        this.privatePhoneNr = privatePhoneNr;
    }

    public String getMobilePhoneNr() {
        return mobilePhoneNr;
    }

    public UserExtra mobilePhoneNr(String mobilePhoneNr) {
        this.mobilePhoneNr = mobilePhoneNr;
        return this;
    }

    public void setMobilePhoneNr(String mobilePhoneNr) {
        this.mobilePhoneNr = mobilePhoneNr;
    }

    public LocalDate getGuestInvitationDate() {
        return guestInvitationDate;
    }

    public UserExtra guestInvitationDate(LocalDate guestInvitationDate) {
        this.guestInvitationDate = guestInvitationDate;
        return this;
    }

    public void setGuestInvitationDate(LocalDate guestInvitationDate) {
        this.guestInvitationDate = guestInvitationDate;
    }

    public Boolean isGuestCommitted() {
        return guestCommitted;
    }

    public UserExtra guestCommitted(Boolean guestCommitted) {
        this.guestCommitted = guestCommitted;
        return this;
    }

    public void setGuestCommitted(Boolean guestCommitted) {
        this.guestCommitted = guestCommitted;
    }

    public Gender getGender() {
        return gender;
    }

    public UserExtra gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public UserExtra ageGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<AllowControl> getOwners() {
        return owners;
    }

    public UserExtra owners(Set<AllowControl> allowControls) {
        this.owners = allowControls;
        return this;
    }

    public UserExtra addOwner(AllowControl allowControl) {
        this.owners.add(allowControl);
        allowControl.setControlGroup(this);
        return this;
    }

    public UserExtra removeOwner(AllowControl allowControl) {
        this.owners.remove(allowControl);
        allowControl.setControlGroup(null);
        return this;
    }

    public void setOwners(Set<AllowControl> allowControls) {
        this.owners = allowControls;
    }

    public Set<PartyFood> getPartyFoods() {
        return partyFoods;
    }

    public UserExtra partyFoods(Set<PartyFood> partyFoods) {
        this.partyFoods = partyFoods;
        return this;
    }

    public UserExtra addPartyFood(PartyFood partyFood) {
        this.partyFoods.add(partyFood);
        partyFood.setUserExtra(this);
        return this;
    }

    public UserExtra removePartyFood(PartyFood partyFood) {
        this.partyFoods.remove(partyFood);
        partyFood.setUserExtra(null);
        return this;
    }

    public void setPartyFoods(Set<PartyFood> partyFoods) {
        this.partyFoods = partyFoods;
    }

    public Set<Message> getOwnedMessages() {
        return ownedMessages;
    }

    public UserExtra ownedMessages(Set<Message> messages) {
        this.ownedMessages = messages;
        return this;
    }

    public UserExtra addOwnedMessage(Message message) {
        this.ownedMessages.add(message);
        message.setFrom(this);
        return this;
    }

    public UserExtra removeOwnedMessage(Message message) {
        this.ownedMessages.remove(message);
        message.setFrom(null);
        return this;
    }

    public void setOwnedMessages(Set<Message> messages) {
        this.ownedMessages = messages;
    }

    public Set<AllowControl> getAllowedUsers() {
        return allowedUsers;
    }

    public UserExtra allowedUsers(Set<AllowControl> allowControls) {
        this.allowedUsers = allowControls;
        return this;
    }

    public UserExtra addAllowedUser(AllowControl allowControl) {
        this.allowedUsers.add(allowControl);
        allowControl.getControlledGroup().add(this);
        return this;
    }

    public UserExtra removeAllowedUser(AllowControl allowControl) {
        this.allowedUsers.remove(allowControl);
        allowControl.getControlledGroup().remove(this);
        return this;
    }

    public void setAllowedUsers(Set<AllowControl> allowControls) {
        this.allowedUsers = allowControls;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public UserExtra receivedMessages(Set<Message> messages) {
        this.receivedMessages = messages;
        return this;
    }

    public UserExtra addReceivedMessage(Message message) {
        this.receivedMessages.add(message);
        message.getTos().add(this);
        return this;
    }

    public UserExtra removeReceivedMessage(Message message) {
        this.receivedMessages.remove(message);
        message.getTos().remove(this);
        return this;
    }

    public void setReceivedMessages(Set<Message> messages) {
        this.receivedMessages = messages;
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
        UserExtra userExtra = (UserExtra) o;
        if (userExtra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", businessPhoneNr='" + getBusinessPhoneNr() + "'" +
            ", privatePhoneNr='" + getPrivatePhoneNr() + "'" +
            ", mobilePhoneNr='" + getMobilePhoneNr() + "'" +
            ", guestInvitationDate='" + getGuestInvitationDate() + "'" +
            ", guestCommitted='" + isGuestCommitted() + "'" +
            ", gender='" + getGender() + "'" +
            ", ageGroup='" + getAgeGroup() + "'" +
            "}";
    }
}
