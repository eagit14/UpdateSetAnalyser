/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ea.sn.comparator;

import java.util.Objects;

/**
 *
 * @author elie.abdelnour
 */
public class UpdateSet {

    private String state;
    private String name;
    private String sysid;
    private String createdOn;
    private String createdBy;

    public UpdateSet() {
        state = "";
        name = "";
        sysid = "";
        createdOn = "";
        createdBy = "";
    }
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the sysid
     */
    public String getSysid() {
        return sysid;
    }

    /**
     * @param sysid the sysid to set
     */
    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    /**
     * @return the createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object obj) {
        //Uniqness would only be on name. update sets might have different states on different envs
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UpdateSet other = (UpdateSet) obj;
        //if (!Objects.equals(this.state, other.state)) {
        //    return false;
        //}
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        //if (!Objects.equals(this.sysid, other.sysid)) {
        //    return false;
        //}
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        //hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.name);
        //hash = 83 * hash + Objects.hashCode(this.sysid);
        return hash;
    }

}
