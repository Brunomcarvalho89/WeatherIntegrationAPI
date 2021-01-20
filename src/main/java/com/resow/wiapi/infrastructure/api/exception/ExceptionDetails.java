package com.resow.wiapi.infrastructure.api.exception;

/**
 *
 * @author brunomcarvalho89@gmail.com
 */
public class ExceptionDetails {

    private String title;
    private int status;
    private String details;
    private long timestamp;

    private ExceptionDetails(String title, int status, String details, long timestamp) {
        this.title = title;
        this.status = status;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static ExceptionDetailsBuilder builder() {
        return new ExceptionDetailsBuilder();
    }

    public static final class ExceptionDetailsBuilder {

        private String title = "";
        private int status = -1;
        private String details = "";
        private long timestamp = 0;

        private ExceptionDetailsBuilder() {
        }

        public ExceptionDetailsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ExceptionDetailsBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public ExceptionDetailsBuilder withDetails(String details) {
            this.details = details;
            return this;
        }

        public ExceptionDetailsBuilder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ExceptionDetails build() {
            return new ExceptionDetails(title, status, details, timestamp);
        }

    }
}
