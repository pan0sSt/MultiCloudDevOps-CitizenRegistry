package gr.ekpa.citizen.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.Column;

@Entity
public class Citizen {

    @Id
    @Size(min = 8, max = 8, message = "ID must be exactly 8 characters")
    private String id;

    @NotBlank(message = "First name is mandatory")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Gender is mandatory")
    @Column(nullable = false)
    private String gender;

    @NotBlank(message = "Birth date is mandatory")
    @Column(nullable = false)
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Birth date must be in the format dd-MM-yyyy")
    private String birthDate;

    @Pattern(regexp = "^\\d{9}$", message = "Tax ID must be exactly 9 digits")
    private String taxId;

    private String address;

    public Citizen() {}

    public Citizen(String id, String firstName, String lastName, String gender, String birthDate, String taxId, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.taxId = taxId;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
