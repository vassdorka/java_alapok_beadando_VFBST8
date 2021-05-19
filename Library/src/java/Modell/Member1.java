
package Modell;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;


@Entity
@Table(name = "member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Member1.findAll", query = "SELECT m FROM Member1 m"),
    @NamedQuery(name = "Member1.findByMemberID", query = "SELECT m FROM Member1 m WHERE m.memberID = :memberID"),
    @NamedQuery(name = "Member1.findBySurname", query = "SELECT m FROM Member1 m WHERE m.surname = :surname"),
    @NamedQuery(name = "Member1.findByLastname", query = "SELECT m FROM Member1 m WHERE m.lastname = :lastname"),
    @NamedQuery(name = "Member1.findByStartOfMembership", query = "SELECT m FROM Member1 m WHERE m.startOfMembership = :startOfMembership"),
    @NamedQuery(name = "Member1.findByPaidMembershipFee", query = "SELECT m FROM Member1 m WHERE m.paidMembershipFee = :paidMembershipFee"),
    @NamedQuery(name = "Member1.findByStatus", query = "SELECT m FROM Member1 m WHERE m.status = :status")})
public class Member1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "memberID")
    private Integer memberID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startOfMembership")
    @Temporal(TemporalType.DATE)
    private Date startOfMembership;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paidMembershipFee")
    private int paidMembershipFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    public Member1() {
    }

    public Member1(Integer memberID) {
        this.memberID = memberID;
    }

    public Member1(Integer memberID, String surname, String lastname, Date startOfMembership, int paidMembershipFee, int status) {
        this.memberID = memberID;
        this.surname = surname;
        this.lastname = lastname;
        this.startOfMembership = startOfMembership;
        this.paidMembershipFee = paidMembershipFee;
        this.status = status;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("memberID",this.memberID);
        object.put("surname;",this.surname);
        object.put("lastname;",this.lastname);
        object.put("startOfMembership",this.startOfMembership);
        object.put("paidMembershipFee",this.paidMembershipFee);
        object.put("status",this.status);
        return object;
    }
    
    public static Member1 getMemberByID(int memberID){
        EntityManager em = Database.getDbConn();
        return em.find(Member1.class, memberID);
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getStartOfMembership() {
        return startOfMembership;
    }

    public void setStartOfMembership(Date startOfMembership) {
        this.startOfMembership = startOfMembership;
    }

    public int getPaidMembershipFee() {
        return paidMembershipFee;
    }

    public void setPaidMembershipFee(int paidMembershipFee) {
        this.paidMembershipFee = paidMembershipFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberID != null ? memberID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Member1)) {
            return false;
        }
        Member1 other = (Member1) object;
        if ((this.memberID == null && other.memberID != null) || (this.memberID != null && !this.memberID.equals(other.memberID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Member1[ memberID=" + memberID + " ]";
    }
    
}
