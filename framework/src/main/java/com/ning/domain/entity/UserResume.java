package entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UserResume)表实体类
 *
 * @author makejava
 * @since 2024-03-14 21:38:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_resume")
public class UserResume  {
    @TableId
    private Integer id;

    //用户id
    private Integer userId;
    //简历id
    private Integer resumeId;



}

