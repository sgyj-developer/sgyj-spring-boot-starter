package com.yeseung.sgyjspringbootstarter.support;

import lombok.Getter;

@Getter
public class PageMeta {

    private boolean hasMore;

    public static PageMeta of(boolean hasMore) {
        PageMeta pageMeta = new PageMeta();
        pageMeta.hasMore = hasMore;
        return pageMeta;
    }

}
