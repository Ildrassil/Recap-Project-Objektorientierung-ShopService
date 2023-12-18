public enum OrderStatus {
    PROCESSING("PR"),
    PACKAGING("PA"),
    SEND("S"),
    DELIVERED("D"),
    RECLAMATION("R");

    private String abkürzung;
    OrderStatus(String a){
        abkürzung = a;
    }
    public String getAbkürzung(){
        return abkürzung;
    }
    public void setAbkürzung(String a){
        this.abkürzung = a;
    }


}
