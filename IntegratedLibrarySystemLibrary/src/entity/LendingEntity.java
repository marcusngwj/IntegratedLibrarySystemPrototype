/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Marcus
 */
@Entity
public class LendingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendId;
    private Long memberId;
    private Long bookId;
    private Date lendDate;
    
    public LendingEntity() {}
    
    public LendingEntity(Long lendId, Long memberId, Long bookId, Date lendDate) {
        this.lendId = lendId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.lendDate = lendDate;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }
    
    public Long getMemberId() {
        return memberId;
    }
    
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId() {
        this.bookId = bookId;
    }
    
    public Date getLendDate(){
        return lendDate;
    }
    
    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lendId != null ? lendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lendId fields are not set
        if (!(object instanceof LendingEntity)) {
            return false;
        }
        LendingEntity other = (LendingEntity) object;
        if ((this.lendId == null && other.lendId != null) || (this.lendId != null && !this.lendId.equals(other.lendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LendingEntity[ id=" + lendId + " ]";
    }
    
}
