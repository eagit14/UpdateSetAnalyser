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
