package io.github.hobart.api;

import io.github.hobart.api.enums.ResultCodeEnum;

import java.util.Collections;
import java.util.List;

public class PageResult<T> extends Result<PageData<T>> {

    private static final int FIRST_PAGE_NO = 1;

    private static final int DEFAULT_PAGE_SIZE = 50;

    public PageResult() {
        super(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc());
        setData(new PageData<>(Collections.emptyList()));
    }

    public PageResult(List<T> list, long total, int pageNum, int pageSize) {
        super(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc());
        PageData<T> pageData = new PageData<>(list);
        pageData.setPageNum(pageNum);
        pageData.setPageSize(pageSize);
        pageData.setTotal(total);
        pageData.setTotalPage(totalPage(total,pageSize));
        pageData.setList(list == null ? Collections.emptyList() : list);
        setData(pageData);
    }

    public static <T> PageResult<T> success() {
        return new PageResult<>();
    }

    public static <T> PageResult<T> success(List<T> list, long total, int pageNum, int pageSize) {
        return new PageResult<>(list, total, pageNum, pageSize);
    }

    public static <T> PageResult<T> success(List<T> list, long total, int pageSize) {
        return new PageResult<>(list, total, FIRST_PAGE_NO, pageSize);
    }

    public static <T> PageResult<T> success(List<T> list, long total) {
        return new PageResult<>(list, total, FIRST_PAGE_NO, DEFAULT_PAGE_SIZE);
    }

    public static int getStart(int pageNo, int pageSize) {
        if (pageNo < FIRST_PAGE_NO) {
            pageNo = FIRST_PAGE_NO;
        }

        if (pageSize < 1) {
            pageSize = 0;
        }

        return (pageNo - FIRST_PAGE_NO) * pageSize;
    }

    public static int getEnd(int pageNo, int pageSize) {
        int start = getStart(pageNo, pageSize);
        return getEndByStart(start, pageSize);
    }

    public static int[] transToStartEnd(int pageNo, int pageSize) {
        int start = getStart(pageNo, pageSize);
        return new int[]{start, getEndByStart(start, pageSize)};
    }

    public static int totalPage(int totalCount, int pageSize) {
        return totalPage((long)totalCount, pageSize);
    }

    public static int totalPage(long totalCount, int pageSize) {
        return pageSize == 0 ? 0 : Math.toIntExact(totalCount % (long)pageSize == 0L ? totalCount / (long)pageSize : totalCount / (long)pageSize + 1L);
    }

    public static int[] rainbow(int pageNo, int totalPage, int displayCount) {
        boolean isEven = (displayCount & 1) == 0;
        int left = displayCount >> 1;
        int right = displayCount >> 1;
        int length = displayCount;
        if (isEven) {
            ++right;
        }

        if (totalPage < displayCount) {
            length = totalPage;
        }

        int[] result = new int[length];
        if (totalPage >= displayCount) {
            if (pageNo <= left) {
                for(int i = 0; i < result.length; ++i) {
                    result[i] = i + 1;
                }
            } else if (pageNo > totalPage - right) {
                for(int i = 0; i < result.length; ++i) {
                    result[i] = i + totalPage - displayCount + 1;
                }
            } else {
                for(int i = 0; i < result.length; ++i) {
                    result[i] = i + pageNo - left + (isEven ? 1 : 0);
                }
            }
        } else {
            for(int i = 0; i < result.length; ++i) {
                result[i] = i + 1;
            }
        }

        return result;
    }

    public static int[] rainbow(int currentPage, int pageCount) {
        return rainbow(currentPage, pageCount, 10);
    }

    private static int getEndByStart(int start, int pageSize) {
        if (pageSize < 1) {
            pageSize = 0;
        }

        return start + pageSize;
    }
}
