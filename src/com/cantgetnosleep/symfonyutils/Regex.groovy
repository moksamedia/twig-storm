package com.cantgetnosleep.symfonyutils

/**
 * Created by cantgetnosleep on 2/22/15.
 */
class Regex {
    public static String LINK_GROUP_REGEX = /^(\s*<link\s[^>]*>[ \t]*)+$/
    public static final HREF_REGEX = /(?i)href=['"]([^'"]*)['"]/
    public static final SRC_REGEX = /(?i)src=['"]([^'"]*)['"]/
    public static final CSS_URL_REGEX = /(?i)url\(([^\)]*)\)/

    public static IMG_TAG_REGEX = /(?<!(\}\s{0,5}))<\s*img[^<]*>/
    public static TAG_ATTRIBUTE_REGEX = /([a-zA-Z_:][-a-zA-Z0-9_:.]+)=(["'])(.*?)\2/
    public static DATA_ATTRIBUTE_REGEX =  /(data[-a-zA-Z0-9_:.]+={0,1})/
    public static IMG_ATTRIBUTES = /(?i)<\s*img(.*)>/

}
