
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
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;


@Entity
@Table(name = "rental")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rental.findAll", query = "SELECT r FROM Rental r"),
    @NamedQuery(name = "Rental.findByRentalID", query = "SELECT r FROM Rental r WHERE r.rentalID = :rentalID"),
    @NamedQuery(name = "Rental.findByMemberID", query = "SELECT r FROM Rental r WHERE r.memberID = :memberID"),
    @NamedQuery(name = "Rental.findByBookISBN", query = "SELECT r FROM Rental r WHERE r.bookISBN = :bookISBN"),
    @NamedQuery(name = "Rental.findByRentalDate", query = "SELECT r FROM Rental r WHERE r.rentalDate = :rentalDate"),
    @NamedQuery(name = "Rental.findByRentalEndDate", query = "SELECT r FROM Rental r WHERE r.rentalEndDate = :rentalEndDate"),
    @NamedQuery(name = "Rental.findByActive", query = "SELECT r FROM Rental r WHERE r.active = :active"),
    @NamedQuery(name = "Rental.findByStatus", query = "SELECT r FROM Rental r WHERE r.status = :status")})
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rentalID")
    private Integer rentalID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "memberID")
    private int memberID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bookISBN")
    private long bookISBN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rentalDate")
    @Temporal(TemporalType.DATE)
    private Date rentalDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rentalEndDate")
    @Temporal(TemporalType.DATE)
    private Date rentalEndDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private int active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    public Rental() {
    }

    public Rental(Integer rentalID) {
        this.rentalID = rentalID;
    }

    public Rental(Integer rentalID, int memberID, long bookISBN, Date rentalDate, Date rentalEndDate, int active, int status) {
        this.rentalID = rentalID;
        this.memberID = memberID;
        this.bookISBN = bookISBN;
        this.rentalDate = rentalDate;
        this.rentalEndDate = rentalEndDate;
        this.active = active;
        this.status = status;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("rentalID",this.rentalID);
        object.put("memberID;",this.memberID);
        object.put("bookISBN;",this.bookISBN);
        object.put("rentalDate",this.rentalDate);
        object.put("rentalEndDate",this.rentalEndDate);
        object.put("active",this.active);
        object.put("status",this.status);
        return object;
    }
    
     public static Rental getRentalById(int rentalID){
        EntityManager em = Database.getDbConn();
        return em.find(Rental.class, rentalID);
    }
     
    public Integer getRentalID() {
        return rentalID;
    }

    public void setRentalID(Integer rentalID) {
        this.rentalID = rentalID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public long getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(long bookISBN) {
        this.bookISBN = bookISBN;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(Date rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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
        hash += (rentalID != null ? rentalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rental)) {
            return false;
        }
        Rental other = (Rental) object;
        if ((this.rentalID == null && other.rentalID != null) || (this.rentalID != null && !this.rentalID.equals(other.rentalID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Rental[ rentalID=" + rentalID + " ]";
    }
    
}
