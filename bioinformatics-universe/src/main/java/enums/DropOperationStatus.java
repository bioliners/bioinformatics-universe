package enums;

public enum DropOperationStatus {
    OK("success"), PROGRAM_EXISTS("ProgramExists"), SUBTAB_EXISTS("SubTabExists");

    private String status;

    DropOperationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
