package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.request.HideItemRequestDto;

public interface HiddenPostService {
    void hidePost(HideItemRequestDto itemRequestDto);
    void unHidePost(String username, Long postId);
}
