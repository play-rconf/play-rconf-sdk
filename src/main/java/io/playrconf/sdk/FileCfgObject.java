/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 The Play Remote Configuration Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.playrconf.sdk;

import io.playrconf.sdk.exception.BadValueException;
import io.playrconf.sdk.exception.StorageException;

import java.io.*;
import java.util.Base64;

/**
 * Represents a file.
 *
 * @author Thibault Meyer
 * @version 13.03.31
 */
public final class FileCfgObject implements Closeable {

    /**
     * Magic Id to detect file.
     */
    static final String FILE_MAGIC_ID = "<FILE>";

    /**
     * Contains the file content.
     */
    private final InputStream is;

    /**
     * Where to save the file.
     */
    private final String target;

    /**
     * Build a new instance. The file instructions must follows
     * this format: "TARGET;BASE64_CONTENT"
     *
     * @param key        The configuration key
     * @param rawContent The file instructions
     */
    public FileCfgObject(final String key, final String rawContent) {
        final String[] data;
        if (rawContent.charAt(0) == '"' && rawContent.charAt(rawContent.length() - 1) == '"') {
            data = rawContent
                .substring(1, rawContent.length() - 1)
                .substring(FileCfgObject.FILE_MAGIC_ID.length())
                .split(";");
        } else if (rawContent.startsWith(FileCfgObject.FILE_MAGIC_ID)) {
            data = rawContent
                .substring(FileCfgObject.FILE_MAGIC_ID.length())
                .split(";");
        } else {
            data = rawContent.split(";");
        }

        try {
            this.is = new ByteArrayInputStream(
                Base64.getDecoder().decode(data[1])
            );
            this.target = data[0].trim();
        } catch (final IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new BadValueException(key, ex.getMessage());
        }
    }

    /**
     * Build a new instance.
     *
     * @param content The file content
     * @param target  Where to save the file
     */
    public FileCfgObject(final byte[] content, final String target) {
        this(new ByteArrayInputStream(content), target);
    }

    /**
     * Build a new instance.
     *
     * @param is     The file input stream
     * @param target Where to save the file
     */
    public FileCfgObject(final InputStream is, final String target) {
        this.is = is;
        this.target = target.trim();
    }

    /**
     * Tries to save file.
     */
    public void apply() {
        final File ofile = new File(this.target);
        try {
            final OutputStream os = new FileOutputStream(ofile);
            final byte[] buffer = new byte[128];
            int bytesRead;
            while ((bytesRead = this.is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            os.close();
        } catch (final IOException ex) {
            throw new StorageException(this.target, ex.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        if (is != null) {
            is.close();
        }
    }

    @Override
    public String toString() {
        try {
            return FileCfgObject.class.getName()
                + "[size <- "
                + this.is.available()
                + " ; target <- "
                + this.target + "]";
        } catch (final IOException ignore) {
            return FileCfgObject.class.getName()
                + "[size <- 0 ; target <- "
                + this.target
                + "]";
        }
    }
}
