@Grab(group='org.springframework.security', module='spring-security-core', version='5.0.6.RELEASE')
@Grab(group='com.xlson.groovycsv', module='groovycsv', version='1.3')

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import static com.xlson.groovycsv.CsvParser.parseCsv

// filepath to csv
def absoluteFilePath = "./"
def sourceFile = "hochzeitsgaeste.csv"
// hochzeitsgaeste.csv columns:
// nr;firstname;lastname;code;street;streetnr;zipcode;city

def data = parseCsv(new FileReader(absoluteFilePath + sourceFile))
def userOrigin = [:]
def columnNames = []

private String convertToBCrypt(String clearTxt){
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    String hashedPassword = passwordEncoder.encode(clearTxt)
}

for(line in data) {

    line.each {
        Benutzer benutzer = new Benutzer()
        benutzer.userId = line.nr.toInteger()+1000
        benutzer.extra_id = line.nr.toInteger()
        benutzer.login = line.first_name + "." + line.last_name
        benutzer.password_hash = convertToBCrypt(benutzer.login)
        benutzer.first_name = line.first_name
        benutzer.last_name = line.last_name
        benutzer.activated = "true"
        benutzer.lang_key = "de"
        benutzer.created_by = "system"
        benutzer.last_modified_by = "system"
        benutzer.addressLine1 = line.street
        benutzer.addressLine2 = ''
        benutzer.code = line.code
        benutzer.country = "Deutschland"
        benutzer.zipCode = line.zipcode
        benutzer.city = line.city
        benutzer.email = ''
        benutzer.image_url = ''
        benutzer.guestInvitationDate = ''
        benutzer.mobilePhoneNr = ''
        benutzer.privatePhoneNr = ''
        benutzer.businessPhoneNr = ''

        userOrigin[line.nr] = benutzer
    }
}

// users.csv - from benutzer:
// id;login;password_hash;first_name;last_name;email;image_url;activated;lang_key;created_by;last_modified_by
File usersFile = new File(absoluteFilePath + "users-prod.csv")
usersFile.write("id;login;password_hash;first_name;last_name;email;image_url;activated;lang_key;created_by;last_modified_by\n")

userOrigin.each { key, entry ->
    println entry.userId + ";" + entry.login
    usersFile.append( entry.userId + ";"
        + entry.login.toLowerCase() + ";"
        + entry.password_hash + ";"
        + entry.first_name + ";"
        + entry.last_name + ";"
        + entry.email + ";"
        + entry.image_url + ";"
        + entry.activated + ";"
        + entry.lang_key + ";"
        + entry.created_by + ";"
        + entry.last_modified_by + "\n")
}

// users_extras.csv - from benutzer:
// addressLine1;addressLine2;businessPhoneNr;code;country;guestCommitted;guestInvitationDate;id;mobilePhoneNr;privatePhoneNr;userId;zipCode
File usersExtraFile = new File(absoluteFilePath + "user_extras-prod.csv")
usersExtraFile.write("address_line1;address_line2;business_phone_nr;code;country;guest_committed;guest_invitation_date;id;mobile_phone_nr;private_phone_nr;user_id;zip_code;city\n")

userOrigin.each { key, entry ->
    println entry.extra_id + ";" + entry.code
    usersExtraFile.append( entry.addressLine1 + ";"
        + entry.addressLine2 + ";"
        + entry.businessPhoneNr + ";"
        + entry.code + ";"
        + entry.country + ";"
        + entry.guestCommitted + ";"
        + entry.guestInvitationDate + ";"
        + entry.extra_id + ";"
        + entry.mobilePhoneNr + ";"
        + entry.privatePhoneNr + ";"
        + entry.userId + ";"
        + entry.zipCode + ";"
        + entry.city + "\n")
}


class Benutzer {
    int userId
    int extra_id
    String login
    String password_hash
    String first_name
    String last_name
    String email
    String image_url
    String activated
    String lang_key
    String created_by
    String last_modified_by
    String addressLine1
    String addressLine2
    String businessPhoneNr
    String code
    String country
    boolean guestCommitted
    String guestInvitationDate
    String mobilePhoneNr
    String privatePhoneNr
    String zipCode
    String city
}
