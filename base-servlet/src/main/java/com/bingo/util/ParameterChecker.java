package com.bingo.util;


import com.bingo.exception.InvalidFormatException;

import javax.annotation.Nullable;

/**
 * @author weijian
 * Date : 2014-09-26 20:08
 */

public final class ParameterChecker {

    private ParameterChecker() {
        throw new AssertionError();
    }

    public static void check(boolean expression,
                             String template,
                             @Nullable Object... args)
            throws InvalidFormatException.InvalidRequestParameterException {
        if ( !expression ){
            throw new InvalidFormatException.InvalidRequestParameterException(format(template, args));
        }
    }

    public static void checkNotNull(String param, String name)
            throws InvalidFormatException.InvalidRequestParameterException {
        check(param != null, "lack of '%s'", name);
    }

    public static void checkNotNullOrEmpty(String param, String name)
            throws InvalidFormatException.InvalidRequestParameterException {
        checkNotNull(param, name);
        check(!param.isEmpty(), "'%s' is empty String", name);
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These
     * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
     * If there are more arguments than placeholders, the unmatched arguments will
     * be appended to the end of the formatted message in square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s}
     *     placeholders.
     * @param args the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}. Arguments can be null.
     */
    public static String format(String template,
                                @Nullable Object... args) {
        if (args == null || args.length == 0){
            return template;
        }

        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(
                template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
