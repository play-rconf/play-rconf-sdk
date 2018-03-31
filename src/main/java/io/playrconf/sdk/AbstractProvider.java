package io.playrconf.sdk;

/**
 * This abstraction provides useful internal methods.
 *
 * @author Thibault Meyer
 * @since 18.03.31
 */
public abstract class AbstractProvider implements Provider {

    /**
     * Checks if the given value represents a file or not.
     *
     * @param value The value to analyse
     * @return {@code true} if the value represents a file
     */
    protected boolean isFile(final String value) {
        if (!value.isEmpty() && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
            return value
                .substring(1, value.length() - 1)
                .startsWith(FileCfgObject.FILE_MAGIC_ID);
        }
        return value.startsWith(FileCfgObject.FILE_MAGIC_ID);
    }
}
