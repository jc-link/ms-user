package cl.jcdev.msuser.dto;


import jakarta.validation.constraints.*;


public class UserDTO {

    private Long id;


    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String name;


    @NotNull(message = "Cannot be null")
    @NotBlank(message = "cannot be blank")
    @Size(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters long")
    private String lastname;

    @NotNull(message = "Cannot be null")
    @Email(message = "Must be a valid email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+\\..+)$", message = "Invalid email format")
    @Size(max = 50, message = "Email must be less than 50 characters long")
    private String email;


    @NotNull(message = "Cannot be null")
    @NotBlank(message = "cannot be blank")
    @Size(min = 12, max = 12, message = "Phone number must be 12 characters long")
    private String phone;

    public UserDTO() {
    }

    public UserDTO(String name, String lastname, String email, String phone) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
