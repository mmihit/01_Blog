package _Blog.demo.DTO.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLiteDtoResponse {
    protected Long id;
    protected String username;
    protected String avatar;
}
