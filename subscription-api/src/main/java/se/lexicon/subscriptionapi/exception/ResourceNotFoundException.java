package se.lexicon.subscriptionapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mode) {
        super(buildAutoMessage(mode));
    }

    private static String buildAutoMessage(String mode) {
        if (!"auto".equalsIgnoreCase(mode)) {
            return "resource_not_found";
        }
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String source = trace.length > 3 ? trace[3].getClassName() + "." + trace[3].getMethodName() : "unknown";
        return "resource_not_found @ " + source;
    }
}
