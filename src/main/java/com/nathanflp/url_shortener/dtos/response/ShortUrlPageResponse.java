package com.nathanflp.url_shortener.dtos.response;

import java.util.*;

public record ShortUrlPageResponse<T>(
                            List<T> content,
                            int page,
                            int size,
                            long totalElements,
                            int totalPages
                            ) {
}
