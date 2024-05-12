package data;

public record DBConnection(String dbUrl, String dbUser, String dbPw) {

    public DBConnection(String dbUrl, String dbUser, String dbPw) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPw = dbPw;
    }

    @Override
    public String dbUrl() {
        return dbUrl;
    }

    @Override
    public String dbUser() {
        return dbUser;
    }

    @Override
    public String dbPw() {
        return dbPw;
    }
}
