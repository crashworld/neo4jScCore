package net.ostis.sccore.contents;


/**
 * Class for content that contains in ScNode.
 *
 * @author yaskoam
 */
public class Content {
    private ContentTypes contentType;
    private Object content;

    /**
     * Construct empty content object.
     */
    public Content() {
        content = 0x0;
        contentType = Content.ContentTypes.BYTE_CONTENT;
    }

    /**
     * Construct content object.
     *
     * @param str string content of object
     */
    public Content(String str) {
        this.setStringContent(str);
    }

    /**
     * Construct content object.
     *
     * @param number integer content of object
     */
    public Content(Integer number) {
        this.setIntContent(number);
    }

    /**
     * Construct content object.
     *
     * @param number float content of object
     */
    public Content(Float number) {
        this.setFloatContent(number);
    }

    /**
     * Construct content object.
     *
     * @param number double content of object
     */
    public Content(Double number) {
        this.setDoubleContent(number);
    }

    /**
     * Construct content object.
     *
     * @param b byte array content of object
     */
    public Content(byte[] b) {
        this.setByteContent(b);
    }

    /**
     * Method that set string content.
     *
     * @param str string object
     */
    public void setStringContent(String str) {
        content = str;
        contentType = Content.ContentTypes.STRING_CONTENT;
    }

    /**
     * Method that set integer content.
     *
     * @param number Integer object
     */
    public void setIntContent(Integer number) {
        content = number;
        contentType = Content.ContentTypes.INT_CONTENT;
    }

    /**
     * Method that set float content.
     *
     * @param number Float object
     */
    public void setFloatContent(Float number) {
        content = number;
        contentType = Content.ContentTypes.FLOAT_CONTENT;
    }

    /**
     * Method that set double content.
     *
     * @param number Double object
     */
    public void setDoubleContent(Double number) {
        content = number;
        contentType = Content.ContentTypes.DOUBLE_CONTENT;
    }

    /**
     * Method that set byte array content.
     *
     * @param b byte array object
     */
    public void setByteContent(byte[] b) {
        content = b;
        contentType = Content.ContentTypes.BYTE_CONTENT;
    }

    /**
     * Method that check if content is string.
     *
     * @return true if content is string
     */
    public boolean isStringContent() {
        return contentType == ContentTypes.STRING_CONTENT;
    }

    /**
     * Method that check if content is Integer.
     *
     * @return true if content is Integer
     */
    public boolean isIntContent() {
        return contentType == ContentTypes.INT_CONTENT;
    }

    /**
     * Method that check if content is Float.
     *
     * @return true if content is Float
     */
    public boolean isFloatContent() {
        return contentType == ContentTypes.FLOAT_CONTENT;
    }

    /**
     * Method that check if content is Double.
     *
     * @return true if content is Double
     */
    public boolean isDoubleContent() {
        return contentType == ContentTypes.DOUBLE_CONTENT;
    }

    /**
     * Method that check if content is byte array.
     *
     * @return true if content is byte array
     */
    public boolean isByteContent() {
        return contentType == ContentTypes.BYTE_CONTENT;
    }

    /**
     * Method that get string from content.
     *
     * @return string object
     */
    public String getStringContent() {
        return content.toString();
    }

    /**
     * Method that get Integer from content.
     *
     * @return Integer object
     */
    public Integer getIntContent() {
        return (Integer) content;
    }

    /**
     * Method that get Float from content.
     *
     * @return Float object
     */
    public Float getFloatContent() {
        return (Float) content;
    }

    /**
     * Method that get Double from content.
     *
     * @return Double object
     */
    public Double getDoubleContent() {
        return (Double) content;
    }

    /**
     * Method that get byte array from content.
     *
     * @return byte array object
     */
    public byte[] getByteContent() {
        return (byte[]) content;
    }

    /**
     * Method that return content.
     * 
     * @return content presented by Object instance
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * Method that return content type.
     *
     * @return type of content
     */
    public ContentTypes getContentType() {
        return this.contentType;
    }

    /**
     * Method that set content.
     *
     * @param content content that presented by Object instance
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * Method that set content type.
     *
     * @param type type of content
     */
    public void setContentType(ContentTypes type) {
        this.contentType = type;
    }

    /**
     * Enum for content types.
     */
    public enum ContentTypes {
        /**
         * String type of content
         */
        STRING_CONTENT("stringContent"),
        /**
         * Int type of content
         */
        INT_CONTENT("intContent"),
        /**
         * Float type of content
         */
        FLOAT_CONTENT("floatContent"),
        /**
         * Double type of content
         */
        DOUBLE_CONTENT("doubleContent"),
        /**
         * Byte type of content
         */
        BYTE_CONTENT("byteContent");

        private String typeName = "";

        private ContentTypes(String name) {
            typeName = name;
        }

        public String toString() {
            return typeName;
        }

        /**
         * Get content type object by name.
         *
         * @param name name of content type
         * @return content type object
         */
        public static ContentTypes getTypeByName(String name) {
            if (name.equals(STRING_CONTENT.toString())) {
                return STRING_CONTENT;
            }
            else if (name.equals(INT_CONTENT.toString())) {
                return INT_CONTENT;
            }
            else if (name.equals(FLOAT_CONTENT.toString())) {
                return FLOAT_CONTENT;
            }
            else if (name.equals(DOUBLE_CONTENT.toString())) {
                return DOUBLE_CONTENT;
            }
            else if (name.equals(BYTE_CONTENT.toString())) {
                return BYTE_CONTENT;
            }

            return null;
        }
    }

}
