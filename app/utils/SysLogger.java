package utils;

import common.SessionParams;
import play.Logger;
import play.mvc.Scope;

public class SysLogger {
    public static void debug(String format, Object... args) {
        Logger.debug(buildMessage(format, args));
    }

    public static void debug(Throwable tr, String format, Object... args) {
        Logger.debug(tr, buildMessage(format, args));
    }

    public static void info(String format, Object... args) {
        Logger.info(buildMessage(format, args));
    }

    public static void info(Throwable tr, String format, Object... args) {
        Logger.info(tr, buildMessage(format, args));
    }

    public static void warn(String format, Object... args) {
        Logger.warn(buildMessage(format, args));
    }

    public static void warn(Throwable tr, String format, Object... args) {
        Logger.warn(tr, buildMessage(format, args));
    }

    public static void error(String format, Object... args) {
        Logger.error(buildMessage(format, args));
    }

    public static void error(Throwable tr, String format, Object... args) {
        Logger.error(tr, buildMessage(format, args));
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private static String buildMessage(String format, Object... args) {
        if (format == null) {
            format = "";
        }
        String msg = (args == null || args.length == 0) ? format : String.format(format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        int lineNum = 0;
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(SysLogger.class)) {

                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                lineNum = trace[i].getLineNumber();
                break;
            }
        }

        // session信息
        Scope.Session session = Scope.Session.current();
        if (session != null) {
            String clinicId = StringUtils.emptyIfNull(session.get(SessionParams.CLINIC_ID));
            String clinicName = StringUtils.emptyIfNull(session.get(SessionParams.CLINIC_NAME));
            String userId = StringUtils.emptyIfNull(session.get(SessionParams.USER_ID));
            String userName = StringUtils.emptyIfNull(session.get(SessionParams.USER_NAME));

            if (!StringUtils.isNullOrEmpty(userId)) {
                return String.format("[%s:%d,%s:%s,%s:%s]:%s",
                        caller, lineNum,
                        clinicId, clinicName,
                        userId, userName,
                        msg);
            }
        }
        return String.format("[%s:%d]:%s", caller, lineNum, msg);
    }
}
