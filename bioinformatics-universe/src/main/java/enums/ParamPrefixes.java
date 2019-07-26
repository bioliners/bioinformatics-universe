package enums;

public enum  ParamPrefixes {
    INPUT("-i "), INPUT_PROTS("-n "), TAXALIST("-l "), INPUT_SECOND("-s "), OUTPUT("-o "), OUTPUT_UNIQUE_SIF("-f "), OUTPUT_PROTS("-p "),
    DELIM("-d "), COLUMN("-c "), DELIM_SECOND("-t "), COLUMN_SECOND("-p "),
    WDIR("-w "), IDENTITY_THRESH("-t "), EVAL_THRESH("-e "), COVERAGE_THRESH("-v "),
    MERGE("-m "), THREAD("--thread "), REORDER("-r "), ALGORITHM("-a "), BEST_HIT("-b ");

    private String paramPrefix;

    ParamPrefixes(String paramPrefix) {
        this.paramPrefix = paramPrefix;
    }

    public String getPrefix() {
        return paramPrefix;
    }

}
