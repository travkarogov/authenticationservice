package twitsec.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitsec.authenticationservice.entity.converter.ListConverter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "totp")
public class TOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private String secretKey;

    private int validationCode;

    @Convert(converter = ListConverter.class)
    private List<Integer> scratchCodes;
}

