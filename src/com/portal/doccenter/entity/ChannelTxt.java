 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.Lob;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_channel_txt")
 public class ChannelTxt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String txtval;
   private Channel channel;
 
   public void init()
   {
     blankToNull();
   }
 
   public void blankToNull() {
     if (StringUtils.isBlank(getTxtval()))
       setTxtval(null);
   }
 
   @Transient
   public boolean isAllBlank() {
     return StringUtils.isBlank(getTxtval());
   }
 
   @Id
   @Column(name="channel_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="channel")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Lob
   @Column(name="txt", nullable=true)
   public String getTxtval() { return this.txtval; }
 
   public void setTxtval(String txtval)
   {
     this.txtval = txtval;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Channel getChannel() { return this.channel; }
 
   public void setChannel(Channel channel)
   {
     this.channel = channel;
   }
 }


 
 
 