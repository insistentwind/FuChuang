package entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Deliver)表实体类
 *
 * @author makejava
 * @since 2024-03-14 21:48:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("deliver")
public class Deliver  {
    @TableId
    private Integer id;

    //用户id
    private Integer userId;
    //职位id
    private Integer workId;



}

