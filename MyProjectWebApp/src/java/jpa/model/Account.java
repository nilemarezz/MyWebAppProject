/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Nile
 */
@Entity
@Table(name = "ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username")
    , @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
    , @NamedQuery(name = "Account.findByName", query = "SELECT a FROM Account a WHERE a.name = :name")
    , @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
    , @NamedQuery(name = "Account.findByActivatekey", query = "SELECT a FROM Account a WHERE a.activatekey = :activatekey")
    , @NamedQuery(name = "Account.findByRegdate", query = "SELECT a FROM Account a WHERE a.regdate = :regdate")
    , @NamedQuery(name = "Account.findByActivatedate", query = "SELECT a FROM Account a WHERE a.activatedate = :activatedate")
    , @NamedQuery(name = "Account.findByAddress", query = "SELECT a FROM Account a WHERE a.address = :address")
    , @NamedQuery(name = "Account.findByProvice", query = "SELECT a FROM Account a WHERE a.provice = :provice")})
public class Account implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Historyorder> historyorderList;

    

    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 40)
    @Column(name = "ACTIVATEKEY")
    private String activatekey;
    @Column(name = "REGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;
    @Column(name = "ACTIVATEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedate;
    @Size(max = 150)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 30)
    @Column(name = "PROVICE")
    private String provice;

    public Account() {
    }

    public Account(String username) {
        this.username = username;
    }

    public Account(String username, String password,String name,String email,String address,String province) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.provice = province;
        this.address = address;
       
        this.regdate = new Date();
        this.activatekey = UUID.randomUUID().toString().replace("-","").substring(0,15);
        if(this.activatekey.length()> 40){
            this.activatekey.substring(0, 40);
        }
}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivatekey() {
        return activatekey;
    }

    public void setActivatekey(String activatekey) {
        this.activatekey = activatekey;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getActivatedate() {
        return activatedate;
    }

    public void setActivatedate(Date activatedate) {
        this.activatedate = activatedate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Account[ username=" + username + " ]";
    }

    @XmlTransient
    public List<Historyorder> getHistoryorderList() {
        return historyorderList;
    }

    public void setHistoryorderList(List<Historyorder> historyorderList) {
        this.historyorderList = historyorderList;
    }

    

    
}
