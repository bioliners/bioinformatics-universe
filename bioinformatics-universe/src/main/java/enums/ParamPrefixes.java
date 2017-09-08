package enums;

public enum  ParamPrefixes {
    INPUT("-i "), OUTPUT("-o "),  DELIM("-d "), COLUMN("-c "), WDIR("-w "), IDENTITY_THRESH("-t "), EVAL_THRESH("-e "), COVERAGE_THRESH("-v "), MERGE("-m ");

    private String paramPreifx;

    ParamPrefixes(String paramPreifx) {
        this.paramPreifx = paramPreifx;
    }

    public String getPreifx() {
        return paramPreifx;
    }

}
