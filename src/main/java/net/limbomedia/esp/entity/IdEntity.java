package net.limbomedia.esp.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
