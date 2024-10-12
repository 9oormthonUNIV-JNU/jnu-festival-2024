package com.jnu.festival.domain.booth.service;

import com.jnu.festival.domain.booth.dto.CommentListResponseDto;
import com.jnu.festival.domain.booth.dto.CommentRequestDto;
import com.jnu.festival.domain.booth.dto.CommentResponseDto;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.Comment;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.booth.repository.CommentRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoothRepository boothRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(UserDetailsImpl userDetails, Long boothId, CommentRequestDto dto){
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        Comment comment = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .booth(booth)
                        .content(dto.getContent())
                        .build()
        );
    }

    @Transactional
    public void deleteComment(UserDetailsImpl userDetails, Long boothId, Long commentId){
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

    public CommentListResponseDto getComments(Long boothId) {

        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        List<Comment> comments = commentRepository.findCommentsByBooth(booth);
        List<CommentResponseDto> commentResponseDtoList = comments.stream()
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .name(comment.getUser().getNickname())
                        .content(comment.getContent())
                        .created_at(comment.getCreatedAt().toLocalDate())
                        .build())
                .collect(Collectors.toList());

        return CommentListResponseDto.builder()
                .comments(commentResponseDtoList)
                .commentCount(commentResponseDtoList.size())
                .build();
    }
}
