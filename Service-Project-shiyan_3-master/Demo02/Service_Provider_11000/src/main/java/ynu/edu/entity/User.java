package ynu.edu.entity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    private Integer userId;
    private String userName;
    private String passWord;
}

