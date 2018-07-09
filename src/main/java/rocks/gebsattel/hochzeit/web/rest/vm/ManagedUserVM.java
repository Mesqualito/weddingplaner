package rocks.gebsattel.hochzeit.web.rest.vm;

import rocks.gebsattel.hochzeit.service.dto.UserDTO;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private String code;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String zipCode;
    private String country;
    private String businessPhoneNr;
    private String privatePhoneNr;
    private String mobilePhoneNr;
    private LocalDate guestInvitationDate;
    private boolean guestCommitted;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessPhoneNr() {
        return businessPhoneNr;
    }

    public void setBusinessPhoneNr(String businessPhoneNr) {
        this.businessPhoneNr = businessPhoneNr;
    }

    public String getPrivatePhoneNr() {
        return privatePhoneNr;
    }

    public void setPrivatePhoneNr(String privatePhoneNr) {
        this.privatePhoneNr = privatePhoneNr;
    }

    public String getMobilePhoneNr() {
        return mobilePhoneNr;
    }

    public void setMobilePhoneNr(String mobilePhoneNr) {
        this.mobilePhoneNr = mobilePhoneNr;
    }

    public LocalDate getGuestInvitationDate() {
        return guestInvitationDate;
    }

    public void setGuestInvitationDate(LocalDate guestInvitationDate) {
        this.guestInvitationDate = guestInvitationDate;
    }

    public boolean isGuestCommitted() {
        return guestCommitted;
    }

    public void setGuestCommitted(boolean guestCommitted) {
        this.guestCommitted = guestCommitted;
    }

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
