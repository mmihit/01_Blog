package _Blog.demo.Mapper;

import _Blog.demo.controller.PageDtoResponse;

public class PageMapper {
    public static <T> PageDtoResponse toPageDtoResponse(T data, boolean hasNext) {
        return PageDtoResponse.builder().data(data).hasNext(hasNext).build();
    }
}
