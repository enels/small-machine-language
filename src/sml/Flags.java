package sml;

// TODO: write a JavaDoc for the class and methods

/**
 * Flags class that helps to get and set the values of flags
 * @author Odusa Toyosi
 */

public class Flags {

    /**
     * POssible signs that can be given to a flag
     */
    private boolean zero, sign;

    /**
     * Get the value of the flag
     * @return flag default value - zero
     */
    public boolean getZF() {
        return zero;
    }

    /**
     * Sets flag ZF default value - zero
     * @param zero
     */
    public void setZF(boolean zero) {
        this.zero = zero;
    }

    /**
     * get flag SF default value (true or false)
     * @return non-zero sign
     */
    public boolean getSF() {
        return sign;
    }

    /**
     * Set SF flag sign (true of false)
     * @param sign
     */
    public void setSF(boolean sign) {
        this.sign = sign;
    }

    //TODO: implement methods .toString, .equals and .hashCode
    @Override
    public String toString() {
        return "";
    }

    /**
     * Checks for equality of the flags
     * @param o
     * @return
     */
    public boolean equals (Object o) {
        if (o instanceof Flags) {
            Flags other = (Flags) o;
            return other.getZF() == ((Flags) o).getZF() && other.getSF() == ((Flags) o).getSF();
        }

        return false;
    }

}
