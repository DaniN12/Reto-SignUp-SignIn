/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Represents a user in the system.
 *
 * @author Enzo
 */
public class User implements Serializable {

    private String email;
    private String fullName;
    private String password;
    private String street;
    private String city;
    private Integer zip;
    private Integer company_id;
    private Boolean active;
    private Integer phone;
    

    /**
     * Default constructor for creating an empty {@link User} object.
     */
    public User() {

    }

    /**
     * Constructs a {@link User} object with the specified attributes.
     *
     * @param email     The user's email address.
     * @param fullName  The user's full name.
     * @param password  The user's password.
     * @param street    The street address of the user.
     * @param city      The city where the user resides.
     * @param zip       The zip code for the user's address.
     * @param active    Indicates whether the user account is active.
     */
    public User(String email, String fullName, String password, String street, String city, Integer zip, Boolean active, Integer phone) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.active = active;
        this.phone = phone;
    }
    /**
     * Returns the user's email address.
     *
     * @return The email address of the user.
     */

    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's full name.
     *
     * @return The full name of the user.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the user's full name.
     *
     * @param fullName The new full name to set for the user.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the user's password.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the user's password.
     *
     * @param password The new password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's street address.
     *
     * @return The street address of the user.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the user's street address.
     *
     * @param street The new street address to set for the user.
     */
    public void setStreet(String street) {
        this.street = street;
    }

     /**
     * Returns the user's city.
     *
     * @return The city where the user resides.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the user's city.
     *
     * @param city The new city to set for the user.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the user's zip code.
     *
     * @return The zip code for the user's address.
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Sets the user's zip code.
     *
     * @param zip The new zip code to set for the user.
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Returns whether the user's account is active.
     *
     * @return True if the user's account is active, otherwise false.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active status of the user's account.
     *
     * @param active The new active status to set for the user.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

     /**
     * Returns the company's ID associated with the user.
     *
     * @return The company ID of the user.
     */
    public Integer getCompany_id() {
        return company_id;
    }

    /**
     * Sets the company ID associated with the user.
     *
     * @param company_id The new company ID to set for the user.
     */
    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }
    
      /**
     * Returns the phone associated with the user.
     *
     * @return The phone of the user.
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     * Sets the phone associated with the user.
     *
     * @param phone The new phone to set for the user.
     */
    public void setPhone(Integer Phone) {
        this.phone = Phone;
    }

}
