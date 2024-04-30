package com.yeseung.sgyjspringbootstarter.support;

import java.util.List;

public record Paged<T>(boolean hasMore, List<T> data) {

}
