
package Modell;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;


@Entity
@Table(name = "bookauthor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bookauthor.findAll", query = "SELECT b FROM Bookauthor b"),
    @NamedQuery(name = "Bookauthor.findByBookauthorID", query = "SELECT b FROM Bookauthor b WHERE b.bookauthorID = :bookauthorID"),
    @NamedQuery(name = "Bookauthor.findByBookISBN", query = "SELECT b FROM Bookauthor b WHERE b.bookISBN = :bookISBN"),
    @NamedQuery(name = "Bookauthor.findByAuthorID", query = "SELECT b FROM Bookauthor b WHERE b.authorID = :authorID"),
    @NamedQuery(name = "Bookauthor.findByStatus", query = "SELECT b FROM Bookauthor b WHERE b.status = :status")})
public class Bookauthor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bookauthorID")
    private Integer bookauthorID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bookISBN")
    private long bookISBN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "authorID")
    private int authorID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    public Bookauthor() {
    }

    public Bookauthor(Integer bookauthorID) {
        this.bookauthorID = bookauthorID;
    }

    public Bookauthor(Integer bookauthorID, long bookISBN, int authorID, int status) {
        this.bookauthorID = bookauthorID;
        this.bookISBN = bookISBN;
        this.authorID = authorID;
        this.status = status;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("bookauthorID",this.bookauthorID);
        object.put("bookISBN",this.bookISBN);
        object.put("authorID;",this.authorID);
        object.put("status", this.status);
        return object;
    }
    
    public static Bookauthor getBookAuthorById(Integer bookauthorID){
        EntityManager em = Database.getDbConn();
        return em.find(Bookauthor.class, bookauthorID);
    }

    public Integer getBookauthorID() {
        return bookauthorID;
    }

    public void setBookauthorID(Integer bookauthorID) {
        this.bookauthorID = bookauthorID;
    }

    public long getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(long bookISBN) {
        this.bookISBN = bookISBN;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
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
        hash += (bookauthorID != null ? bookauthorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookauthor)) {
            return false;
        }
        Bookauthor other = (Bookauthor) object;
        if ((this.bookauthorID == null && other.bookauthorID != null) || (this.bookauthorID != null && !this.bookauthorID.equals(other.bookauthorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Bookauthor[ bookauthorID=" + bookauthorID + " ]";
    }
    
}
