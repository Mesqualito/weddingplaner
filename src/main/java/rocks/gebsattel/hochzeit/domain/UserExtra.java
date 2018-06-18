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
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(optional = false)
    // @NotNull
    // @JoinColumn(unique = true)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "userExtra")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyFood> partyFoods = new HashSet<>();

    @ManyToMany(mappedBy = "userExtras")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany(mappedBy = "userExtras")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AllowControl> allowControls = new HashSet<>();

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

    public Set<Message> getMessages() {
        return messages;
    }

    public UserExtra messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public UserExtra addMessage(Message message) {
        this.messages.add(message);
        message.getUserExtras().add(this);
        return this;
    }

    public UserExtra removeMessage(Message message) {
        this.messages.remove(message);
        message.getUserExtras().remove(this);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<AllowControl> getAllowControls() {
        return allowControls;
    }

    public UserExtra allowControls(Set<AllowControl> allowControls) {
        this.allowControls = allowControls;
        return this;
    }

    public UserExtra addAllowControl(AllowControl allowControl) {
        this.allowControls.add(allowControl);
        allowControl.getUserExtras().add(this);
        return this;
    }

    public UserExtra removeAllowControl(AllowControl allowControl) {
        this.allowControls.remove(allowControl);
        allowControl.getUserExtras().remove(this);
        return this;
    }

    public void setAllowControls(Set<AllowControl> allowControls) {
        this.allowControls = allowControls;
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
            "}";
    }
}
