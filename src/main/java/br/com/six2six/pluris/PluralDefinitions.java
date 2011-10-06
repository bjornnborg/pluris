package br.com.six2six.pluris;

public class PluralDefinitions {
    private String zeroOccurs;
    private String oneOccur;
    private String manyOccurs;
    
    public PluralDefinitions(String zeroOccurs, String oneOccur, String manyOccurs) {
        this.zeroOccurs = zeroOccurs;
        this.oneOccur = oneOccur;
        this.manyOccurs = manyOccurs;
    }
    
    public PluralDefinitions(String oneOccur, String multipleOccurs) {
        this(multipleOccurs, oneOccur, multipleOccurs);
    }

    public static PluralDefinitions of(String zeroOccurs, String oneOcurr, String manyOccurs) {
        return new PluralDefinitions(zeroOccurs, oneOcurr, manyOccurs);
    }
    
    public static PluralDefinitions of(String oneOcurr, String manyOccurs) {
        return of(manyOccurs, oneOcurr, manyOccurs);
    }

    public String getOneOccur() {
        return oneOccur;
    }

    public void setOneOccur(String oneOccur) {
        this.oneOccur = oneOccur;
    }

    public String getManyOccurs() {
        return manyOccurs;
    }

    public void setManyOccurs(String manyOccurs) {
        this.manyOccurs = manyOccurs;
    }

    public String getZeroOccurs() {
        return zeroOccurs;
    }
    
    @Override
    public String toString() {
        return 
            "[zero: " + 
            this.getZeroOccurs() + 
            ", one:" + this.getOneOccur() + ", " +
            ", many: " + this.getManyOccurs() + "]";
    }

    public String[] toArray() {
        return new String[]{this.zeroOccurs, this.oneOccur, this.manyOccurs};
    }

}
