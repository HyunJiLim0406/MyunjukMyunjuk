package jpa.myunjuk.module.model.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoDto {

    private Long bookId;
    private String title;
    private String thumbnail;
    private String content;
    private LocalDateTime saved;
}
