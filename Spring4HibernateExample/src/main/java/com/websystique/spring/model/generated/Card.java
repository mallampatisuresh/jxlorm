
package com.websystique.spring.model.generated;

import javax.persistence.Entity;
import com.websystique.spring.model.BaseEntity;

@Entity
public class Card
    extends BaseEntity
{

    private String Name;
    private String Type;
    private String Issuer;
    private String Acquirer;

    public String getName() {
        return Name;
    }

    public void setName(String inputName) {
        this.Name = inputName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String inputType) {
        this.Type = inputType;
    }

    public String getIssuer() {
        return Issuer;
    }

    public void setIssuer(String inputIssuer) {
        this.Issuer = inputIssuer;
    }

    public String getAcquirer() {
        return Acquirer;
    }

    public void setAcquirer(String inputAcquirer) {
        this.Acquirer = inputAcquirer;
    }

}
