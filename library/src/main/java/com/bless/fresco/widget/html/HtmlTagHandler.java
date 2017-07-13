/*
 * Copyright (C) 2013-2015 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2013-2015 Juha Kuitunen
 * Copyright (C) 2013 Mohammed Lakkadshaw
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bless.fresco.widget.html;

import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.util.Log;

import com.bless.fresco.widget.html.span.ClickableTableSpan;
import com.bless.fresco.widget.html.span.ClickableURLSpan;
import com.bless.fresco.widget.html.span.DrawTableLinkSpan;
import com.bless.fresco.widget.html.span.ClickableImageSpan;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Some parts of this code are based on android.text.Html
 */
public class HtmlTagHandler implements Html.TagHandler {
    /**
     * Keeps track of lists (ol, ul). On bottom of Stack is the outermost list
     * and on top of Stack is the most nested list
     */
    Stack<String> lists = new Stack<>();
    /**
     * Tracks indexes of ordered lists so that after a nested list ends
     * we can continue with correct index of outer list
     */
    Stack<Integer> olNextIndex = new Stack<>();
    /**
     * List indentation in pixels. Nested lists use multiple of this.
     */
    /**
     * Running HTML table string based off of the root table tag. Root table tag being the tag which
     * isn't embedded within any other table tag. Example:
     * <!-- This is the root level opening table tag. This is where we keep track of tables. -->
     * <table>
     *     ...
     *     <table> <!-- Non-root table tags -->
     *     ...
     *     </table>
     *     ...
     * </table>
     * <!-- This is the root level closing table tag and the end of the string we track. -->
     */
    StringBuilder tableHtmlBuilder = new StringBuilder();
    /**
     * Tells us which level of table tag we're on; ultimately used to find the root table tag.
     */
    int tableTagLevel = 0;

    private static final int indent = 10;
    private static final int listItemIndent = indent * 2;
    private static final BulletSpan bullet = new BulletSpan(indent);
    private ClickableTableSpan mClickableTableSpan;
    private DrawTableLinkSpan mDrawTableLinkSpan;
    private ClickableImageSpan mClickableImageSpan;
    private ClickableURLSpan mClickableURLSpan;

    private static class Ul {
    }

    private static class Ol {
    }

    private static class Code {
    }

    private static class Center {
    }

    private static class Strike {
    }

    private static class Table {
    }

    private static class Tr {
    }

    private static class Th {
    }

    private static class Td {
    }

    @Override
    public void handleTag(final boolean opening, final String tag, Editable output, final XMLReader xmlReader) {
        if (opening) {
            // opening tag
            if (HtmlTextView.DEBUG) {
                Log.d(HtmlTextView.TAG, "opening, output: " + output.toString());
            }

            if (tag.equalsIgnoreCase("ul")) {
                lists.push(tag);
            } else if (tag.equalsIgnoreCase("ol")) {
                lists.push(tag);
                olNextIndex.push(1);
            } else if (tag.equalsIgnoreCase("li")) {
                if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
                    output.append("\n");
                }
                String parentList = lists.peek();
                if (parentList.equalsIgnoreCase("ol")) {
                    start(output, new Ol());
                    output.append(olNextIndex.peek().toString()).append(". ");
                    olNextIndex.push(olNextIndex.pop() + 1);
                } else if (parentList.equalsIgnoreCase("ul")) {
                    start(output, new Ul());
                }
            } else if (tag.equalsIgnoreCase("code")) {
                start(output, new Code());
            } else if (tag.equalsIgnoreCase("center")) {
                start(output, new Center());
            } else if (tag.equalsIgnoreCase("s") || tag.equalsIgnoreCase("strike")) {
                start(output, new Strike());
            } else if (tag.equalsIgnoreCase("table")) {
                start(output, new Table());
                if (tableTagLevel == 0) {
                    tableHtmlBuilder = new StringBuilder();
                    // We need some text for the table to be replaced by the span because
                    // the other tags will remove their text when their text is extracted
                    output.append("table placeholder");
                }

                tableTagLevel++;
            }
            else if (tag.equalsIgnoreCase("tr")) {
                start(output, new Tr());
            } else if (tag.equalsIgnoreCase("th")) {
                start(output, new Th());
            } else if (tag.equalsIgnoreCase("td")) {
                start(output, new Td());
            }
        } else {
            // closing tag
            if (HtmlTextView.DEBUG) {
                Log.d(HtmlTextView.TAG, "closing, output: " + output.toString());
            }

            if (tag.equalsIgnoreCase("ul")) {
                lists.pop();
            } else if (tag.equalsIgnoreCase("ol")) {
                lists.pop();
                olNextIndex.pop();
            } else if (tag.equalsIgnoreCase("li")) {
                if (lists.peek().equalsIgnoreCase("ul")) {
                    if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
                        output.append("\n");
                    }
                    // Nested BulletSpans increases distance between bullet and text, so we must prevent it.
                    int bulletMargin = indent;
                    if (lists.size() > 1) {
                        bulletMargin = indent - bullet.getLeadingMargin(true);
                        if (lists.size() > 2) {
                            // This get's more complicated when we add a LeadingMarginSpan into the same line:
                            // we have also counter it's effect to BulletSpan
                            bulletMargin -= (lists.size() - 2) * listItemIndent;
                        }
                    }
                    BulletSpan newBullet = new BulletSpan(bulletMargin);
                    end(output, Ul.class, false,
                            new LeadingMarginSpan.Standard(listItemIndent * (lists.size() - 1)),
                            newBullet);
                } else if (lists.peek().equalsIgnoreCase("ol")) {
                    if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
                        output.append("\n");
                    }
                    int numberMargin = listItemIndent * (lists.size() - 1);
                    if (lists.size() > 2) {
                        // Same as in ordered lists: counter the effect of nested Spans
                        numberMargin -= (lists.size() - 2) * listItemIndent;
                    }
                    end(output, Ol.class, false, new LeadingMarginSpan.Standard(numberMargin));
                }
            } else if (tag.equalsIgnoreCase("code")) {
                end(output, Code.class, false, new TypefaceSpan("monospace"));
            } else if (tag.equalsIgnoreCase("center")) {
                end(output, Center.class, true, new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER));
            } else if (tag.equalsIgnoreCase("s") || tag.equalsIgnoreCase("strike")) {
                end(output, Strike.class, false, new StrikethroughSpan());
            } else if (tag.equalsIgnoreCase("table")) {
                tableTagLevel--;

                // When we're back at the root-level table
                if (tableTagLevel == 0) {
                    final String tableHtml = tableHtmlBuilder.toString();

                    ClickableTableSpan clickableTableSpan = null;
                    if (mClickableTableSpan != null) {
                        clickableTableSpan = mClickableTableSpan.newInstance();
                        clickableTableSpan.setTableHtml(tableHtml);
                    }

                    DrawTableLinkSpan drawTableLinkSpan = null;
                    if (mDrawTableLinkSpan != null) {
                        drawTableLinkSpan = mDrawTableLinkSpan.newInstance();
                    }

                    end(output, Table.class, false, drawTableLinkSpan, clickableTableSpan);
                } else {
                    end(output, Table.class, false);
                }
            }
            else if (tag.equalsIgnoreCase("tr")) {
                end(output, Tr.class, false);
            } else if (tag.equalsIgnoreCase("th")) {
                end(output, Th.class, false);
            } else if (tag.equalsIgnoreCase("td")) {
                end(output, Td.class, false);
            }
        }

        storeTableTags(opening, tag);
        parseImgTags(opening, tag, output);
        parseURLTags(opening, tag, output);
    }

    /**
     * If we're arriving at a table tag or are already within a table tag, then we should store it
     * the raw HTML for our ClickableTableSpan
     */
    private void storeTableTags(boolean opening, String tag) {
        if (tableTagLevel > 0 || tag.equalsIgnoreCase("table")) {
            tableHtmlBuilder.append("<");
            if (!opening) {
                tableHtmlBuilder.append("/");
            }
            tableHtmlBuilder
                    .append(tag.toLowerCase())
                    .append(">");
        }
    }

    /**
     * ASLai to add
     *
     * @param opening
     * @param tag
     * @param output
     */
    private void parseImgTags(boolean opening, String tag, Editable output) {
        if (tag.equalsIgnoreCase("img")) {
            // 获取长度
            int len = output.length();
            ImageSpan[] imageSpans = output.getSpans(0, len, ImageSpan.class);
            final List<String> imageUrls = new ArrayList<String>();

            for (int i = 0, size = imageSpans.length; i < size; i++) {
                ImageSpan imageSpan = imageSpans[i];
                String imageUrl = imageSpan.getSource();
                int start = output.getSpanStart(imageSpan);
                int end = output.getSpanEnd(imageSpan);
                imageUrls.add(imageUrl);

                final int postion = i;

                ClickableImageSpan clickableImageSpan = null;
                if (mClickableImageSpan != null)
                {
                    clickableImageSpan = mClickableImageSpan.newInstance();
                    clickableImageSpan.setImageUrls(imageUrls);
                    clickableImageSpan.setPosition(postion);
                }

                ClickableSpan[] clickableSpans = output.getSpans(start, end, ClickableSpan.class);
                if (clickableSpans != null && clickableSpans.length != 0) {
                    for (ClickableSpan cs : clickableSpans) {
                        output.removeSpan(cs);
                    }
                }
                // 使图片可点击并监听点击事件
                output.setSpan(clickableImageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
    }

    private void parseURLTags(boolean opening, String tag, Editable output) {
        if (tag.equalsIgnoreCase("a"))
        {
            URLSpan[] urlSpans = output.getSpans(0, output.length(), URLSpan.class);
            final List<String> urls = new ArrayList<String>();
            for (int i = 0, size = urlSpans.length; i < size; i++) {
                URLSpan urlSpan = urlSpans[i];
                String url = urlSpan.getURL();
                int start = output.getSpanStart(urlSpan);
                int end = output.getSpanEnd(urlSpan);
                urls.add(url);
                final int position = i;
                ClickableURLSpan clickableURLSpan = null;
                if (mClickableURLSpan != null)
                {
                    clickableURLSpan = mClickableURLSpan.newInstance();
                    clickableURLSpan.setUrl(urls.get(position));
                }

                ClickableSpan[] clickableSpans = output.getSpans(start, end, ClickableSpan.class);
                if (clickableSpans != null && clickableSpans.length != 0) {
                    for (ClickableSpan cs : clickableSpans) {
                        output.removeSpan(cs);
                    }
                }
                output.setSpan(clickableURLSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
    }


    /**
     * Mark the opening tag by using private classes
     */
    private void start(Editable output, Object mark) {
        int len = output.length();
        output.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK);

        if (HtmlTextView.DEBUG) {
            Log.d(HtmlTextView.TAG, "len: " + len);
        }
    }

    /**
     * Modified from {@link Html}
     */
    private void end(Editable output, Class kind, boolean paragraphStyle, Object... replaces) {
        Object obj = getLast(output, kind);
        // start of the tag
        int where = output.getSpanStart(obj);
        // end of the tag
        int len = output.length();

        // If we're in a table, then we need to store the raw HTML for later
        if (tableTagLevel > 0) {
            final CharSequence extractedSpanText = extractSpanText(output, kind);
            tableHtmlBuilder.append(extractedSpanText);
        }

        output.removeSpan(obj);

        if (where != len) {
            int thisLen = len;
            // paragraph styles like AlignmentSpan need to end with a new line!
            if (paragraphStyle) {
                output.append("\n");
                thisLen++;
            }
            for (Object replace : replaces) {
                output.setSpan(replace, where, thisLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (HtmlTextView.DEBUG) {
                Log.d(HtmlTextView.TAG, "where: " + where);
                Log.d(HtmlTextView.TAG, "thisLen: " + thisLen);
            }
        }
    }

    /**
     * Returns the text contained within a span and deletes it from the output string
     */
    private CharSequence extractSpanText(Editable output, Class kind) {
        final Object obj = getLast(output, kind);
        // start of the tag
        final int where = output.getSpanStart(obj);
        // end of the tag
        final int len = output.length();

        final CharSequence extractedSpanText = output.subSequence(where, len);
        output.delete(where, len);
        return extractedSpanText;
    }

    /**
     * Get last marked position of a specific tag kind (private class)
     */
    private static Object getLast(Editable text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);
        if (objs.length == 0) {
            return null;
        } else {
            for (int i = objs.length; i > 0; i--) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1];
                }
            }
            return null;
        }
    }

    public void setClickableTableSpan(ClickableTableSpan clickableTableSpan) {
        this.mClickableTableSpan = clickableTableSpan;
    }

    public void setDrawTableLinkSpan(DrawTableLinkSpan drawTableLinkSpan) {
        this.mDrawTableLinkSpan = drawTableLinkSpan;
    }

    public void setClickableImageSpan(ClickableImageSpan clickableImageSpan) {
        this.mClickableImageSpan = clickableImageSpan;
    }

    public void setClickableURLSpan(ClickableURLSpan clickableURLSpan) {
        this.mClickableURLSpan = clickableURLSpan;
    }
}
