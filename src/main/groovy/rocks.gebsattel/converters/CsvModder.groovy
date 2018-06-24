@Grab(group = 'org.springframework.security', module = 'spring-security-core', version = '5.0.6.RELEASE')
@Grab(group = 'com.xlson.groovycsv', module = 'groovycsv', version = '1.3')

import static com.xlson.groovycsv.CsvParser.parseCsv
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Date
import java.text.SimpleDateFormat

// filepath to csv
def absoluteFilePath = "./"
def sourceFile = "hochzeitsgaeste.csv"
// hochzeitsgaeste.csv columns:
// nr;first_name;last_name;code;address_line_1;address_line_1_nr;zip_code;city

def data = parseCsv(new FileReader(absoluteFilePath + sourceFile))
def userOrigin = [:]
def mailDomain = "gebsattel.rocks"

String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss").format(new Date())

for (line in data) {

    line.each {
        Benutzer benutzer = new Benutzer()
        benutzer.userId = line.nr.toInteger() + 1000
        benutzer.extraId = line.nr.toInteger()
        benutzer.login = line.first_name + "." + line.last_name
        benutzer.passwordHash = convertToBCrypt(line.code)
        benutzer.firstName = line.first_name
        benutzer.lastName = line.last_name
        benutzer.activated = "true"
        benutzer.userCreatedTimeStamp = timeStamp
        benutzer.langKey = "de"
        benutzer.createdBy = "system"
        benutzer.lastModifiedBy = "system"
        benutzer.addressLine1 = line.address_line_1 + " " + line.address_line_1_nr
        benutzer.addressLine2 = ''
        benutzer.code = line.code
        benutzer.country = "Deutschland"
        benutzer.zipCode = line.zip_code
        benutzer.city = line.city
        benutzer.email = (replaceUmlaut(benutzer.login)).toLowerCase() + '@' + mailDomain
        benutzer.imageUrl = ''
        benutzer.guestInvitationDate = '2018-04-01'
        benutzer.mobilePhoneNr = ''
        benutzer.privatePhoneNr = ''
        benutzer.businessPhoneNr = ''

        userOrigin[line.nr] = benutzer
    }
}

// users.csv - from benutzer:
// id;login;password_hash;first_name;last_name;email;image_url;activated;lang_key;created_by;last_modified_by
File usersFile = new File(absoluteFilePath + "users-prod.csv")
usersFile.write("id;login;password_hash;first_name;last_name;email;image_url;activated;created_date;lang_key;created_by;last_modified_by\n")

userOrigin.each { key, entry ->
    usersFile.append(entry.userId + ";"
        + entry.login.toLowerCase() + ";"
        + entry.passwordHash + ";"
        + entry.firstName + ";"
        + entry.lastName + ";"
        + entry.email + ";"
        + entry.imageUrl + ";"
        + entry.activated + ";"
        + entry.userCreatedTimeStamp + ";"
        + entry.langKey + ";"
        + entry.createdBy + ";"
        + entry.lastModifiedBy + "\n")
}

// users_extras.csv - from benutzer:
// address_line_1;address_line_2;business_phone_nr;code;country;guest_committed;guest_invitation_date;mobile_phone_nr;private_phone_nr;user_id;zip_code
File usersExtraFile = new File(absoluteFilePath + "user_extras-prod.csv")
usersExtraFile.write("address_line1;address_line2;business_phone_nr;code;country;guest_committed;guest_invitation_date;mobile_phone_nr;private_phone_nr;user_id;zip_code;city\n")

userOrigin.each { key, entry ->
    usersExtraFile.append(entry.addressLine1 + ";"
        + entry.addressLine2 + ";"
        + entry.businessPhoneNr + ";"
        + entry.code + ";"
        + entry.country + ";"
        + entry.guestCommitted + ";"
        + entry.guestInvitationDate + ";"
        + entry.mobilePhoneNr + ";"
        + entry.privatePhoneNr + ";"
        + entry.userId + ";"
        + entry.zipCode + ";"
        + entry.city + "\n")
}

// users_authorities-prod.csv - from benutzer:
// user_id;authority_name
File usersAuthoritiesFile = new File(absoluteFilePath + "users_authorities-prod.csv")
usersAuthoritiesFile.write(
    "address_line1;address_line2;business_phone_nr;code;country;guest_committed;guest_invitation_date;id;mobile_phone_nr;private_phone_nr;user_id;zip_code;city\n")

userOrigin.each { key, entry ->
    usersAuthoritiesFile.append(entry.userId + ";" + "ROLE_USER")
}

def src = new File(absoluteFilePath + "users-prod.csv")
def dst = new File(absoluteFilePath + "users-dev.csv")
dst.write('')
dst << src.text

src = new File(absoluteFilePath + "user_extras-prod.csv")
dst = new File(absoluteFilePath + "user_extras-dev.csv")
dst.write('')
dst << src.text

src = new File(absoluteFilePath + "users_authorities-prod.csv")
dst = new File(absoluteFilePath + "users_authorities-dev.csv")
dst.write('')
dst << src.text

private static String replaceUmlaut(String input) {

    //replace all lower Umlauts
    String output = input.replace("ü", "ue")
        .replace("ö", "oe")
        .replace("ä", "ae")
        .replace("ß", "ss");

    //first replace all capital umlaute in a non-capitalized context (e.g. Übung)
    output = output.replace("Ü(?=[a-zäöüß ])", "Ue")
        .replace("Ö(?=[a-zäöüß ])", "Oe")
        .replace("Ä(?=[a-zäöüß ])", "Ae");

    //now replace all the other capital umlaute
    output = output.replace("Ü", "UE")
        .replace("Ö", "OE")
        .replace("Ä", "AE");

    return output;
}

private String convertToBCrypt(String clearTxt) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    String hashedPassword = passwordEncoder.encode(clearTxt)
}


class Benutzer {
    int userId
    int extraId
    String login
    String passwordHash
    String firstName
    String lastName
    String email
    String imageUrl
    String activated
    String langKey
    String createdBy
    String lastModifiedBy
    String addressLine1
    String addressLine2
    String businessPhoneNr
    String code
    String country
    boolean guestCommitted
    String userCreatedTimeStamp
    String guestInvitationDate
    String mobilePhoneNr
    String privatePhoneNr
    String zipCode
    String city
}
