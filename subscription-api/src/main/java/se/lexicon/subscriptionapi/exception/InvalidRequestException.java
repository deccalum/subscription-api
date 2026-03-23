package se.lexicon.subscriptionapi.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String mode) {
        super(buildAutoMessage(mode));
    }

    private static String buildAutoMessage(String mode) {
        if (!"auto".equalsIgnoreCase(mode)) {
            return "invalid_request";
        }
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String source = trace.length > 3 ? trace[3].getClassName() + "." + trace[3].getMethodName() : "unknown";
        return "invalid_request @ " + source;
    }
}
