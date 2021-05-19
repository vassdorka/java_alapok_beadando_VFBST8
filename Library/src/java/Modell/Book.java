
package Modell;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;


@Entity
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByIsbn", query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title"),
    @NamedQuery(name = "Book.findByPublished", query = "SELECT b FROM Book b WHERE b.published = :published"),
    @NamedQuery(name = "Book.findByBookCondition", query = "SELECT b FROM Book b WHERE b.bookCondition = :bookCondition"),
    @NamedQuery(name = "Book.findByStatus", query = "SELECT b FROM Book b WHERE b.status = :status")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "isbn")
    private Long isbn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "discription")
    private String discription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "published")
    private short published;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "bookCondition")
    private String bookCondition;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    public Book() {
    }

    public Book(Long isbn) {
        this.isbn = isbn;
    }

    public Book(Long isbn, String title, String discription, short published, String bookCondition, int status) {
        this.isbn = isbn;
        this.title = title;
        this.discription = discription;
        this.published = published;
        this.bookCondition = bookCondition;
        this.status = status;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("isbn",this.isbn);
        object.put("title",this.title);
        object.put("discription",this.discription);
        object.put("published",this.published);
        object.put("bookCondition",this.bookCondition);
        object.put("status",this.status);
        return object;
    }
  
     public static Book getBookByIsbn(long isbn){
        EntityManager em = Database.getDbConn();
        return em.find(Book.class, isbn);
    }
     
    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public short getPublished() {
        return published;
    }

    public void setPublished(short published) {
        this.published = published;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
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
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Book[ isbn=" + isbn + " ]";
    }
    
}
