
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;


@Entity
@Table(name = "author")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a"),
    @NamedQuery(name = "Author.findByAuthorID", query = "SELECT a FROM Author a WHERE a.authorID = :authorID"),
    @NamedQuery(name = "Author.findBySurname", query = "SELECT a FROM Author a WHERE a.surname = :surname"),
    @NamedQuery(name = "Author.findByLastname", query = "SELECT a FROM Author a WHERE a.lastname = :lastname"),
    @NamedQuery(name = "Author.findByStatus", query = "SELECT a FROM Author a WHERE a.status = :status")})
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "authorID")
    private Integer authorID;
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
    @Column(name = "status")
    private int status;

    public Author() {
    }

    public Author(Integer authorID) {
        this.authorID = authorID;
    }

    public Author(Integer authorID, String surname, String lastname, int status) {
        this.authorID = authorID;
        this.surname = surname;
        this.lastname = lastname;
        this.status = status;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("authorID",this.authorID);
        object.put("surname",this.surname);
        object.put("lastname",this.lastname);
        object.put("status",this.status);
        return object;
    }
    
    public static Author getAuthorById(int authorID){
        EntityManager em = Database.getDbConn();
        return em.find(Author.class, authorID);
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authorID != null ? authorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.authorID == null && other.authorID != null) || (this.authorID != null && !this.authorID.equals(other.authorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Author[ authorID=" + authorID + " ]";
    }
    
}
