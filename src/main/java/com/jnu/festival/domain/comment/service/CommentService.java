package com.jnu.festival.domain.comment.service;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.comment.dto.request.CommentRequestDto;
import com.jnu.festival.domain.comment.dto.response.CommentDto;
import com.jnu.festival.domain.comment.dto.response.CommentListDto;
import com.jnu.festival.domain.comment.entity.Comment;
import com.jnu.festival.domain.comment.repository.CommentRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoothRepository boothRepository;
    private final CommentRepository commentRepository;

    public CommentListDto readCommentList(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        List<CommentDto> commentDtos = commentRepository.findAllByBooth(booth).stream()
                .map(comment -> CommentDto.builder()
                        .id(comment.getId())
                        .nickname(comment.getUser().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();

        return CommentListDto.builder()
                .comments(commentDtos)
                .commentCount(commentDtos.size())
                .build();
    }

    @Transactional
    public void createComment(Long boothId, CommentRequestDto request, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        commentRepository.save(
                Comment.builder()
                        .user(user)
                        .booth(booth)
                        .content(request.content())
                        .build()
        );
    }

    @Transactional
    public void deleteComment(Long boothId, Long commentId, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        // 댓글이 해당 부스에 속하는지 확인
        if (!comment.getBooth().getId().equals(boothId)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST_PARAMETER);
        }

        // 댓글 작성자와 요청한 사용자가 일치하는지 확인
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.NOT_MATCH_USER);
        }

        commentRepository.delete(comment);
    }
}
