package com.blogging.aps.support.utils;

/**
 * @author techoneduan
 * @date 2019/1/22
 */
public class IdGenerator {

    private static final String POST_PRE = "PO";

    private static final String PASSAGE_PRE = "PA";

    private static final String IMAGE_PRE = "IM";


    /**
     * post id生成
     *
     * @return
     */
    public static String generatePostId() {

        return POST_PRE + new SnowflakeIdGenerator(0, 0).nextId();
    }

    /**
     * @return
     */
    public static String generatePassageId() {

        return PASSAGE_PRE + new SnowflakeIdGenerator(0, 0).nextId();
    }

    public static String generateImageId() {
        return IMAGE_PRE + new SnowflakeIdGenerator(0, 0).nextId();
    }
}
