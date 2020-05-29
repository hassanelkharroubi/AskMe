package slimane.hafid.ma;

public class question {
    private String rep1;
    private String rep2;
    private String rep3;
    private String rep4;


    private String password_QCM;


    public String getPassword_QCM() {
        return password_QCM;
    }

    public void setPassword_QCM(String password_QCM) {
        this.password_QCM = password_QCM;
    }

    public question() {
    }

    public String getRep1() {
        return rep1;
    }

    public void setRep1(String rep1) {
        this.rep1 = rep1;
    }

    public String getRep2() {
        return rep2;
    }

    public void setRep2(String rep2) {
        this.rep2 = rep2;
    }

    public String getRep3() {
        return rep3;
    }

    public void setRep3(String rep3) {
        this.rep3 = rep3;
    }

    public String getRep4() {
        return rep4;
    }

    public void setRep4(String rep4) {
        this.rep4 = rep4;
    }

    public question(String rep1, String rep2, String rep3, String rep4) {
        this.rep1 = rep1;
        this.rep2 = rep2;
        this.rep3 = rep3;
        this.rep4 = rep4;
    }
}
