package app.foot.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PlayerUpdate {
    private Integer id;
    private String name;
    private Boolean isGuardian;
}
