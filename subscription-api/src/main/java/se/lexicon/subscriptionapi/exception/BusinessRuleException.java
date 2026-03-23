package se.lexicon.subscriptionapi.exception;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String mode) {
        super(buildAutoMessage(mode));
    }

    private static String buildAutoMessage(String mode) {
        if (!"auto".equalsIgnoreCase(mode)) {
            return "business_rule_violation";
        }
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String source = trace.length > 3 ? trace[3].getClassName() + "." + trace[3].getMethodName() : "unknown";
        return "business_rule_violation @ " + source;
    }
}
