package org.josescalia.swingrss.model;

/**
 * Created by josescalia on 31/01/15.
 */
public class LandFWrapper {
    private String lfName;
    private String lfValue;

    public LandFWrapper() {
    }

    public LandFWrapper(String lfName, String lfValue) {
        this.lfName = lfName;
        this.lfValue = lfValue;
    }

    public String getLfName() {
        return lfName;
    }

    public void setLfName(String lfName) {
        this.lfName = lfName;
    }

    public String getLfValue() {
        return lfValue;
    }

    public void setLfValue(String lfValue) {
        this.lfValue = lfValue;
    }

    @Override
    public String toString() {
        return "LandFWrapper{" +
                "lfName='" + lfName + '\'' +
                ", lfValue='" + lfValue + '\'' +
                '}';
    }
}
