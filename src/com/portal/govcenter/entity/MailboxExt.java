 package com.portal.govcenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_mailbox_ext")
 public class MailboxExt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String content;
   private String reply;
   private Mailbox mailbox;
 
   @Id
   @Column(name="mailbox_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="mailbox")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="content", nullable=false, length=2000)
   public String getContent() {
     return this.content;
   }
 
   public void setContent(String content) {
     this.content = content;
   }
   @Column(name="reply", nullable=true, length=2000)
   public String getReply() {
     return this.reply;
   }
 
   public void setReply(String reply) {
     this.reply = reply;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Mailbox getMailbox() { return this.mailbox; }
 
   public void setMailbox(Mailbox mailbox)
   {
     this.mailbox = mailbox;
   }
 }


 
 
 