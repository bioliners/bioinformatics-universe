package enums;

public enum  ParamPrefixes {
    INPUT("-i "), FOLDER("-f "), INPUT_SECOND("-s "), OUTPUT("-o "),
    DELIM("-d "), COLUMN("-c "), DELIM_SECOND("-t "), COLUMN_SECOND("-p "),
    WDIR("-w "), IDENTITY_THRESH("-t "), EVAL_THRESH("-e "), COVERAGE_THRESH("-v "),
    MERGE("-m ");

    private String paramPrefix;

    ParamPrefixes(String paramPrefix) {
        this.paramPrefix = paramPrefix;
    }

    public String getPrefix() {
        return paramPrefix;
    }

}
